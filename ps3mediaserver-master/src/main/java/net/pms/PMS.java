/*
 * PS3 Media Server, for streaming any medias to your PS3.
 * Copyright (C) 2008  A.Brochard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 2
 * of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.pms;

import com.sun.jna.Platform;
import net.pms.configuration.Build;
import net.pms.configuration.PmsConfiguration;
import net.pms.configuration.RendererConfiguration;
import net.pms.dlna.DLNAMediaDatabase;
import net.pms.dlna.RootFolder;
import net.pms.dlna.virtual.MediaLibrary;
import net.pms.encoders.Player;
import net.pms.encoders.PlayerFactory;
import net.pms.external.ExternalFactory;
import net.pms.external.ExternalListener;
import net.pms.formats.Format;
import net.pms.formats.FormatFactory;
import net.pms.io.*;
import net.pms.logging.FrameAppender;
import net.pms.logging.LoggingConfigFileLoader;
import net.pms.network.HTTPServer;
import net.pms.network.ProxyServer;
import net.pms.network.UPNPHelper;
import net.pms.newgui.DummyFrame;
import net.pms.newgui.IFrame;
import net.pms.newgui.LooksFrame;
import net.pms.newgui.ProfileChooser;
import net.pms.update.AutoUpdater;
import net.pms.util.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.BindException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.LogManager;

public class PMS {
	private static final String SCROLLBARS = "scrollbars";
	private static final String NATIVELOOK = "nativelook";
	private static final String CONSOLE = "console";
	private static final String NOCONSOLE = "noconsole";
	private static final String PROFILES = "profiles";
	private static Boolean isHeadless;

	/**
	 * @deprecated The version has moved to the resources/project.properties file. Use {@link #getVersion()} instead.
	 */
	@Deprecated
	public static String VERSION;

	public static final String AVS_SEPARATOR = "\1";

	// (innot): The logger used for all logging.
	private static final Logger logger = LoggerFactory.getLogger(PMS.class);

	// TODO(tcox):  This shouldn't be static
	private static PmsConfiguration configuration;

	/**
	 * Universally Unique Identifier used in the UPnP server.
	 */
	private String uuid;

	/**
	 * Relative location of a context sensitive help page in the documentation
	 * directory.
	 */
	private static String helpPage = "index.html";

	/**
	 * Returns a pointer to the PMS GUI's main window.
	 * @return {@link net.pms.newgui.IFrame} Main PMS window.
	 */
	public IFrame getFrame() {
		return frame;
	}

	/**
	 * Returns the root folder for a given renderer. There could be the case
	 * where a given media renderer needs a different root structure.
	 *
	 * @param renderer {@link net.pms.configuration.RendererConfiguration}
	 * is the renderer for which to get the RootFolder structure. If <code>null</code>,
	 * then the default renderer is used.
	 * @return {@link net.pms.dlna.RootFolder} The root folder structure for a given renderer
	 */
	public RootFolder getRootFolder(RendererConfiguration renderer) {
		// something to do here for multiple directories views for each renderer
		if (renderer == null) {
			renderer = RendererConfiguration.getDefaultConf();
		}

		return renderer.getRootFolder();
	}

	/**
	 * Pointer to a running PMS server.
	 */
	private static PMS instance = null;

	/**
	 * @deprecated This field is not used and will be removed in the future.
	 */
	@Deprecated
	public final static SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);

	/**
	 * @deprecated This field is not used and will be removed in the future.
	 */
	@Deprecated
	public final static SimpleDateFormat sdfHour = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

	/**
	 * Array of {@link net.pms.configuration.RendererConfiguration} that have been found by PMS.
	 */
	private final ArrayList<RendererConfiguration> foundRenderers = new ArrayList<RendererConfiguration>();

	/**
	 * @deprecated Use {@link #setRendererFound(RendererConfiguration)} instead.
	 */
	@Deprecated
	public void setRendererfound(RendererConfiguration renderer) {
		setRendererFound(renderer);
	}

	/**
	 * Adds a {@link net.pms.configuration.RendererConfiguration} to the list of media renderers found.
	 * The list is being used, for example, to give the user a graphical representation of the found
	 * media renderers.
	 *
	 * @param renderer {@link net.pms.configuration.RendererConfiguration}
	 * @since 1.82.0
	 */
	public void setRendererFound(RendererConfiguration renderer) {
		if (!foundRenderers.contains(renderer) && !renderer.isFDSSDP()) {
			foundRenderers.add(renderer);
			frame.addRendererIcon(renderer.getRank(), renderer.getRendererName(), renderer.getRendererIcon());
			frame.setStatusCode(0, Messages.getString("PMS.18"), "apply-220.png");
		}
	}

	/**
	 * HTTP server that serves the XML files needed by UPnP server and the media files.
	 */
	private HTTPServer server;

	/**
	 * User friendly name for the server.
	 */
	private String serverName;

	// FIXME unused
	private ProxyServer proxyServer;

	public ProxyServer getProxy() {
		return proxyServer;
	}

	public ArrayList<Process> currentProcesses = new ArrayList<Process>();

	private PMS() { }

	/**
	 * {@link net.pms.newgui.IFrame} object that represents the PMS GUI.
	 */
	private IFrame frame;

	/**
	 * Interface to Windows-specific functions, like Windows Registry. registry is set by {@link #init()}.
	 * @see net.pms.io.WinUtils
	 */
	private SystemUtils registry;

	/**
	 * @see net.pms.io.WinUtils
	 */
	public SystemUtils getRegistry() {
		return registry;
	}

	/**
	 * Executes a new Process and creates a fork that waits for its results.
	 * TODO Extend explanation on where this is being used.
	 * @param name Symbolic name for the process to be launched, only used in the trace log
	 * @param error (boolean) Set to true if you want PMS to add error messages to the trace pane
	 * @param workDir (File) optional working directory to run the process in
	 * @param params (array of Strings) array containing the command to call and its arguments
	 * @return Returns true if the command exited as expected
	 * @throws Exception TODO: Check which exceptions to use
	 */
	private boolean checkProcessExistence(String name, boolean error, File workDir, String... params) throws Exception {
		logger.debug("launching: " + params[0]);

		try {
			ProcessBuilder pb = new ProcessBuilder(params);
			if (workDir != null) {
				pb.directory(workDir);
			}
			final Process process = pb.start();

			OutputTextConsumer stderrConsumer = new OutputTextConsumer(process.getErrorStream(), false);
			stderrConsumer.start();

			OutputTextConsumer outConsumer = new OutputTextConsumer(process.getInputStream(), false);
			outConsumer.start();

			Runnable r = new Runnable() {
				public void run() {
					ProcessUtil.waitFor(process);
				}
			};

			Thread checkThread = new Thread(r, "PMS Checker");
			checkThread.start();
			checkThread.join(60000);
			checkThread.interrupt();
			checkThread = null;

			// XXX no longer used
			if (params[0].equals("vlc") && stderrConsumer.getResults().get(0).startsWith("VLC")) {
				return true;
			}

			// XXX no longer used
			if (params[0].equals("ffmpeg") && stderrConsumer.getResults().get(0).startsWith("FF")) {
				return true;
			}

			int exit = process.exitValue();
			if (exit != 0) {
				if (error) {
					logger.info("[" + exit + "] Cannot launch " + name + " / Check the presence of " + params[0] + " ...");
				}
				return false;
			}
			return true;
		} catch (Exception e) {
			if (error) {
				logger.error("Cannot launch " + name + " / Check the presence of " + params[0] + " ...", e);
			}
			return false;
		}
	}

	/**
	 * @see System#err
	 */
	@SuppressWarnings("unused")
	private final PrintStream stderr = System.err;

	/**
	 * Main resource database that supports search capabilities. Also known as media cache.
	 * @see net.pms.dlna.DLNAMediaDatabase
	 */
	private DLNAMediaDatabase database;

	private void initializeDatabase() {
		database = new DLNAMediaDatabase("medias"); // TODO: rename "medias" -> "cache"
		database.init(false);
	}

	/**
	 * Used to get the database. Needed in the case of the Xbox 360, that requires a database.
	 * for its queries.
	 * @return (DLNAMediaDatabase) a reference to the database instance or <b>null</b> if one isn't defined
	 * (e.g. if the cache is disabled).
	 */
	public synchronized DLNAMediaDatabase getDatabase() {
		if (configuration.getUseCache()) {
			if (database == null) {
				initializeDatabase();
			}

			return database;
		}

		return null;
	}

	// helper method for displayBanner: return a file or directory's
	// permissions in the Unix ls style e.g.: "rw" (read-write),
	// "r-" (read-only) &c.
	private String getPathPermissions(String path) {
		String permissions;
		File file = new File(path);

		if (file.exists()) {
			if (file.isFile()) {
				permissions = String.format("%s%s",
					FileUtil.isFileReadable(file) ? "r" : "-",
					FileUtil.isFileWritable(file) ? "w" : "-"
				);
			} else {
				permissions = String.format("%s%s",
					FileUtil.isDirectoryReadable(file) ? "r" : "-",
					FileUtil.isDirectoryWritable(file) ? "w" : "-"
				);
			}
		} else {
			permissions = "file not found";
		}

		return permissions;
	}

	private void displayBanner() throws IOException {
		logger.info("Starting " + PropertiesUtil.getProjectProperties().get("project.name") + " " + getVersion());
		logger.info("by shagrath / 2008-2013");
		logger.info("http://ps3mediaserver.org");
		logger.info("https://github.com/ps3mediaserver/ps3mediaserver");
		logger.info("");

		String commitId = PropertiesUtil.getProjectProperties().get("git.commit.id");
		String commitTime = PropertiesUtil.getProjectProperties().get("git.commit.time");
		String shortCommitId = commitId.substring(0,  9);

		logger.info("Build: " + shortCommitId + " (" + commitTime + ")");

		// Log system properties
		logSystemInfo();

		String cwd = new File("").getAbsolutePath();
		logger.info("Working directory: " + cwd);

		logger.info("Temp directory: " + configuration.getTempFolder());

		// Verify the java.io.tmpdir is writable; JNA requires it.
		// Note: the configured tempFolder has already been checked, but it
		// may differ from the java.io.tmpdir so double check to be sure.
		File javaTmpdir = new File(System.getProperty("java.io.tmpdir"));

		if (!FileUtil.isDirectoryWritable(javaTmpdir)) {
			logger.error("The Java temp directory \"{}\" is not writable for PMS!", javaTmpdir.getAbsolutePath());
			logger.error("Please make sure the directory is writable for user \"{}\"", System.getProperty("user.name"));
			throw new IOException("Cannot write to Java temp directory");
		}

		logger.info("Logging config file: {}", LoggingConfigFileLoader.getConfigFilePath());

		HashMap<String, String> lfps = LoggingConfigFileLoader.getLogFilePaths();

		// debug.log filename(s) and path(s)
		if (lfps != null && lfps.size() > 0) {
			if (lfps.size() == 1) {
				Entry<String, String> entry = lfps.entrySet().iterator().next();
				logger.info(String.format("%s: %s", entry.getKey(), entry.getValue()));
			} else {
				logger.info("Logging to multiple files:");
				Iterator<Entry<String, String>> logsIterator = lfps.entrySet().iterator();
				Entry<String, String> entry;
				while (logsIterator.hasNext()) {
					entry = logsIterator.next();
					logger.info(String.format("%s: %s", entry.getKey(), entry.getValue()));
				}
			}
		}

		String profilePath = configuration.getProfilePath();
		String profileDirectoryPath = configuration.getProfileDirectory();
		logger.info("");
		logger.info("Profile directory: {}", profileDirectoryPath);
		logger.info("Profile directory permissions: {}", getPathPermissions(profileDirectoryPath));
		logger.info("Profile path: {}", profilePath);
		logger.info("Profile permissions: {}", getPathPermissions(profilePath));
		logger.info("Profile name: {}", configuration.getProfileName());

		String webConfPath = configuration.getWebConfPath();
		logger.info("");
		logger.info("Web conf path: {}", webConfPath);
		logger.info("Web conf permissions: {}", getPathPermissions(webConfPath));

		logger.info("");
	}

	/**
	 * Initialisation procedure for PMS.
	 * @return true if the server has been initialized correctly. false if the server could
	 * not be set to listen on the UPnP port.
	 * @throws Exception
	 */
	private boolean init() throws Exception {
		// The public VERSION field is deprecated.
		// This is a temporary fix for backwards compatibility
		VERSION = getVersion();

		// call this as early as possible
		displayBanner();

		AutoUpdater autoUpdater = null;

		if (configuration.isAutoUpdate()) {
			String serverURL = Build.getUpdateServerURL();
			autoUpdater = new AutoUpdater(serverURL, getVersion());
		}

		registry = createSystemUtils();

		if (!isHeadless()) {
			frame = new LooksFrame(autoUpdater, configuration);
		} else {
			logger.info("GUI environment not available");
			logger.info("Switching to console mode");
			frame = new DummyFrame();
		}

		/*
		 * we're here:
		 *
		 *     main() -> createInstance() -> init()
		 *
		 * which means we haven't created the instance returned by get()
		 * yet, so the frame appender can't access the frame in the
		 * standard way i.e. PMS.get().getFrame(). we solve it by
		 * inverting control ("don't call us; we'll call you") i.e.
		 * we notify the appender when the frame is ready rather than
		 * e.g. making getFrame() static and requiring the frame
		 * appender to poll it.
		 *
		 * XXX an event bus (e.g. MBassador or Guava EventBus
		 * (if they fix the memory-leak issue)) notification
		 * would be cleaner and could support other lifecycle
		 * notifications (see above).
		 */
		FrameAppender.setFrame(frame);

		configuration.addConfigurationListener(new ConfigurationListener() {
			@Override
			public void configurationChanged(ConfigurationEvent event) {
				if ((!event.isBeforeUpdate())
						&& PmsConfiguration.NEED_RELOAD_FLAGS.contains(event.getPropertyName())) {
					frame.setReloadable(true);
				}
			}
		});

		frame.setStatusCode(0, Messages.getString("PMS.130"), "connect_no-220.png");
		RendererConfiguration.loadRendererConfigurations(configuration);
		logger.info("Checking MPlayer font cache. It can take a minute or so.");
		checkProcessExistence("MPlayer", true, null, configuration.getMplayerPath(), "dummy");

		if (Platform.isWindows()) {
			checkProcessExistence("MPlayer", true, configuration.getTempFolder(), configuration.getMplayerPath(), "dummy");
		}

		logger.info("Done!");

		// check the existence of Vsfilter.dll
		if (registry.isAvis() && registry.getAvsPluginsDir() != null) {
			logger.info("Found AviSynth plugins dir: " + registry.getAvsPluginsDir().getAbsolutePath());
			File vsFilterdll = new File(registry.getAvsPluginsDir(), "VSFilter.dll");
			if (!vsFilterdll.exists()) {
				logger.info("VSFilter.dll is not in the AviSynth plugins directory. This can cause problems when trying to play subtitled videos with AviSynth");
			}
		}

		// Check if VLC is found
		String vlcVersion = registry.getVlcVersion();
		String vlcPath = registry.getVlcPath();

		if (vlcVersion != null && vlcPath != null) {
			logger.info("Found VLC version " + vlcVersion + " at: " + vlcPath);

			Version vlc = new Version(vlcVersion);
			Version requiredVersion = new Version("2.0.2");

			if (vlc.compareTo(requiredVersion) <= 0) {
				logger.error("Only VLC versions 2.0.2 and above are supported");
			}
		}

		// check if Kerio is installed
		if (registry.isKerioFirewall()) {
			logger.info("Detected Kerio firewall");
		}

		// force use of specific dvr ms muxer when it's installed in the right place
		File dvrsMsffmpegmuxer = new File("win32/dvrms/ffmpeg_MPGMUX.exe");
		if (dvrsMsffmpegmuxer.exists()) {
			configuration.setFfmpegAlternativePath(dvrsMsffmpegmuxer.getAbsolutePath());
		}

		// disable jaudiotagger logging
		LogManager.getLogManager().readConfiguration(new ByteArrayInputStream("org.jaudiotagger.level=OFF".getBytes()));

		// wrap System.err
		System.setErr(new PrintStream(new SystemErrWrapper(), true));

		server = new HTTPServer(configuration.getServerPort());

		/*
		 * XXX: keep this here (i.e. after registerExtensions and before registerPlayers) so that plugins
		 * can register custom players correctly (e.g. in the GUI) and/or add/replace custom formats
		 *
		 * XXX: if a plugin requires initialization/notification even earlier than
		 * this, then a new external listener implementing a new callback should be added
		 * e.g. StartupListener.registeredExtensions()
		 */
		try {
			ExternalFactory.lookup();
		} catch (Exception e) {
			logger.error("Error loading plugins", e);
		}

		// a static block in Player doesn't work (i.e. is called too late).
		// this must always be called *after* the plugins have loaded.
		// here's as good a place as any
		Player.initializeFinalizeTranscoderArgsListeners();

		// Initialize a player factory to register all players
		PlayerFactory.initialize(configuration);

		// Instantiate listeners that require registered players.
		ExternalFactory.instantiateLateListeners();

		// Any plugin-defined players are now registered, create the GUI view.
		frame.addEngines();

		boolean binding = false;

		try {
			binding = server.start();
		} catch (BindException b) {
			logger.info("FATAL ERROR: Unable to bind on port: " + configuration.getServerPort() + ", because: " + b.getMessage());
			logger.info("Maybe another process is running or the hostname is wrong.");
		}

		new Thread("Connection Checker") {
			@Override
			public void run() {
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) { }

				if (foundRenderers.isEmpty()) {
					frame.setStatusCode(0, Messages.getString("PMS.0"), "messagebox_critical-220.png");
				} else {
					frame.setStatusCode(0, Messages.getString("PMS.18"), "apply-220.png");
				}
			}
		}.start();

		if (!binding) {
			return false;
		}

		// initialize the cache
		if (configuration.getUseCache()) {
			initializeDatabase(); // XXX: this must be done *before* new MediaLibrary -> new MediaLibraryFolder
			mediaLibrary = new MediaLibrary();
			logger.info("A tiny cache admin interface is available at: http://" + server.getHost() + ":" + server.getPort() + "/console/home");
		}

		// XXX: this must be called:
		//     a) *after* loading plugins i.e. plugins register root folders then RootFolder.discoverChildren adds them
		//     b) *after* mediaLibrary is initialized, if enabled (above)
		getRootFolder(RendererConfiguration.getDefaultConf());

		frame.serverReady();

		// UPNPHelper.sendByeBye();
		Runtime.getRuntime().addShutdownHook(new Thread("PMS Listeners Stopper") {
			@Override
			public void run() {
				try {
					for (ExternalListener l : ExternalFactory.getExternalListeners()) {
						l.shutdown();
					}
					UPNPHelper.shutDownListener();
					UPNPHelper.sendByeBye();
					logger.debug("Forcing shutdown of all active processes");
					for (Process p : currentProcesses) {
						try {
							p.exitValue();
						} catch (IllegalThreadStateException ise) {
							logger.trace("Forcing shutdown of process: " + p);
							ProcessUtil.destroy(p);
						}
					}
					get().getServer().stop();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					logger.debug("Caught exception", e);
				}
			}
		});

		UPNPHelper.sendAlive();
		logger.trace("Waiting 250 milliseconds...");
		Thread.sleep(250);
		UPNPHelper.listen();

		return true;
	}

	private MediaLibrary mediaLibrary;

	/**
	 * Returns the MediaLibrary used by PMS.
	 * @return (MediaLibrary) Used mediaLibrary, if any. null if none is in use.
	 */
	public MediaLibrary getLibrary() {
		return mediaLibrary;
	}

	private SystemUtils createSystemUtils() {
		if (Platform.isWindows()) {
			return new WinUtils();
		} else {
			if (Platform.isMac()) {
				return new MacSystemUtils();
			} else {
				if (Platform.isSolaris()) {
					return new SolarisUtils();
				} else {
					return new BasicSystemUtils();
				}
			}
		}
	}

	/**
	 * Executes the needed commands in order to make PMS a Windows service that starts whenever the machine is started.
	 * This function is called from the Network tab.
	 * @return true if PMS could be installed as a Windows service.
	 * @see net.pms.newgui.GeneralTab#build()
	 */
	public boolean installWin32Service() {
		logger.info(Messages.getString("PMS.41"));
		String cmdArray[] = new String[]{ "win32/service/wrapper.exe", "-r", "wrapper.conf" };
		OutputParams output = new OutputParams(configuration);
		output.noexitcheck = true;
		ProcessWrapperImpl pwuninstall = new ProcessWrapperImpl(cmdArray, output);
		pwuninstall.runInSameThread();
		cmdArray = new String[]{ "win32/service/wrapper.exe", "-i", "wrapper.conf" };
		ProcessWrapperImpl pwInstall = new ProcessWrapperImpl(cmdArray, new OutputParams(configuration));
		pwInstall.runInSameThread();
		return pwInstall.isSuccess();
	}

	/**
	 * @deprecated Use {@link #getFoldersConf()} instead.
	 */
	@Deprecated
	public File[] getFoldersConf(boolean log) {
		return getFoldersConf();
	}

	/**
	 * Transforms a comma-separated list of directory entries into an array of {@link String}.
	 * Checks that the directory exists and is a valid directory.
	 *
	 * @return {@link java.io.File}[] Array of directories.
	 * @throws java.io.IOException
	 */
	public File[] getFoldersConf() {
		String folders = getConfiguration().getFolders();

		if (folders == null || folders.length() == 0) {
			return null;
		}

		ArrayList<File> directories = new ArrayList<File>();
		String[] foldersArray = folders.split(",");

		for (String folder : foldersArray) {
			// unescape embedded commas. note: backslashing isn't safe as it conflicts with
			// Windows path separators:
			// http://ps3mediaserver.org/forum/viewtopic.php?f=14&t=8883&start=250#p43520
			folder = folder.replaceAll("&comma;", ",");

			// this is called *way* too often
			// so log it so we can fix it.
			logger.info("Checking shared folder: " + folder);

			File file = new File(folder);

			if (file.exists()) {
				if (!file.isDirectory()) {
					logger.warn("The file " + folder + " is not a directory! Please remove it from your Shared folders list on the Navigation/Share Settings tab");
				}
			} else {
				logger.warn("The directory " + folder + " does not exist. Please remove it from your Shared folders list on the Navigation/Share Settings tab");
			}

			// add the file even if there are problems so that the user can update the shared folders as required.
			directories.add(file);
		}

		File f[] = new File[directories.size()];
		directories.toArray(f);
		return f;
	}

	/**
	 * Restarts the server. The trigger is either a button on the main PMS window or via
	 * an action item.
	 * @throws java.io.IOException
	 */
	// XXX: don't try to optimize this by reusing the same server instance.
	// see the comment above HTTPServer.stop()
	public void reset() {
		TaskRunner.getInstance().submitNamed("restart", true, new Runnable() {
			public void run() {
				try {
					logger.trace("Waiting 1 second...");
					UPNPHelper.sendByeBye();
					server.stop();
					server = null;
					RendererConfiguration.resetAllRenderers();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.trace("Caught exception", e);
					}
					server = new HTTPServer(configuration.getServerPort());
					server.start();
					UPNPHelper.sendAlive();
					frame.setReloadable(false);
				} catch (IOException e) {
					logger.error("error during restart :" +e.getMessage(), e);
				}
			}
		});
	}

	// Cannot remove these methods because of backwards compatibility;
	// none of the PMS code uses it, but some plugins still do.

	/**
	 * @deprecated Use the SLF4J logging API instead.
	 * Adds a message to the debug stream, or {@link System#out} in case the
	 * debug stream has not been set up yet.
	 * @param msg {@link String} to be added to the debug stream.
	 */
	@Deprecated
	public static void debug(String msg) {
		logger.trace(msg);
	}

	/**
	 * @deprecated Use the SLF4J logging API instead.
	 * Adds a message to the info stream.
	 * @param msg {@link String} to be added to the info stream.
	 */
	@Deprecated
	public static void info(String msg) {
		logger.debug(msg);
	}

	/**
	 * @deprecated Use the SLF4J logging API instead.
	 * Adds a message to the minimal stream. This stream is also
	 * shown in the Trace tab.
	 * @param msg {@link String} to be added to the minimal stream.
	 */
	@Deprecated
	public static void minimal(String msg) {
		logger.info(msg);
	}

	/**
	 * @deprecated Use the SLF4J logging API instead.
	 * Adds a message to the error stream. This is usually called by
	 * statements that are in a try/catch block.
	 * @param msg {@link String} to be added to the error stream
	 * @param t {@link Throwable} comes from an {@link Exception}
	 */
	@Deprecated
	public static void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	/**
	 * Creates a new random {@link #uuid}. These are used to uniquely identify the server to renderers (i.e.
	 * renderers treat multiple servers with the same UUID as the same server).
	 * @return {@link String} with an Universally Unique Identifier.
	 */
	// XXX don't use the MAC address to seed the UUID as it breaks multiple profiles:
	// http://www.ps3mediaserver.org/forum/viewtopic.php?f=6&p=75542#p75542
	public synchronized String usn() {
		if (uuid == null) {
			// retrieve UUID from configuration
			uuid = getConfiguration().getUuid();

			if (uuid == null) {
				uuid = UUID.randomUUID().toString();
				logger.info("Generated new random UUID: {}", uuid);

				// save the newly-generated UUID
				getConfiguration().setUuid(uuid);

				try {
					getConfiguration().save();
				} catch (ConfigurationException e) {
					logger.error("Failed to save configuration with new UUID", e);
				}
			}

			logger.info("Using the following UUID configured in PMS.conf: {}", uuid);
		}

		return "uuid:" + uuid;
	}

	/**
	 * Returns the user friendly name of the UPnP server.
	 * @return {@link String} with the user friendly name.
	 */
	public String getServerName() {
		if (serverName == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(System.getProperty("os.name").replace(" ", "_"));
			sb.append("-");
			sb.append(System.getProperty("os.arch").replace(" ", "_"));
			sb.append("-");
			sb.append(System.getProperty("os.version").replace(" ", "_"));
			sb.append(", UPnP/1.0, PMS/" + getVersion());
			serverName = sb.toString();
		}

		return serverName;
	}

	/**
	 * Returns the PMS instance.
	 * @return {@link net.pms.PMS}
	 */
	public static PMS get() {
		// XXX when PMS is run as an application, the instance is initialized via the createInstance call in main().
		// However, plugin tests may need access to a PMS instance without going
		// to the trouble of launching the PMS application, so we provide a fallback
		// initialization here. Either way, createInstance() should only be called once (see below)
		if (instance == null) {
			createInstance();
		}

		return instance;
	}

	private synchronized static void createInstance() {
		assert instance == null; // this should only be called once
		instance = new PMS();

		try {
			if (instance.init()) {
				logger.info("The server should now appear on your renderer");
			} else {
				logger.error("A serious error occurred during PMS init");
			}
		} catch (Exception e) {
			logger.error("A serious error occurred during PMS init", e);
		}
	}

	/**
	 * @deprecated Use {@link net.pms.formats.FormatFactory#getAssociatedFormat(String)}
	 * instead.
	 *
	 * @param filename
	 * @return The format.
	 */
	@Deprecated
	public Format getAssociatedFormat(String filename) {
		return FormatFactory.getAssociatedFormat(filename);
	}

	public static void main(String args[]) throws IOException, ConfigurationException {
		boolean displayProfileChooser = false;

		// FIXME (breaking change): use a standard argument
		// format (and a standard argument processor) e.g.
		// --console, --scrollbars &c.
		if (args.length > 0) {
			for (int a = 0; a < args.length; a++) {
				if (args[a].equals(CONSOLE)) {
					System.setProperty(CONSOLE, Boolean.toString(true));
				} else if (args[a].equals(NATIVELOOK)) {
					System.setProperty(NATIVELOOK, Boolean.toString(true));
				} else if (args[a].equals(SCROLLBARS)) {
					System.setProperty(SCROLLBARS, Boolean.toString(true));
				} else if (args[a].equals(NOCONSOLE)) {
					System.setProperty(NOCONSOLE, Boolean.toString(true));
				} else if (args[a].equals(PROFILES)) {
					displayProfileChooser = true;
				}
			}
		}

		try {
			Toolkit.getDefaultToolkit();

			if (isHeadless()) {
				if (System.getProperty(NOCONSOLE) == null) {
					System.setProperty(CONSOLE, Boolean.toString(true));
				}
			}
		} catch (Throwable t) {
			System.err.println("Toolkit error: " + t.getClass().getName() + ": " + t.getMessage());

			if (System.getProperty(NOCONSOLE) == null) {
				System.setProperty(CONSOLE, Boolean.toString(true));
			}
		}

		if (!isHeadless() && displayProfileChooser) {
			ProfileChooser.display();
		}

		try {
			setConfiguration(new PmsConfiguration());
			assert getConfiguration() != null;

			// Load the (optional) logback config file.
			// This has to be called after 'new PmsConfiguration'
			// as the logging starts immediately and some filters
			// need the PmsConfiguration.
			// XXX not sure this is (still) true: the only filter
			// we use is ch.qos.logback.classic.filter.ThresholdFilter
			LoggingConfigFileLoader.load();

			// create the PMS instance returned by get()
			createInstance(); // calls new() then init()
		} catch (Throwable t) {
			String errorMessage = String.format(
				"Configuration error: %s: %s",
				t.getClass().getName(),
				t.getMessage()
			);

			System.err.println(errorMessage);
			t.printStackTrace();

			if (!isHeadless() && instance != null) {
				JOptionPane.showMessageDialog(
					((JFrame) (SwingUtilities.getWindowAncestor((Component) instance.getFrame()))),
					errorMessage,
					Messages.getString("PMS.42"),
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}

	public HTTPServer getServer() {
		return server;
	}

	public void save() {
		try {
			configuration.save();
		} catch (ConfigurationException e) {
			logger.error("Could not save configuration", e);
		}
	}

	public void storeFileInCache(File file, int formatType) {
		if (getConfiguration().getUseCache()
				&& !getDatabase().isDataExists(file.getAbsolutePath(), file.lastModified())) {

			getDatabase().insertData(file.getAbsolutePath(), file.lastModified(), formatType, null);
		}
	}

	/**
	 * Retrieves the {@link net.pms.configuration.PmsConfiguration PmsConfiguration} object
	 * that contains all configured settings for PMS. The object provides getters for all
	 * configurable PMS settings.
	 *
	 * @return The configuration object
	 */
	public static PmsConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the {@link net.pms.configuration.PmsConfiguration PmsConfiguration} object
	 * that contains all configured settings for PMS. The object provides getters for all
	 * configurable PMS settings.
	 *
	 * @param conf The configuration object.
	 */
	public static void setConfiguration(PmsConfiguration conf) {
		configuration = conf;
	}

	/**
	 * Returns the project version for PMS.
	 *
	 * @return The project version.
	 */
	public static String getVersion() {
		return PropertiesUtil.getProjectProperties().get("project.version");
	}

	/**
	 * Log system properties identifying Java, the OS and encoding and log
	 * warnings where appropriate.
	 */
	private void logSystemInfo() {
		long memoryInMB = Runtime.getRuntime().maxMemory() / 1048576;

		logger.info("Java: " + System.getProperty("java.vm.name") + " " + System.getProperty("java.version") + " by " + System.getProperty("java.vendor"));
		logger.info("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"));
		logger.info("Encoding: " + System.getProperty("file.encoding"));
		logger.info("Memory: " + memoryInMB + " " + Messages.getString("StatusTab.12"));
		logger.info("");

		if (Platform.isMac()) {
			// The binaries shipped with the Mac OS X version of PMS are being
			// compiled against specific OS versions, making them incompatible
			// with older versions. Warn the user about this when necessary.
			String osVersion = System.getProperty("os.version");

			// Split takes a regular expression, so escape the dot.
			String[] versionNumbers = osVersion.split("\\.");

			if (versionNumbers.length > 1) {
				try {
					int osVersionMinor = Integer.parseInt(versionNumbers[1]);

					if (osVersionMinor < 6) {
						logger.warn("-----------------------------------------------------------------");
						logger.warn("WARNING!");
						logger.warn("PMS ships with binaries compiled for Mac OS X 10.6 or higher.");
						logger.warn("You are running an older version of Mac OS X so PMS may not work!");
						logger.warn("More information in the FAQ:");
						logger.warn("http://www.ps3mediaserver.org/forum/viewtopic.php?f=6&t=3507&p=66371#p66371");
						logger.warn("-----------------------------------------------------------------");
						logger.warn("");
					}
				} catch (NumberFormatException e) {
					logger.debug("Cannot parse minor os.version number");
				}
			}
		}
	}

	/**
	 * Check if server is running in headless (console) mode.
	 *
	 * @return true if server is running in headless (console) mode, false otherwise
	 */
	public static synchronized boolean isHeadless() {
		if (isHeadless == null) {
			if (System.getProperty(CONSOLE) != null) {
				isHeadless = true;
			} else {
				try {
					javax.swing.JDialog d = new javax.swing.JDialog();
					d.dispose();
					isHeadless = false;
				} catch (Throwable throwable) {
					isHeadless = true;
				}
			}
		}

		return isHeadless;
	}

	/**
	 * Sets the relative URL of a context sensitive help page located in the
	 * documentation directory.
	 *
	 * @param page The help page.
	 */
	public static void setHelpPage(String page) {
		helpPage = page;
	}

	/**
	 * Returns the relative URL of a context sensitive help page in the
	 * documentation directory.
	 *
	 * @return The help page.
	 */
	public static String getHelpPage() {
		return helpPage;
	}
}
