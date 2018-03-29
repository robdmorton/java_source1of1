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
package net.pms.dlna;

import com.floreysoft.jmte.Engine;

import net.pms.Messages;
import net.pms.PMS;
import net.pms.configuration.FormatConfiguration;
import net.pms.configuration.PmsConfiguration;
import net.pms.configuration.RendererConfiguration;
import net.pms.dlna.virtual.TranscodeVirtualFolder;
import net.pms.dlna.virtual.VirtualFolder;
import net.pms.encoders.*;
import net.pms.external.AdditionalResourceFolderListener;
import net.pms.external.ExternalFactory;
import net.pms.external.ExternalListener;
import net.pms.external.StartStopListener;
import net.pms.formats.Format;
import net.pms.formats.FormatFactory;
import net.pms.io.OutputParams;
import net.pms.io.ProcessWrapper;
import net.pms.io.SizeLimitInputStream;
import net.pms.network.HTTPResource;
import net.pms.util.ImagesUtil;
import net.pms.util.Iso639;
import net.pms.util.MpegUtil;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static net.pms.util.StringUtil.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Represents any item that can be browsed via the UPNP ContentDirectory service.
 *
 * TODO: Change all instance variables to private. For backwards compatibility
 * with external plugin code the variables have all been marked as deprecated
 * instead of changed to private, but this will surely change in the future.
 * When everything has been changed to private, the deprecated note can be
 * removed.
 */
public abstract class DLNAResource extends HTTPResource implements Cloneable, Runnable {
	private final Map<String, Integer> requestIdToRefcount = new HashMap<String, Integer>();
	private boolean resolved;

	private static final int STOP_PLAYING_DELAY = 4000;
	private static final Logger logger = LoggerFactory.getLogger(DLNAResource.class);
	private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
	private static final PmsConfiguration configuration = PMS.getConfiguration();
	private static final Engine displayNameTemplateEngine = Engine.createCompilingEngine();

	static {
		displayNameTemplateEngine.setExprStartToken("<");
		displayNameTemplateEngine.setExprEndToken(">");
	}

	protected static final int MAX_ARCHIVE_ENTRY_SIZE = 10000000;
	protected static final int MAX_ARCHIVE_SIZE_SEEK = 800000000;

	/**
	 * The name displayed on the renderer. Cached the first time getDisplayName(RendererConfiguration) is called.
	 */
	private String displayName;

	/**
	 * @deprecated This field will be removed. Use {@link net.pms.configuration.PmsConfiguration#getTranscodeFolderName()} instead.
	 */
	@Deprecated
	protected static final String TRANSCODE_FOLDER = Messages.getString("TranscodeVirtualFolder.0"); // localized #--TRANSCODE--#

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected int specificType;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected String id;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected DLNAResource parent;

	/**
	 * @deprecated This field will be removed. Use {@link #getFormat()} and
	 * {@link #setFormat(Format)} instead.
	 */
	@Deprecated
	protected Format ext;

	/**
	 * The format of this resource.
	 */
	private Format format;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected DLNAMediaInfo media;

	/**
	 * @deprecated Use {@link #getMediaAudio()} and {@link
	 * #setMediaAudio(DLNAMediaAudio)} to access this field.
	 */
	@Deprecated
	protected DLNAMediaAudio media_audio;

	/**
	 * @deprecated Use {@link #getMediaSubtitle()} and {@link
	 * #setMediaSubtitle(DLNAMediaSubtitle)} to access this field.
	 */
	@Deprecated
	protected DLNAMediaSubtitle media_subtitle;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected long lastmodified; // TODO make private and rename lastmodified -> lastModified

	/**
	 * Represents the transformation to be used to the file. If null, then
	 * @see Player
	 */
	private Player player;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected boolean discovered = false;

	private ProcessWrapper externalProcess;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected boolean srtFile;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected int updateId = 1;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	public static int systemUpdateId = 1;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected boolean noName;

	private int nametruncate;
	private DLNAResource first;
	private DLNAResource second;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 *
	 * The time range for the file containing the start and end time in seconds.
	 */
	@Deprecated
	protected Range.Time splitRange = new Range.Time();

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected int splitTrack;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected String fakeParentId;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	// Ditlew - needs this in one of the derived classes
	@Deprecated
	protected RendererConfiguration defaultRenderer;

	private String dlnaspec;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected boolean avisynth;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 */
	@Deprecated
	protected boolean skipTranscode = false;

	private boolean allChildrenAreFolders = true;
	private String dlnaOrgOpFlags;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 *
	 * List of children objects associated with this DLNAResource. This is only valid when the DLNAResource is of the container type.
	 */
	@Deprecated
	protected List<DLNAResource> children;

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 *
	 * The numerical ID (1-based index) assigned to the last child of this folder. The next child is assigned this ID + 1.
	 */
	// FIXME should be lastChildId
	@Deprecated
	protected int lastChildrenId = 0; // XXX make private and rename lastChildrenId -> lastChildId

	/**
	 * @deprecated Use standard getter and setter to access this field.
	 *
	 * The last time refresh was called.
	 */
	@Deprecated
	protected long lastRefreshTime;

	/**
	 * Returns parent object, usually a folder type of resource. In the DLDI
	 * queries, the UPNP server needs to give out the parent container where
	 * the item is. The <i>parent</i> represents such a container.
	 *
	 * @return Parent object.
	 */
	public DLNAResource getParent() {
		return parent;
	}

	/**
	 * Set the parent object, usually a folder type of resource. In the DLDI
	 * queries, the UPNP server needs to give out the parent container where
	 * the item is. The <i>parent</i> represents such a container.

	 * @param parent Sets the parent object.
	 */
	public void setParent(DLNAResource parent) {
		this.parent = parent;
	}

	/**
	 * Returns the id of this resource based on the index in its parent
	 * container. Its main purpose is to be unique in the parent container.
	 *
	 * @return The id string.
	 * @since 1.50.0
	 */
	protected String getId() {
		return id;
	}

	/**
	 * Set the ID of this resource based on the index in its parent container.
	 * Its main purpose is to be unique in the parent container. The method is
	 * automatically called by addChildInternal, so most of the time it is not
	 * necessary to call it explicitly.
	 *
	 * @param id
	 * @since 1.50.0
	 * @see #addChildInternal(DLNAResource)
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * String representing this resource ID. This string is used by the UPNP
	 * ContentDirectory service. There is no hard spec on the actual numbering
	 * except for the root container that always has to be "0". In PMS the
	 * format used is <i>number($number)+</i>. A common client that expects a
	 * different format than the one used here is the XBox360. PMS translates
	 * the XBox360 queries on the fly. For more info, check
	 * http://www.mperfect.net/whsUpnp360/ .
	 *
	 * @return The resource id.
	 * @since 1.50.0
	 */
	public String getResourceId() {
		if (getId() == null) {
			return null;
		}

		if (getParent() != null) {
			return getParent().getResourceId() + '$' + getId();
		} else {
			return getId();
		}
	}

	/**
	 * @see #setId(String)
	 * @param id
	 */
	protected void setIndexId(int id) {
		setId(Integer.toString(id));
	}

	/**
	 *
	 * @return the unique id which identifies the DLNAResource relative to its parent.
	 */
	public String getInternalId() {
		return getId();
	}

	/**
	 *
	 * @return true, if this contain can have a transcode folder
	 */
	// TODO (breaking change): should be protected
	public boolean isTranscodeFolderAvailable() {
		return true;
	}

	/**
	 * Any {@link DLNAResource} needs to represent the container or item with a String.
	 *
	 * @return String to be showed in the UPNP client.
	 */
	public abstract String getName();

	public abstract String getSystemName();

	public abstract long length();

	// Ditlew
	public long length(RendererConfiguration mediaRenderer) {
		return length();
	}

	public abstract InputStream getInputStream() throws IOException;

	public abstract boolean isFolder();

	public String getDlnaContentFeatures() {
		return (dlnaspec != null ? (dlnaspec + ";") : "") + getDlnaOrgOpFlags() + ";DLNA.ORG_CI=0;DLNA.ORG_FLAGS=01700000000000000000000000000000";
	}

	public DLNAResource getPrimaryResource() {
		return first;
	}

	public DLNAResource getSecondaryResource() {
		return second;
	}

	public String getFakeParentId() {
		return fakeParentId;
	}

	public void setFakeParentId(String fakeParentId) {
		this.fakeParentId = fakeParentId;
	}

	/**
	 * @return the fake parent id if specified, or the real parent id
	 */
	public String getParentId() {
		if (getFakeParentId() != null) {
			return getFakeParentId();
		} else {
			if (getParent() != null) {
				return getParent().getResourceId();
			} else {
				return "-1";
			}
		}
	}

	public DLNAResource() {
		setSpecificType(Format.UNKNOWN);
		setChildren(new ArrayList<DLNAResource>());
		setUpdateId(1);
	}

	public DLNAResource(int specificType) {
		this();
		setSpecificType(specificType);
	}

	/**
	 * Recursive function that searches through all of the children until it finds
	 * a {@link DLNAResource} that matches the name.<p> Only used by
	 * {@link net.pms.dlna.RootFolder#addWebFolder(File webConf)
	 * addWebFolder(File webConf)} while parsing the web.conf file.
	 * @param name String to be compared the name to.
	 * @return Returns a {@link DLNAResource} whose name matches the parameter name
	 * @see #getName()
	 */
	public DLNAResource searchByName(String name) {
		for (DLNAResource child : getChildren()) {
			if (child.getName().equals(name)) {
				return child;
			}
		}

		return null;
	}

	/**
	 * @param renderer Renderer for which to check if file is supported.
	 * @return true if the given {@link net.pms.configuration.RendererConfiguration
	 *		RendererConfiguration} can understand type of media. Also returns true
	 *		if this DLNAResource is a container.
	 */
	public boolean isCompatible(RendererConfiguration renderer) {
		return getFormat() == null
			|| getFormat().isUnknown()
			|| (getFormat().isVideo() && renderer.isVideoSupported())
			|| (getFormat().isAudio() && renderer.isAudioSupported())
			|| (getFormat().isImage() && renderer.isImageSupported());
	}

	/**
	 * Adds a new DLNAResource to the child list. Only useful if this object is
	 * of the container type.
	 * <P>
	 * TODO: (botijo) check what happens with the child object. This function
	 * can and will transform the child object. If the transcode option is set,
	 * the child item is converted to a container with the real item and the
	 * transcode option folder. There is also a parser in order to get the right
	 * name and type, I suppose. Is this the right place to be doing things like
	 * these?
	 * <p>
	 * FIXME: Ideally the logic below is completely renderer-agnostic. Focus on
	 * harvesting generic data and transform it for a specific renderer as late
	 * as possible. 
	 * 
	 * @param child
	 *            DLNAResource to add to a container type.
	 */
	public void addChild(DLNAResource child) {
		// child may be null (spotted - via rootFolder.addChild() - in a misbehaving plugin
		if (child == null) {
			logger.error("A plugin has attempted to add a null child to \"{}\"", getName());
			logger.debug("Error info:", new NullPointerException("Invalid DLNA resource"));
			return;
		}

		child.setParent(this);

		if (getParent() != null) {
			setDefaultRenderer(getParent().getDefaultRenderer());
		}

		try {
			if (child.isValid()) {
				logger.trace("Adding new child \"{}\" with class \"{}\"", child.getName(), child.getClass().getName());

				if (allChildrenAreFolders && !child.isFolder()) {
					allChildrenAreFolders = false;
				}

				addChildInternal(child);

				boolean parserV2 = child.getMedia() != null && getDefaultRenderer() != null && getDefaultRenderer().isMediaParserV2();

				if (parserV2) {
					// See which mime type the renderer prefers in case it supports the media
					String mimeType = getDefaultRenderer().getFormatConfiguration().match(child.getMedia());

					if (mimeType != null) {
						// Media is streamable
						if (!FormatConfiguration.MIMETYPE_AUTO.equals(mimeType)) {
							// Override with the preferred mime type of the renderer
							logger.trace("Overriding detected mime type \"{}\" for file \"{}\" with renderer preferred mime type \"{}\"",
									child.getMedia().getMimeType(), child.getName(), mimeType);
							child.getMedia().setMimeType(mimeType);
						}

						logger.trace("File \"{}\" can be streamed with mime type \"{}\"", child.getName(), child.getMedia().getMimeType());
					} else {
						// Media is transcodable
						logger.trace("File \"{}\" can be transcoded", child.getName());
					}
				}

				if (child.getFormat() != null) {
					String configurationSkipExtensions = configuration.getDisableTranscodeForExtensions();
					String rendererSkipExtensions = null;
					
					if (getDefaultRenderer() != null) {
						rendererSkipExtensions = getDefaultRenderer().getStreamedExtensions();
					}

					// Should transcoding be skipped for this format?
					boolean skip = child.getFormat().skip(configurationSkipExtensions, rendererSkipExtensions);
					setSkipTranscode(skip);
					
					if (skip) {
						logger.trace("File \"{}\" will be forced to skip transcoding by configuration", child.getName());
					}

					if (parserV2 || (child.getFormat().transcodable() && child.getMedia() == null)) {
						if (!parserV2) {
							child.setMedia(new DLNAMediaInfo());
						}
	
						// Try to determine a player to use for transcoding.
						Player player = null;
	
						// First, try to match a player based on the name of the DLNAResource
						// or its parent. If the name ends in "[unique player id]", that player
						// is preferred.
						String name = getName();
	
						for (Player p : PlayerFactory.getAllPlayers()) {
							String end = "[" + p.id() + "]";
	
							if (name.endsWith(end)) {
								nametruncate = name.lastIndexOf(end);
								player = p;
								logger.trace("Selecting player based on name end");
								break;
							} else if (getParent() != null && getParent().getName().endsWith(end)) {
								getParent().nametruncate = getParent().getName().lastIndexOf(end);
								player = p;
								logger.trace("Selecting player based on parent name end");
								break;
							}
						}
	
						// If no preferred player could be determined from the name, try to
						// match a player based on media information and format.
						if (player == null) {
							player = PlayerFactory.getPlayer(child);
						}
	
						if (player != null && !allChildrenAreFolders) {
							String configurationForceExtensions = configuration.getForceTranscodeForExtensions();
							String rendererForceExtensions = null;
							
							if (getDefaultRenderer() != null) {
								rendererForceExtensions = getDefaultRenderer().getTranscodedExtensions();
							}

							// Should transcoding be forced for this format?
							boolean forceTranscode = child.getFormat().skip(configurationForceExtensions, rendererForceExtensions);

							if (forceTranscode) {
								logger.trace("File \"{}\" will be forced to be transcoded by configuration", child.getName());
							}

							boolean hasEmbeddedSubs = false;
	
							if (child.getMedia() != null) {
								for (DLNAMediaSubtitle s : child.getMedia().getSubtitleTracksList()) {
									hasEmbeddedSubs = (hasEmbeddedSubs || s.isEmbedded());
								}
							}
	
							boolean hasSubsToTranscode = false;
	
							if (!configuration.isDisableSubtitles()) {
								// FIXME: Why transcode if the renderer can handle embedded subs?
								hasSubsToTranscode = (configuration.isAutoloadExternalSubtitles() && child.isSrtFile()) || hasEmbeddedSubs;

								if (hasSubsToTranscode) {
									logger.trace("File \"{}\" has subs that need transcoding", child.getName());
								}
							}
	
							boolean isIncompatible = false;
	
							if (!child.getFormat().isCompatible(child.getMedia(), getDefaultRenderer())) {
								isIncompatible = true;
								logger.trace("File \"{}\" is not supported by the renderer", child.getName());
							}
	
							// Prefer transcoding over streaming if:
							// 1) the media is unsupported by the renderer, or
							// 2) there are subs to transcode
							boolean preferTranscode = isIncompatible || hasSubsToTranscode;

							// Transcode if:
							// 1) transcoding is forced by configuration, or
							// 2) transcoding is preferred and not prevented by configuration
							if (forceTranscode || (preferTranscode && !isSkipTranscode())) {
								child.setPlayer(player);

								if (parserV2) {
									logger.trace("Final verdict: \"{}\" will be transcoded with player \"{}\" with mime type \"{}\"", child.getName(), player.toString(), child.getMedia().getMimeType());
								} else {
									logger.trace("Final verdict: \"{}\" will be transcoded with player \"{}\"", child.getName(), player.toString());
								}
							} else {
								logger.trace("Final verdict: \"{}\" will be streamed", child.getName());
							}
	
							// Should the child be added to the #--TRANSCODE--# folder?
							if ((child.getFormat().isVideo() || child.getFormat().isAudio()) && child.isTranscodeFolderAvailable()) {
								// true: create (and append) the #--TRANSCODE--# folder to this
								// folder if supported/enabled and if it doesn't already exist
								VirtualFolder transcodeFolder = getTranscodeFolder(true);
								if (transcodeFolder != null) {
									VirtualFolder fileTranscodeFolder = new FileTranscodeVirtualFolder(child.getName(), null);
	
									DLNAResource newChild = child.clone();
									newChild.setPlayer(player);
									newChild.setMedia(child.getMedia());
									fileTranscodeFolder.addChildInternal(newChild);
									logger.trace("Adding \"{}\" to transcode folder for player: \"{}\"", child.getName(), player.toString());
	
									transcodeFolder.addChild(fileTranscodeFolder);
								}
							}
	
							for (ExternalListener listener : ExternalFactory.getExternalListeners()) {
								if (listener instanceof AdditionalResourceFolderListener) {
									try {
										((AdditionalResourceFolderListener) listener).addAdditionalFolder(this, child);
									} catch (Throwable t) {
										logger.error("Failed to add additional folder for listener of type: \"{}\"", listener.getClass(), t);
									}
								}
							}
						} else if (!child.getFormat().isCompatible(child.getMedia(), getDefaultRenderer()) && !child.isFolder()) {
							logger.trace("Ignoring file \"{}\" because it is not compatible with renderer \"{}\"", child.getName(), getDefaultRenderer().getRendererName());
							getChildren().remove(child);
						}
					}

					if (child.getFormat().getSecondaryFormat() != null &&
						child.getMedia() != null &&
						getDefaultRenderer() != null &&
						getDefaultRenderer().supportsFormat(child.getFormat().getSecondaryFormat())
					) {
						DLNAResource newChild = child.clone();
						newChild.setFormat(newChild.getFormat().getSecondaryFormat());
						logger.trace("Detected secondary format \"{}\" for \"{}\"", newChild.getFormat().toString(), newChild.getName());
						newChild.first = child;
						child.second = newChild;
	
						if (!newChild.getFormat().isCompatible(newChild.getMedia(), getDefaultRenderer())) {
							Player player = PlayerFactory.getPlayer(newChild);
							newChild.setPlayer(player);
							logger.trace("Secondary format \"{}\" will use player \"{}\" for \"{}\"", newChild.getFormat().toString(), child.getPlayer().name(), newChild.getName());
						}
	
						if (child.getMedia() != null && child.getMedia().isSecondaryFormatValid()) {
							addChild(newChild);
							logger.trace("Adding secondary format \"{}\" for \"{}\"", newChild.getFormat().toString(), newChild.getName());
						} else {
							logger.trace("Ignoring secondary format \"{}\" for \"{}\": invalid format", newChild.getFormat().toString(), newChild.getName());
						}
					}
				}
			}
		} catch (Throwable t) {
			logger.error("Error adding child: \"{}\"", child.getName(), t);

			child.setParent(null);
			getChildren().remove(child);
		}
	}

	/**
	 * Return the transcode folder for this resource.
	 * If PMS is configured to hide transcode folders, null is returned.
	 * If no folder exists and the create argument is false, null is returned.
	 * If no folder exists and the create argument is true, a new transcode folder is created.
	 * This method is called on the parent frolder each time a child is added to that parent
	 * (via {@link #addChild(DLNAResource)}.
	 *
	 * @param create
	 * @return the transcode virtual folder
	 */
	// XXX package-private: used by MapFile; should be protected?
	TranscodeVirtualFolder getTranscodeFolder(boolean create) {
		if (!isTranscodeFolderAvailable()) {
			return null;
		}

		if (configuration.getHideTranscodeEnabled()) {
			return null;
		}

		// search for transcode folder
		for (DLNAResource child : getChildren()) {
			if (child instanceof TranscodeVirtualFolder) {
				return (TranscodeVirtualFolder) child;
			}
		}

		if (create) {
			TranscodeVirtualFolder transcodeFolder = new TranscodeVirtualFolder(null);
			addChildInternal(transcodeFolder);
			return transcodeFolder;
		}

		return null;
	}

	/**
	 * Adds the supplied DNLA resource to the internal list of child nodes,
	 * and sets the parent to the current node. Avoids the side-effects
	 * associated with the {@link #addChild(DLNAResource)} method.
	 *
	 * @param child the DLNA resource to add to this node's list of children
	 */
	protected synchronized void addChildInternal(DLNAResource child) {
		if (child.getInternalId() != null) {
			logger.info(
				"Node ({}) already has an ID ({}), which is overridden now. The previous parent node was: {}",
				new Object[] {
					child.getClass().getName(),
					child.getResourceId(),
					child.getParent()
				}
			);
		}

		getChildren().add(child);
		child.setParent(this);

		setLastChildId(getLastChildId() + 1);
		child.setIndexId(getLastChildId());
	}

	/**
	 * First thing it does it searches for an item matching the given objectID.
	 * If children is false, then it returns the found object as the only object in the list.
	 * TODO: (botijo) This function does a lot more than this!
	 * @param objectId ID to search for.
	 * @param returnChildren State if you want all the children in the returned list.
	 * @param start
	 * @param count
	 * @param renderer Renderer for which to do the actions.
	 * @return List of DLNAResource items.
	 * @throws IOException
	 */
	public synchronized List<DLNAResource> getDLNAResources(String objectId, boolean returnChildren, int start, int count, RendererConfiguration renderer) throws IOException {
		ArrayList<DLNAResource> resources = new ArrayList<DLNAResource>();
		DLNAResource dlna = search(objectId, count, renderer);

		if (dlna != null) {
			String systemName = dlna.getSystemName();
			dlna.setDefaultRenderer(renderer);

			if (!returnChildren) {
				resources.add(dlna);
				dlna.refreshChildrenIfNeeded();
			} else {
				dlna.discoverWithRenderer(renderer, count, true);

				if (count == 0) {
					count = dlna.getChildren().size();
				}

				if (count > 0) {
					ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(count);

					int nParallelThreads = 3;
					if (dlna instanceof DVDISOFile) {
						nParallelThreads = 1; // Some DVD drives die wih 3 parallel threads
					}

					ThreadPoolExecutor tpe = new ThreadPoolExecutor(
						Math.min(count, nParallelThreads),
						count,
						20,
						TimeUnit.SECONDS,
						queue
					);

					for (int i = start; i < start + count; i++) {
						if (i < dlna.getChildren().size()) {
							final DLNAResource child = dlna.getChildren().get(i);

							if (child != null) {
								tpe.execute(child);
								resources.add(child);
							} else {
								logger.warn("null child at index {} in {}", i, systemName);
							}
						}
					}

					try {
						tpe.shutdown();
						tpe.awaitTermination(20, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						logger.error("error while shutting down thread pool executor for " + systemName, e);
					}

					logger.trace("End of analysis for {}", systemName);
				}
			}
		}

		return resources;
	}

	protected void refreshChildrenIfNeeded() {
		if (isDiscovered() && isRefreshNeeded()) {
			refreshChildren();
			notifyRefresh();
		}
	}

	/**
	 * Update the last refresh time.
	 */
	protected void notifyRefresh() {
		setLastRefreshTime(System.currentTimeMillis());
		setUpdateId(getUpdateId() + 1);
		setSystemUpdateId(getSystemUpdateId() + 1);
	}

	final protected void discoverWithRenderer(RendererConfiguration renderer, int count, boolean forced) {
		// Discover children if it hasn't been done already
		if (!isDiscovered()) {
			discoverChildren();
			boolean ready;

			if (renderer.isMediaParserV2() && renderer.isDLNATreeHack()) {
				ready = analyzeChildren(count);
			} else {
				ready = analyzeChildren(-1);
			}

			if (!renderer.isMediaParserV2() || ready) {
				setDiscovered(true);
			}

			notifyRefresh();
		} else {
			// if forced, then call the old 'refreshChildren' method
			logger.trace("discover {} refresh forced: {}", getResourceId(), forced);
			if (forced) {
				if (refreshChildren()) {
					notifyRefresh();
				}
			} else {
				// if not, then the regular isRefreshNeeded/doRefreshChildren pair.
				if (isRefreshNeeded()) {
					doRefreshChildren();
					notifyRefresh();
				}
			}
		}
	}

	@Override
	public void run() {
		if (first == null) {
			resolve();
			if (second != null) {
				second.resolve();
			}
		}
	}

	/**
	 * Recursive function that searches for a given ID.
	 *
	 * @param searchId ID to search for.
	 * @param renderer
	 * @param count
	 * @return Item found, or null otherwise.
	 * @see #getId()
	 */
	public DLNAResource search(String searchId, int count, RendererConfiguration renderer) {
		if (getId() != null && searchId != null) {
			String[] indexPath = searchId.split("\\$", 2);
			if (getId().equals(indexPath[0])) {
				if (indexPath.length == 1 || indexPath[1].length() == 0) {
					return this;
				} else {
					discoverWithRenderer(renderer, count, false);

					for (DLNAResource file : getChildren()) {
						DLNAResource found = file.search(indexPath[1], count, renderer);
						if (found != null) {
							return found;
						}
					}
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * TODO: (botijo) What is the intention of this function? Looks like a prototype to be overloaded.
	 */
	public void discoverChildren() { }

	/**
	 * TODO: (botijo) What is the intention of this function? Looks like a prototype to be overloaded.
	 * @param count
	 * @return Returns true
	 */
	public boolean analyzeChildren(int count) {
		return true;
	}

	/**
	 * Reload the list of children.
	 */
	public void doRefreshChildren() { }

	/**
	 * @return true, if the container is changed, so refresh is needed.
	 * This could be called a lot of times.
	 */
	public boolean isRefreshNeeded() {
		return false;
	}

	/**
	 * This method gets called only for the browsed folder, and not for the
	 * parent folders. (And in the media library scan step too). Override in
	 * plugins when you do not want to implement proper change tracking, and
	 * you do not care if the hierarchy of nodes getting invalid between.
	 *
	 * @return True when a refresh is needed, false otherwise.
	 */
	public boolean refreshChildren() {
		if (isRefreshNeeded()) {
			doRefreshChildren();
			return true;
		}

		return false;
	}

	/**
	 * @deprecated Use {@link #resolveFormat()} instead.
	 */
	@Deprecated
	protected void checktype() {
		resolveFormat();
	}

	/**
	 * Sets the resource's {@link net.pms.formats.Format} according to its filename
	 * if it isn't set already.
	 * @since 1.90.0
	 */
	protected void resolveFormat() {
		if (getFormat() == null) {
			setFormat(FormatFactory.getAssociatedFormat(getSystemName()));
		}

		if (getFormat() != null && getFormat().isUnknown()) {
			getFormat().setType(getSpecificType());
		}
	}

	/**
	 * Hook to lazily initialise immutable resources e.g. ISOs, zip files &amp;c.
	 *
	 * @since 1.90.0
	 * @see #resolve()
	 */
	protected void resolveOnce() { }

	/**
	 * Resolve events are hooks that allow DLNA resources to perform various forms
	 * of initialisation when navigated to or streamed i.e. they function as lazy
	 * constructors.
	 *
	 * This method is called by request handlers for a) requests for a stream
	 * or b) content directory browsing i.e. for potentially every request for a file or
	 * folder the renderer hasn't cached. Many resource types are immutable (e.g. playlists,
	 * zip files, DVD ISOs &amp;c.) and only need to respond to this event once.
	 * Most resource types don't "subscribe" to this event at all. This default implementation
	 * provides hooks for immutable resources and handles the event for resource types that
	 * don't care about it. The rest override this method and handle it accordingly. Currently,
	 * the only resource type that overrides it is {@link RealFile}.
	 *
	 * Note: resolving a resource once (only) doesn't prevent children being added to or
	 * removed from it (if supported). There are other mechanisms for that e.g.
	 * {@link #doRefreshChildren()} (see {@link Feed} for an example).
	 */
	public synchronized void resolve() {
		if (!resolved) {
			resolveOnce();
			// if resolve() isn't overridden, this file/folder is immutable
			// (or doesn't respond to resolve events, which amounts to the
			// same thing), so don't spam it with this event again.
			resolved = true;
		}
	}

	// Ditlew
	/**
	 * Returns the display name for the default renderer.
	 *
	 * @return The display name.
	 * @see #getDisplayName(RendererConfiguration)
	 */
	public String getDisplayName() {
		return getDisplayName(getDefaultRenderer());
	}

	// helper method for getDisplayName
	private boolean anyStringIsNotBlank(String... strings) {
		for (String string : strings) {
			if (isNotBlank(string)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This hook allows subclasses to add resource-specific variables
	 * to the String -&gt; Object map exported to the display name
	 * template in {@link #getDisplayName(RendererConfiguration)}.
	 *
	 * @since 1.90.0
	 * @param vars the current map of variables to export to the template
	 */
	protected void finalizeDisplayNameVars(Map<String, Object> vars) { }

	/**
	 * Returns the string for this resource that will be displayed on the
	 * renderer. The name is formatted based on the PMS.conf settings
	 * for filename_format_short and filename_format_long, which can
	 * be overridden on a per-renderer basis by ShortFilenameFormat
	 * and LongFilenameFormat respectively.
	 *
	 * This allows the same resource to be displayed with different
	 * display names on different renderers.
	 *
	 * See the "Filename templates" section of PMS.conf for full details.
	 *
	 * @param renderer the renderer the resource will be displayed on.
	 * @return the resource's display name.
	 */
	public String getDisplayName(RendererConfiguration renderer) {
		if (displayName != null) { // cached
			return displayName;
		}

		// Chapter virtual folder ignores formats and only displays the start time
		if (getSplitRange().isEndLimitAvailable()) {
			displayName = ">> " + DLNAMediaInfo.getDurationString(getSplitRange().getStart());
			return displayName;
		}

		// Is this still relevant? The player name already contains "AviSynth"
		if (isAvisynth()) {
			displayName = (getPlayer() != null ? ("[" + getPlayer().name()) : "") + " + AviSynth]";
			return displayName;
		}

		String template;
		String audioLangFullName = "";
		String audioLangShortName = "";
		String audioFlavor = "";
		String audioCodec = "";
		String dvdTrackDuration = "";
		String engineFullName = "";
		String engineShortName = "";
		String filenameWithExtension = "";
		String filenameWithoutExtension = "";
		String subLangFullName = "";
		String subLangShortName = "";
		String subType = "";
		String subFlavor = "";
		String externalSubs = "";

		// the display names of #--TRANSCODE--# folder files may need to
		// pack in a lot of information (e.g. engine, audio track, subtitle
		// track) so we use a compact format to try to prevent them being
		// truncated or scrolling off the screen
		boolean useShortFormat = isNoName();

		// Determine the format
		if (renderer != null) {
			if (useShortFormat) {
				template = renderer.getShortFilenameFormat();
			} else {
				template = renderer.getLongFilenameFormat();
			}
		} else {
			if (useShortFormat) {
				template = configuration.getShortFilenameFormat();
			} else {
				template = configuration.getLongFilenameFormat();
			}
		}

		// Handle file name
		if (!isNoName()) {
			filenameWithExtension = getName();
			filenameWithoutExtension = FilenameUtils.getBaseName(filenameWithExtension);

			// Check if file extensions are configured to be hidden
			if (this instanceof RealFile && configuration.isHideExtensions() && !isFolder()) {
				filenameWithExtension = filenameWithoutExtension;
			}
		}

		// Handle engine name
		if (isNoName() || !configuration.isHideEngineNames()) {
			if (getPlayer() != null) {
				engineFullName = getPlayer().name();
				engineShortName = abbreviate(engineFullName);
			} else if (isNoName()) {
				engineFullName = Messages.getString("DLNAResource.1");
				engineShortName = Messages.getString("DLNAResource.2");
			}
		}

		// Handle DVD track duration
		if (
			renderer != null
			&& renderer.isShowDVDTitleDuration()
			&& getMedia() != null
			&& getMedia().getDvdtrack() > 0
		) {
			dvdTrackDuration = getMedia().getDurationString();
		}

		// Handle external subtitles
		if (isSrtFile()
			&& (getMediaAudio() == null
			&& getMediaSubtitle() == null)
			&& (getPlayer() == null || getPlayer().isExternalSubtitlesSupported())
		) {
			externalSubs = Messages.getString("DLNAResource.0");
		}

		// Handle audio
		if (getMediaAudio() != null) {
			audioCodec = getMediaAudio().getAudioCodec();
			audioLangFullName = getMediaAudio().getLangFullName();
			audioLangShortName = getMediaAudio().getLang();

			if ((getMediaAudio().getFlavor() != null && renderer != null && renderer.isShowAudioMetadata())) {
				audioFlavor = getMediaAudio().getFlavor();
			}
		}

		// Handle subtitle
		if (getMediaSubtitle() != null && getMediaSubtitle().getId() != -1) {
			subType = getMediaSubtitle().getType().getDescription();
			subLangFullName = getMediaSubtitle().getLangFullName();
			subLangShortName = getMediaSubtitle().getLang();

			if (getMediaSubtitle().getFlavor() != null && renderer != null && renderer.isShowSubMetadata()) {
				subFlavor = getMediaSubtitle().getFlavor();
			}
		}

		// define any extra variables to be exported
		boolean isFolder = isFolder();
		boolean extra = anyStringIsNotBlank(dvdTrackDuration, engineShortName, engineFullName, externalSubs, subType);

		Map<String, Object> vars = new HashMap<String, Object>();

		vars.put("lt", "<");
		vars.put("gt", ">");

		vars.put("aLabel", Messages.getString("DLNAResource.3"));
		vars.put("sLabel", Messages.getString("DLNAResource.4"));

		vars.put("aCodec", audioCodec);
		vars.put("aFlavor", audioFlavor);
		vars.put("aFull", audioLangFullName);
		vars.put("aShort", audioLangShortName);
		vars.put("dvdLen", dvdTrackDuration);
		vars.put("eFull", engineFullName);
		vars.put("eShort", engineShortName);
		vars.put("fFull", filenameWithExtension);
		vars.put("fShort", filenameWithoutExtension);
		vars.put("sExt", externalSubs);
		vars.put("sFlavor", subFlavor);
		vars.put("sFull", subLangFullName);
		vars.put("sShort", subLangShortName);
		vars.put("sType", subType);

		vars.put("extra", extra);
		vars.put("isFile", !isFolder);
		vars.put("isFolder", isFolder);

		// allow subclasses to add resource-specific vars
		// currently only used by DVDISOFile
		finalizeDisplayNameVars(vars);

		displayName = displayNameTemplateEngine.transform(template, vars);
		displayName = displayName.replaceAll("\\s+", " ");
		displayName = displayName.trim();

		return displayName;
	}

	/**
	 * Prototype for returning URLs.
	 *
	 * @return An empty URL
	 */
	protected String getFileURL() {
		return getURL("");
	}

	/**
	 * @return Returns a URL pointing to an image representing the item. If
	 * none is available, "thumbnail0000.png" is used.
	 */
	protected String getThumbnailURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(PMS.get().getServer().getURL());
		sb.append("/images/");
		String id = null;

		if (getMediaAudio() != null) {
			id = getMediaAudio().getLang();
		}

		if (getMediaSubtitle() != null && getMediaSubtitle().getId() != -1) {
			id = getMediaSubtitle().getLang();
		}

		if ((getMediaSubtitle() != null || getMediaAudio() != null) && StringUtils.isBlank(id)) {
			id = DLNAMediaLang.UND;
		}

		if (id != null) {
			String code = Iso639.getISO639_2Code(id.toLowerCase());
			sb.append("codes/").append(code).append(".png");
			return sb.toString();
		}

		if (isAvisynth()) {
			sb.append("logo-avisynth.png");
			return sb.toString();
		}

		return getURL("thumbnail0000");
	}

	/**
	 * @param prefix
	 * @return Returns a URL for a given media item. Not used for container types.
	 */
	protected String getURL(String prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append(PMS.get().getServer().getURL());
		sb.append("/get/");
		sb.append(getResourceId()); //id
		sb.append("/");
		sb.append(prefix);
		sb.append(encode(getName()));
		return sb.toString();
	}

	/**
	 * Transforms a String to UTF-8.
	 *
	 * @param s
	 * @return Transformed string s in UTF-8 encoding.
	 */
	private static String encode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.debug("Caught exception", e);
		}
		return "";
	}

	/**
	 * @return Number of children objects. This might be used in the DLDI
	 * response, as some renderers might not have enough memory to hold the
	 * list for all children.
	 */
	public int childrenNumber() {
		if (getChildren() == null) {
			return 0;
		}
		return getChildren().size();
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected DLNAResource clone() {
		DLNAResource o = null;
		try {
			o = (DLNAResource) super.clone();
			o.setId(null);
			// clear the cached display name
			o.displayName = null;
			// make sure clones (typically #--TRANSCODE--# folder files)
			// have the option to respond to resolve events
			o.resolved = false;
		} catch (CloneNotSupportedException e) {
			logger.error(null, e);
		}

		return o;
	}

	// this shouldn't be public
	@Deprecated
	public String getFlags() {
		return getDlnaOrgOpFlags();
	}

	// permit the renderer to seek by time, bytes or both
	private String getDlnaOrgOpFlags() {
		return "DLNA.ORG_OP=" + dlnaOrgOpFlags;
	}

	/**
	 * @deprecated Use {@link #getDidlString(RendererConfiguration)} instead.
	 *
	 * @param mediaRenderer
	 * @return
	 */
	@Deprecated
	public final String toString(RendererConfiguration mediaRenderer) {
		return getDidlString(mediaRenderer);
	}

	/**
	 * Returns an XML (DIDL) representation of the DLNA node. It gives a
	 * complete representation of the item, with as many tags as available.
	 * Recommendations as per UPNP specification are followed where possible.
	 * 
	 * @param mediaRenderer
	 *            Media Renderer for which to represent this information. Useful
	 *            for some hacks.
	 * @return String representing the item. An example would start like this:
	 *         {@code <container id="0$1" childCount="1" parentID="0" restricted="true">}
	 */
	public final String getDidlString(RendererConfiguration mediaRenderer) {
		StringBuilder sb = new StringBuilder();

		if (isFolder()) {
			openTag(sb, "container");
		} else {
			openTag(sb, "item");
		}

		addAttribute(sb, "id", getResourceId());

		if (isFolder()) {
			if (!isDiscovered() && childrenNumber() == 0) {
				//  When a folder has not been scanned for resources, it will automatically have zero children.
				//  Some renderers like XBMC will assume a folder is empty when encountering childCount="0" and
				//  will not display the folder. By returning childCount="1" these renderers will still display
				//  the folder. When it is opened, its children will be discovered and childrenNumber() will be
				//  set to the right value.
				addAttribute(sb, "childCount", 1);
			} else {
				addAttribute(sb, "childCount", childrenNumber());
			}
		}
		addAttribute(sb, "parentID", getParentId());
		addAttribute(sb, "restricted", "true");
		endTag(sb);

		final DLNAMediaAudio firstAudioTrack = getMedia() != null ? getMedia().getFirstAudioTrack() : null;
		if (firstAudioTrack != null && isNotBlank(firstAudioTrack.getSongname())) {
			addXMLTagAndAttribute(
				sb,
				"dc:title",
				encodeXML(firstAudioTrack.getSongname() + (getPlayer() != null && !configuration.isHideEngineNames() ? (" [" + getPlayer().name() + "]") : ""))
			);
		} else { // Ditlew - org
			// Ditlew
			addXMLTagAndAttribute(
				sb,
				"dc:title",
				encodeXML(((isFolder() || getPlayer() == null) ? getDisplayName(mediaRenderer) : mediaRenderer.getUseSameExtension(getDisplayName(mediaRenderer))))
			);
		}

		if (firstAudioTrack != null) {
			if (isNotBlank(firstAudioTrack.getAlbum())) {
				addXMLTagAndAttribute(sb, "upnp:album", encodeXML(firstAudioTrack.getAlbum()));
			}

			if (isNotBlank(firstAudioTrack.getArtist())) {
				addXMLTagAndAttribute(sb, "upnp:artist", encodeXML(firstAudioTrack.getArtist()));
				addXMLTagAndAttribute(sb, "dc:creator", encodeXML(firstAudioTrack.getArtist()));
			}

			if (isNotBlank(firstAudioTrack.getGenre())) {
				addXMLTagAndAttribute(sb, "upnp:genre", encodeXML(firstAudioTrack.getGenre()));
			}

			if (firstAudioTrack.getTrack() > 0) {
				addXMLTagAndAttribute(sb, "upnp:originalTrackNumber", "" + firstAudioTrack.getTrack());
			}
		}

		if (!isFolder()) {
			int indexCount = 1;

			if (mediaRenderer.isDLNALocalizationRequired()) {
				indexCount = getDLNALocalesCount();
			}

			for (int c = 0; c < indexCount; c++) {
				openTag(sb, "res");

				/*
					DLNA.ORG_OP flags

					Two booleans (binary digits) which determine what transport operations the renderer is allowed to
					perform (in the form of HTTP request headers): the first digit allows the renderer to send
					TimeSeekRange.DLNA.ORG (seek by time) headers; the second allows it to send RANGE (seek by byte)
					headers.

						00 - no seeking (or even pausing) allowed
						01 - seek by byte
						10 - seek by time
						11 - seek by both

					See here for an example of how these options can be mapped to keys on the renderer's controller:
					http://www.ps3mediaserver.org/forum/viewtopic.php?f=2&t=2908&p=12550#p12550

					Note that seek-by-byte is the preferred option for streamed files [1] and seek-by-time is the
					preferred option for transcoded files.

					[1] see http://www.ps3mediaserver.org/forum/viewtopic.php?f=6&t=15841&p=76201#p76201

					seek-by-time requires a) support by the renderer (via the SeekByTime renderer conf option)
					and b) support by the transcode engine.

					The seek-by-byte fallback doesn't work well with transcoded files [2], but it's better than
					disabling seeking (and pausing) altogether.

					[2] http://www.ps3mediaserver.org/forum/viewtopic.php?f=6&t=3507&p=16567#p16567 (bottom post)
				*/

				dlnaOrgOpFlags = "01"; // seek by byte (exclusive)

				if (mediaRenderer.isSeekByTime() && getPlayer() != null && getPlayer().isTimeSeekable()) {
					/*
						Some renderers - e.g. the PS3 and Panasonic TVs - behave erratically when
						transcoding if we keep the default seek-by-byte permission on when permitting
						seek-by-time: http://www.ps3mediaserver.org/forum/viewtopic.php?f=6&t=15841

						It's not clear if this is a bug in the DLNA libraries of these renderers or a bug
						in PMS, but setting an option in the renderer conf that disables seek-by-byte when
						we permit seek-by-time - e.g.:

							SeekByTime = exclusive

						- works around it.
					*/

					/*
						TODO (e.g. in a beta release): set seek-by-time (exclusive) here for *all* renderers:
						seek-by-byte isn't needed here (both the renderer and the engine support seek-by-time)
						and may be buggy on other renderers than the ones we currently handle.

						In the unlikely event that a renderer *requires* seek-by-both here, it can
						opt in with (e.g.):

							SeekByTime = both
					*/
					if (mediaRenderer.isSeekByTimeExclusive()) {
						dlnaOrgOpFlags = "10"; // seek by time (exclusive)
					} else {
						dlnaOrgOpFlags = "11"; // seek by both
					}
				}

				addAttribute(sb, "xmlns:dlna", "urn:schemas-dlna-org:metadata-1-0/");

				// FIXME: There is a flaw here. In addChild(DLNAResource) the mime type
				// is determined for the default renderer. This renderer may rewrite the
				// mime type based on its configuration. Looking up that mime type is
				// not guaranteed to return a match for another renderer.
				String mime = mediaRenderer.getMimeType(mimeType());

				if (mime == null) {
					// FIXME: Setting the default to "video/mpeg" leaves a lot of audio files in the cold.
					mime = "video/mpeg";
				}

				// XXX remove
				logger.trace("DIDL mime type = " + mime + ", mimeType() = " + mimeType() + " for " + getName());

				dlnaspec = null;

				if (mediaRenderer.isDLNAOrgPNUsed()) {
					if (mediaRenderer.isPS3()) {
						if (mime.equals("video/x-divx")) {
							dlnaspec = "DLNA.ORG_PN=AVI";
						} else if (mime.equals("video/x-ms-wmv") && getMedia() != null && getMedia().getHeight() > 700) {
							dlnaspec = "DLNA.ORG_PN=WMVHIGH_PRO";
						}
					} else {
						if (mime.equals("video/mpeg")) {
							dlnaspec = "DLNA.ORG_PN=" + getMPEG_PS_PALLocalizedValue(c);

							if (getPlayer() != null) {
								// Do we have some mpegts to offer?
								boolean mpegTsMux = TsMuxeRVideo.ID.equals(getPlayer().id()) || VideoLanVideoStreaming.ID.equals(getPlayer().id());
								boolean isMuxableResult = getMedia().isMuxable(mediaRenderer);
								if (!mpegTsMux) {
									mpegTsMux = MEncoderVideo.ID.equals(getPlayer().id()) && mediaRenderer.isTranscodeToMPEGTSAC3();
								}
								if (mpegTsMux) {
									dlnaspec = "DLNA.ORG_PN=" + getMPEG_TS_SD_EU_ISOLocalizedValue(c);
									if (getMedia().isH264()
											&& !VideoLanVideoStreaming.ID.equals(getPlayer().id())
											&& isMuxableResult) {
										dlnaspec = "DLNA.ORG_PN=AVC_TS_HD_24_AC3_ISO";
									}
								}
							} else if (getMedia() != null) {
								if (getMedia().isMpegTS()) {
										dlnaspec = "DLNA.ORG_PN=" + getMPEG_TS_SD_EULocalizedValue(c);
									if (getMedia().isH264()) {
										dlnaspec = "DLNA.ORG_PN=AVC_TS_HD_50_AC3";
									}
								}
							}
						} else if (mime.equals("video/vnd.dlna.mpeg-tts")) {
							// patters - on Sony BDP m2ts clips aren't listed without this
							dlnaspec = "DLNA.ORG_PN=" + getMPEG_TS_SD_EULocalizedValue(c);
						} else if (mime.equals("image/jpeg")) {
							dlnaspec = "DLNA.ORG_PN=JPEG_LRG";
						} else if (mime.equals("audio/mpeg")) {
							dlnaspec = "DLNA.ORG_PN=MP3";
						} else if (mime.substring(0, 9).equals("audio/L16") || mime.equals("audio/wav")) {
							dlnaspec = "DLNA.ORG_PN=LPCM";
						}
					}

					if (dlnaspec != null) {
						dlnaspec = "DLNA.ORG_PN=" + mediaRenderer.getDLNAPN(dlnaspec.substring(12));
					}
				}

				String tempString = "http-get:*:" + mime + ":" + (dlnaspec != null ? (dlnaspec + ";") : "") + getDlnaOrgOpFlags();
				addAttribute(sb, "protocolInfo", tempString);

				if (getFormat() != null && getFormat().isVideo() && getMedia() != null && getMedia().isMediaparsed()) {
					if (getPlayer() == null && getMedia() != null) {
						addAttribute(sb, "size", getMedia().getSize());
					} else {
						long transcoded_size = mediaRenderer.getTranscodedSize();
						if (transcoded_size != 0) {
							addAttribute(sb, "size", transcoded_size);
						}
					}
					if (getMedia().getDuration() != null) {
						if (getSplitRange().isEndLimitAvailable()) {
							addAttribute(sb, "duration", DLNAMediaInfo.getDurationString(getSplitRange().getDuration()));
						} else {
							addAttribute(sb, "duration", getMedia().getDurationString());
						}
					}
					if (getMedia().getResolution() != null) {
						addAttribute(sb, "resolution", getMedia().getResolution());
					}
					addAttribute(sb, "bitrate", getMedia().getRealVideoBitrate());
					if (firstAudioTrack != null) {
						if (firstAudioTrack.getAudioProperties().getNumberOfChannels() > 0) {
							addAttribute(sb, "nrAudioChannels", firstAudioTrack.getAudioProperties().getNumberOfChannels());
						}
						if (firstAudioTrack.getSampleFrequency() != null) {
							addAttribute(sb, "sampleFrequency", firstAudioTrack.getSampleFrequency());
						}
					}
				} else if (getFormat() != null && getFormat().isImage()) {
					if (getMedia() != null && getMedia().isMediaparsed()) {
						addAttribute(sb, "size", getMedia().getSize());
						if (getMedia().getResolution() != null) {
							addAttribute(sb, "resolution", getMedia().getResolution());
						}
					} else {
						addAttribute(sb, "size", length());
					}
				} else if (getFormat() != null && getFormat().isAudio()) {
					if (getMedia() != null && getMedia().isMediaparsed()) {
						addAttribute(sb, "bitrate", getMedia().getBitrate());
						if (getMedia().getDuration() != null) {
							addAttribute(sb, "duration", DLNAMediaInfo.getDurationString(getMedia().getDuration()));
						}
						if (firstAudioTrack != null && firstAudioTrack.getSampleFrequency() != null) {
							addAttribute(sb, "sampleFrequency", firstAudioTrack.getSampleFrequency());
						}
						if (firstAudioTrack != null) {
							addAttribute(sb, "nrAudioChannels", firstAudioTrack.getAudioProperties().getNumberOfChannels());
						}

						if (getPlayer() == null) {
							addAttribute(sb, "size", getMedia().getSize());
						} else {
							// Calculate WAV size
							if (firstAudioTrack != null) {
								int defaultFrequency = mediaRenderer.isTranscodeAudioTo441() ? 44100 : 48000;
								if (!configuration.isAudioResample()) {
									try {
										// FIXME: Which exception could be thrown here?
										defaultFrequency = firstAudioTrack.getSampleRate();
									} catch (Exception e) {
										logger.debug("Caught exception", e);
									}
								}
								int na = firstAudioTrack.getAudioProperties().getNumberOfChannels();
								if (na > 2) { // No 5.1 dump in MPlayer
									na = 2;
								}
								int finalSize = (int) (getMedia().getDurationInSeconds() * defaultFrequency * 2 * na);
								logger.trace("Calculated size for {}: {}", getSystemName(), finalSize);
								addAttribute(sb, "size", finalSize);
							}
						}
					} else {
						addAttribute(sb, "size", length());
					}
				} else {
					addAttribute(sb, "size", DLNAMediaInfo.TRANS_SIZE);
					addAttribute(sb, "duration", "09:59:59");
					addAttribute(sb, "bitrate", "1000000");
				}
				endTag(sb);
				sb.append(getFileURL());
				closeTag(sb, "res");
			}
		}

		appendThumbnail(mediaRenderer, sb);

		if (getLastModified() > 0) {
			addXMLTagAndAttribute(sb, "dc:date", sdfDate.format(new Date(getLastModified())));
		}

		String uclass;
		if (first != null && getMedia() != null && !getMedia().isSecondaryFormatValid()) {
			uclass = "dummy";
		} else {
			if (isFolder()) {
				uclass = "object.container.storageFolder";
				boolean xbox = mediaRenderer.isXBOX();
				if (xbox && getFakeParentId() != null && getFakeParentId().equals("7")) {
					uclass = "object.container.album.musicAlbum";
				} else if (xbox && getFakeParentId() != null && getFakeParentId().equals("6")) {
					uclass = "object.container.person.musicArtist";
				} else if (xbox && getFakeParentId() != null && getFakeParentId().equals("5")) {
					uclass = "object.container.genre.musicGenre";
				} else if (xbox && getFakeParentId() != null && getFakeParentId().equals("F")) {
					uclass = "object.container.playlistContainer";
				}
			} else if (getFormat() != null && getFormat().isVideo()) {
				uclass = "object.item.videoItem";
			} else if (getFormat() != null && getFormat().isImage()) {
				uclass = "object.item.imageItem.photo";
			} else if (getFormat() != null && getFormat().isAudio()) {
				uclass = "object.item.audioItem.musicTrack";
			} else {
				uclass = "object.item.videoItem";
			}
		}
		addXMLTagAndAttribute(sb, "upnp:class", uclass);

		if (isFolder()) {
			closeTag(sb, "container");
		} else {
			closeTag(sb, "item");
		}
		return sb.toString();
	}

	/**
	 * Generate and append the response for the thumbnail based on the
	 * configuration of the renderer.
	 *
	 * @param mediaRenderer The renderer configuration.
	 * @param sb            The StringBuilder to append the response to.
	 */
	private void appendThumbnail(RendererConfiguration mediaRenderer, StringBuilder sb) {
		final String thumbURL = getThumbnailURL();
		final boolean addThumbnailAsResElement = isFolder() || mediaRenderer.getThumbNailAsResource() || mediaRenderer.isForceJPGThumbnails();

		if (isNotBlank(thumbURL)) {
			if (addThumbnailAsResElement) {
				// Samsung 2012 (ES and EH) models do not recognize the "albumArtURI" element. Instead,
				// the "res" element should be used.
				// Also use "res" when faking JPEG thumbs.
				openTag(sb, "res");

				if (getThumbnailContentType().equals(PNG_TYPEMIME) && !mediaRenderer.isForceJPGThumbnails()) {
					addAttribute(sb, "protocolInfo", "http-get:*:image/png:DLNA.ORG_PN=PNG_TN");
				} else {
					addAttribute(sb, "protocolInfo", "http-get:*:image/jpeg:DLNA.ORG_PN=JPEG_TN");
				}

				endTag(sb);
				sb.append(thumbURL);
				closeTag(sb, "res");
			} else {
				// Renderers that can handle the "albumArtURI" element.
				openTag(sb, "upnp:albumArtURI");
				addAttribute(sb, "xmlns:dlna", "urn:schemas-dlna-org:metadata-1-0/");

				if (getThumbnailContentType().equals(PNG_TYPEMIME)) {
					addAttribute(sb, "dlna:profileID", "PNG_TN");
				} else {
					addAttribute(sb, "dlna:profileID", "JPEG_TN");
				}

				endTag(sb);
				sb.append(thumbURL);
				closeTag(sb, "upnp:albumArtURI");
			}
		}
	}

	private String getRequestId(String rendererId) {
		return String.format("%s|%x|%s", rendererId, hashCode(), getSystemName());
	}

	/**
	 * Plugin implementation. When this item is going to play, it will notify all
	 * the StartStopListener objects available.
	 * @see StartStopListener
	 */
	public void startPlaying(final String rendererId) {
		final String requestId = getRequestId(rendererId);
		synchronized (requestIdToRefcount) {
			Integer temp = requestIdToRefcount.get(requestId);
			if (temp == null) {
				temp = 0;
			}

			final Integer refCount = temp;
			requestIdToRefcount.put(requestId, refCount + 1);

			if (refCount == 0) {
				final DLNAResource self = this;
				Runnable r = new Runnable() {
					@Override
					public void run() {
						InetAddress rendererIpAddress;
						try {
							rendererIpAddress = InetAddress.getByName(rendererId);
							RendererConfiguration renderer = RendererConfiguration.getRendererConfigurationBySocketAddress(rendererIpAddress);
							String rendererName = "unknown renderer";
							try {
								rendererName = renderer.getRendererName();
							} catch (NullPointerException e) { }
							logger.info("Started sending {} to {} on {}", getSystemName(), rendererName, rendererId);
						} catch (UnknownHostException ex) {
							logger.debug("" + ex);
						}

						for (final ExternalListener listener : ExternalFactory.getExternalListeners()) {
							if (listener instanceof StartStopListener) {
								// run these asynchronously for slow handlers (e.g. logging, scrobbling)
								// TODO use an event bus to fire these
								Runnable fireStartStopEvent = new Runnable() {
									@Override
									public void run() {
										try {
											((StartStopListener) listener).nowPlaying(getMedia(), self);
										} catch (Throwable t) {
											logger.error("Notification of startPlaying event failed for StartStopListener {}", listener.getClass(), t);
										}
									}
								};
								new Thread(fireStartStopEvent, "StartPlaying Event for " + listener.name()).start();
							}
						}
					}
				};

				new Thread(r, "StartPlaying Event").start();
			}
		}
	}

	/**
	 * Plugin implementation. When this item is going to stop playing, it will notify all the StartStopListener
	 * objects available.
	 * @see StartStopListener
	 */
	public void stopPlaying(final String rendererId) {
		final DLNAResource self = this;
		final String requestId = getRequestId(rendererId);
		Runnable defer = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(STOP_PLAYING_DELAY);
				} catch (InterruptedException e) {
					logger.error("stopPlaying sleep interrupted", e);
				}

				synchronized (requestIdToRefcount) {
					final Integer refCount = requestIdToRefcount.get(requestId);
					assert refCount != null;
					assert refCount > 0;
					requestIdToRefcount.put(requestId, refCount - 1);

					Runnable r = new Runnable() {
						@Override
						public void run() {
							if (refCount == 1) {
								InetAddress rendererIpAddress;
								try {
									rendererIpAddress = InetAddress.getByName(rendererId);
									RendererConfiguration renderer = RendererConfiguration.getRendererConfigurationBySocketAddress(rendererIpAddress);
									String rendererName = "unknown renderer";
									try {
										rendererName = renderer.getRendererName();
									} catch (NullPointerException e) { }
									logger.info("Stopped sending {} to {} on {}", getSystemName(), rendererName, rendererId);
								} catch (UnknownHostException ex) {
									logger.debug("" + ex);
								}

								PMS.get().getFrame().setStatusLine("");

								for (final ExternalListener listener : ExternalFactory.getExternalListeners()) {
									if (listener instanceof StartStopListener) {
										// run these asynchronously for slow handlers (e.g. logging, scrobbling)
										// TODO use an event bus to fire these
										Runnable fireStartStopEvent = new Runnable() {
											@Override
											public void run() {
												try {
													((StartStopListener) listener).donePlaying(getMedia(), self);
												} catch (Throwable t) {
													logger.error("Notification of donePlaying event failed for StartStopListener {}", listener.getClass(), t);
												}
											}
										};
										new Thread(fireStartStopEvent, "StopPlaying Event for " + listener.name()).start();
									}
								}
							}
						}
					};

					new Thread(r, "StopPlaying Event").start();
				}
			}
		};

		new Thread(defer, "StopPlaying Event Deferrer").start();
	}

	/**
	 * Returns an InputStream of this DLNAResource that starts at a given time, if possible. Very useful if video chapters are being used.
	 * @param range
	 * @param mediarenderer
	 * @return The inputstream
	 * @throws IOException
	 */
	public InputStream getInputStream(Range range, RendererConfiguration mediarenderer) throws IOException {
		logger.trace("Asked stream chunk : " + range + " of " + getName() + " and player " + getPlayer());

		// shagrath: small fix, regression on chapters
		boolean timeseek_auto = false;
		// Ditlew - WDTV Live
		// Ditlew - We convert byteoffset to timeoffset here. This needs the stream to be CBR!
		int cbr_video_bitrate = mediarenderer.getCBRVideoBitrate();
		long low = range.isByteRange() && range.isStartOffsetAvailable() ? range.asByteRange().getStart() : 0;
		long high = range.isByteRange() && range.isEndLimitAvailable() ? range.asByteRange().getEnd() : -1;
		Range.Time timeRange = range.createTimeRange();

		if (getPlayer() != null && low > 0 && cbr_video_bitrate > 0) {
			int used_bit_rated = (int) ((cbr_video_bitrate + 256) * 1024 / 8 * 1.04); // 1.04 = container overhead
			if (low > used_bit_rated) {
				timeRange.setStart((double) (low / (used_bit_rated)));
				low = 0;

				// WDTV Live - if set to TS it asks multiple times and ends by
				// asking for an invalid offset which kills MEncoder
				if (timeRange.getStartOrZero() > getMedia().getDurationInSeconds()) {
					return null;
				}

				// Should we rewind a little (in case our overhead isn't accurate enough)
				int rewind_secs = mediarenderer.getByteToTimeseekRewindSeconds();
				timeRange.rewindStart(rewind_secs);

				// shagrath:
				timeseek_auto = true;
			}
		}

		// determine source of the stream
		if (getPlayer() == null) {
			// no transcoding
			if (this instanceof IPushOutput) {
				PipedOutputStream out = new PipedOutputStream();
				InputStream fis = new PipedInputStream(out);
				((IPushOutput) this).push(out);

				if (low > 0) {
					fis.skip(low);
				}
				// http://www.ps3mediaserver.org/forum/viewtopic.php?f=11&t=12035
				fis = wrap(fis, high, low);

				return fis;
			}

			InputStream fis;
			if (getFormat() != null && getFormat().isImage() && getMedia() != null && getMedia().getOrientation() > 1 && mediarenderer.isAutoRotateBasedOnExif()) {
				// seems it's a jpeg file with an orientation setting to take care of
				fis = ImagesUtil.getAutoRotateInputStreamImage(getInputStream(), getMedia().getOrientation());
				if (fis == null) { // error, let's return the original one
					fis = getInputStream();
				}
			} else {
				fis = getInputStream();
			}

			if (fis != null) {
				if (low > 0) {
					fis.skip(low);
				}

				// http://www.ps3mediaserver.org/forum/viewtopic.php?f=11&t=12035
				fis = wrap(fis, high, low);

				if (timeRange.getStartOrZero() > 0 && this instanceof RealFile) {
					fis.skip(MpegUtil.getPositionForTimeInMpeg(((RealFile) this).getFile(), (int) timeRange.getStartOrZero() ));
				}
			}
			return fis;
		} else {
			// pipe transcoding result
			OutputParams params = new OutputParams(configuration);
			params.aid = getMediaAudio();
			params.sid = getMediaSubtitle();
			params.mediaRenderer = mediarenderer;
			timeRange.limit(getSplitRange());
			params.timeseek = timeRange.getStartOrZero();
			params.timeend = timeRange.getEndOrZero();
			params.shift_scr = timeseek_auto;

			if (this instanceof IPushOutput) {
				params.stdin = (IPushOutput) this;
			}

			// (re)start transcoding process if necessary
			if (externalProcess == null || externalProcess.isDestroyed()) {
				// first playback attempt => start new transcoding process
				logger.info("Starting transcode/remux of " + getName());
				logger.debug("Launching transcode with media info: " + getMedia().toString());

				externalProcess = getPlayer().launchTranscode(this, getMedia(), params);

				if (params.waitbeforestart > 0) {
					logger.trace("Sleeping for {} milliseconds", params.waitbeforestart);
					try {
						Thread.sleep(params.waitbeforestart);
					} catch (InterruptedException e) {
						logger.error(null, e);
					}
					logger.trace("Finished sleeping for " + params.waitbeforestart + " milliseconds");
				}
			} else if (params.timeseek > 0 && getMedia() != null && getMedia().isMediaparsed()
					&& getMedia().getDurationInSeconds() > 0) {
				// time seek request => stop running transcode process and start new one
				logger.debug("Requesting time seek: " + params.timeseek + " seconds");
				params.minBufferSize = 1;
				Runnable r = new Runnable() {
					@Override
					public void run() {
						externalProcess.stopProcess();
					}
				};
				new Thread(r, "External Process Stopper").start();
				ProcessWrapper newExternalProcess = getPlayer().launchTranscode(this, getMedia(), params);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error(null, e);
				}
				if (newExternalProcess == null) {
					logger.trace("External process instance is null... sounds not good");
				}
				externalProcess = newExternalProcess;
			}
			if (externalProcess == null) {
				return null;
			}
			InputStream is = null;
			int timer = 0;
			while (is == null && timer < 10) {
				is = externalProcess.getInputStream(low);
				timer++;
				if (is == null) {
					logger.warn("External input stream instance is null... sounds not good, waiting 500ms");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
			}

			// fail fast: don't leave a process running indefinitely if it's
			// not producing output after params.waitbeforestart milliseconds + 5 seconds
			// this cleans up lingering MEncoder web video transcode processes that hang
			// instead of exiting
			if (is == null && externalProcess != null && !externalProcess.isDestroyed()) {
				Runnable r = new Runnable() {
					@Override
					public void run() {
						logger.error("External input stream instance is null... stopping process");
						externalProcess.stopProcess();
					}
				};
				new Thread(r, "Hanging External Process Stopper").start();
			}
			return is;
		}
	}

	/**
	 * Wrap an {@link InputStream} in a {@link SizeLimitInputStream} that sets a
	 * limit to the maximum number of bytes to be read from the original input
	 * stream. The number of bytes is determined by the high and low value
	 * (bytes = high - low). If the high value is less than the low value, the
	 * input stream is not wrapped and returned as is.
	 *
	 * @param input
	 *            The input stream to wrap.
	 * @param high
	 *            The high value.
	 * @param low
	 *            The low value.
	 * @return The resulting input stream.
	 */
	private InputStream wrap(InputStream input, long high, long low) {
		if (input != null && high > low) {
			long bytes = (high - (low < 0 ? 0 : low)) + 1;
			logger.trace("Using size-limiting stream (" + bytes + " bytes)");
			return new SizeLimitInputStream(input, bytes);
		}
		return input;
	}

	public String mimeType() {
		if (getPlayer() != null) {
			// FIXME: This cannot be right. A player like FFmpeg can output many
			// formats depending on the media and the renderer. Also, players are
			// singletons. Therefore it is impossible to have exactly one mime
			// type to return.
			return getPlayer().mimeType();
		} else if (getMedia() != null && getMedia().isMediaparsed()) {
			return getMedia().getMimeType();
		} else if (getFormat() != null) {
			return getFormat().mimeType();
		} else {
			return getDefaultMimeType(getSpecificType());
		}
	}

	/**
	 * Prototype function. Original comment: need to override if some thumbnail work is to be done when mediaparserv2 enabled
	 */
	public void checkThumbnail() {
		// need to override if some thumbnail work is to be done when mediaparserv2 enabled
	}

	/**
	 * Checks if a thumbnail exists, and, if not, generates one (if possible).
	 * Called from Request/RequestV2 in response to thumbnail requests e.g. HEAD /get/0$1$0$42$3/thumbnail0000%5BExample.mkv
	 * Calls DLNAMediaInfo.generateThumbnail, which in turn calls DLNAMediaInfo.parse.
	 *
	 * @param inputFile File to check or generate the thumbnail for.
	 */
	protected void checkThumbnail(InputFile inputFile) {
		if (getMedia() != null && !getMedia().isThumbready() && configuration.isThumbnailGenerationEnabled()) {
			getMedia().setThumbready(true);
			getMedia().generateThumbnail(inputFile, getFormat(), getType());
			if (getMedia().getThumb() != null && configuration.getUseCache() && inputFile.getFile() != null) {
				PMS.get().getDatabase().updateThumbnail(inputFile.getFile().getAbsolutePath(), inputFile.getFile().lastModified(), getType(), getMedia());
			}
		}
	}

	/**
	 * Returns the input stream for this resource's generic thumbnail,
	 * which is the first of:
	 *          - its Format icon, if any
	 *          - the fallback image, if any
	 *          - the default video icon
	 *
	 * @param fallback
	 *            the fallback image, or null.
	 *
	 * @return The InputStream
	 * @throws IOException
	 */
	public InputStream getGenericThumbnailInputStream(String fallback) throws IOException {
		String thumb = fallback;
		if (getFormat() != null && getFormat().getIcon() != null) {
			thumb = getFormat().getIcon();
		}

		// Thumb could be:
		if (thumb != null) {
			// A local file
			if (new File(thumb).canRead()) {
				return new FileInputStream(thumb);
			}

			// A jar resource
			InputStream is;
			if ((is = getResourceInputStream(thumb)) != null) {
				return is;
			}

			// A URL
			try {
				return downloadAndSend(thumb, true);
			} catch (Exception e) {}
		}

		// Or none of the above
		return getResourceInputStream("images/thumbnail-video-256.png");
	}

	/**
	 * Returns the input stream for this resource's thumbnail
	 * (or a default image if a thumbnail can't be found).
	 * Typically overridden by a subclass.
	 *
	 * @return The InputStream
	 * @throws IOException
	 */
	public InputStream getThumbnailInputStream() throws IOException {
		return getGenericThumbnailInputStream(null);
	}

	public String getThumbnailContentType() {
		return HTTPResource.JPEG_TYPEMIME;
	}

	public int getType() {
		if (getFormat() != null) {
			return getFormat().getType();
		} else {
			return Format.UNKNOWN;
		}
	}

	/**
	 * Prototype function.
	 *
	 * @return true if child can be added to other folder.
	 * @see #addChild(DLNAResource)
	 */
	public abstract boolean isValid();

	public boolean allowScan() {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getClass().getSimpleName());
		result.append(" [id=");
		result.append(getId());
		result.append(", name=");
		result.append(getName());
		result.append(", full path=");
		result.append(getResourceId());
		result.append(", ext=");
		result.append(getFormat());
		result.append(", discovered=");
		result.append(isDiscovered());
		result.append("]");
		return result.toString();
	}

	/**
	 * Returns the specific type of resource. Valid types are defined in {@link Format}.
	 *
	 * @return The specific type
	 */
	protected int getSpecificType() {
		return specificType;
	}

	/**
	 * Set the specific type of this resource. Valid types are defined in {@link Format}.
	 * @param specificType The specific type to set.
	 */
	protected void setSpecificType(int specificType) {
		this.specificType = specificType;
	}

	/**
	 * Returns the {@link Format} of this resource, which defines its capabilities.
	 *
	 * @return The format of this resource.
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * Sets the {@link Format} of this resource, thereby defining its capabilities.
	 *
	 * @param format The format to set.
	 */
	protected void setFormat(Format format) {
		this.format = format;

		// Set deprecated variable for backwards compatibility
		ext = format;
	}

	/**
	 * Returns the {@link DLNAMediaInfo} object for this resource, containing the
	 * specifics of this resource, e.g. the duration.
	 *
	 * @return The object containing detailed information.
	 */
	public DLNAMediaInfo getMedia() {
		return media;
	}

	/**
	 * Sets the the {@link DLNAMediaInfo} object that contains all specifics for
	 * this resource.
	 *
	 * @param media The object containing detailed information.
	 * @since 1.50.0
	 */
	protected void setMedia(DLNAMediaInfo media) {
		this.media = media;
	}

	/**
	 * Returns the {@link DLNAMediaAudio} object for this resource that contains
	 * the audio specifics. A resource can have many audio tracks, this method
	 * returns the one that should be played.
	 *
	 * @return The audio object containing detailed information.
	 * @since 1.50.0
	 */
	public DLNAMediaAudio getMediaAudio() {
		return media_audio;
	}

	/**
	 * Sets the {@link DLNAMediaAudio} object for this resource that contains
	 * the audio specifics. A resource can have many audio tracks, this method
	 * determines the one that should be played.
	 *
	 * @param mediaAudio The audio object containing detailed information.
	 * @since 1.50.0
	 */
	protected void setMediaAudio(DLNAMediaAudio mediaAudio) {
		this.media_audio = mediaAudio;
	}

	/**
	 * Returns the {@link DLNAMediaSubtitle} object for this resource that
	 * contains the specifics for the subtitles. A resource can have many
	 * subtitles, this method returns the one that should be displayed.
	 *
	 * @return The subtitle object containing detailed information.
	 * @since 1.50.0
	 */
	public DLNAMediaSubtitle getMediaSubtitle() {
		return media_subtitle;
	}

	/**
	 * Sets the {@link DLNAMediaSubtitle} object for this resource that
	 * contains the specifics for the subtitles. A resource can have many
	 * subtitles, this method determines the one that should be used.
	 *
	 * @param mediaSubtitle The subtitle object containing detailed information.
	 * @since 1.50.0
	 */
	protected void setMediaSubtitle(DLNAMediaSubtitle mediaSubtitle) {
		this.media_subtitle = mediaSubtitle;
	}

	/**
	 * @deprecated Use {@link #getLastModified()} instead.
	 *
	 * Returns the timestamp at which this resource was last modified.
	 *
	 * @return The timestamp.
	 */
	@Deprecated
	public long getLastmodified() {
		return getLastModified();
	}

	/**
	 * Returns the timestamp at which this resource was last modified.
	 *
	 * @return The timestamp.
	 * @since 1.71.0
	 */
	public long getLastModified() {
		return lastmodified; // TODO rename lastmodified -> lastModified
	}

	/**
	 * @deprecated Use {@link #setLastModified(long)} instead.
	 *
	 * Sets the timestamp at which this resource was last modified.
	 *
	 * @param lastModified The timestamp to set.
	 * @since 1.50.0
	 */
	@Deprecated
	protected void setLastmodified(long lastModified) {
		setLastModified(lastModified);
	}

	/**
	 * Sets the timestamp at which this resource was last modified.
	 *
	 * @param lastModified The timestamp to set.
	 * @since 1.71.0
	 */
	protected void setLastModified(long lastModified) {
		this.lastmodified = lastModified; // TODO rename lastmodified -> lastModified
	}

	/**
	 * Returns the {@link Player} object that is used to encode this resource
	 * for the renderer. Can be null.
	 *
	 * @return The player object.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the {@link Player} object that is to be used to encode this
	 * resource for the renderer. The player object can be null.
	 *
	 * @param player The player object to set.
	 * @since 1.50.0
	 */
	protected void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Returns true when the details of this resource have already been
	 * investigated. This helps is not doing the same work twice.
	 *
	 * @return True if discovered, false otherwise.
	 */
	public boolean isDiscovered() {
		return discovered;
	}

	/**
	 * Set to true when the details of this resource have already been
	 * investigated. This helps is not doing the same work twice.
	 *
	 * @param discovered Set to true if this resource is discovered,
	 * 			false otherwise.
	 * @since 1.50.0
	 */
	protected void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}

	/**
	 * Returns true if this resource has subtitles in a file.
	 *
	 * @return the srtFile
	 * @since 1.50.0
	 */
	protected boolean isSrtFile() {
		return srtFile;
	}

	/**
	 * Set to true if this resource has subtitles in a file.
	 *
	 * @param srtFile the srtFile to set
	 * @since 1.50.0
	 */
	protected void setSrtFile(boolean srtFile) {
		this.srtFile = srtFile;
	}

	/**
	 * Returns the update counter for this resource. When the resource needs
	 * to be refreshed, its counter is updated.
	 *
	 * @return The update counter.
	 * @see #notifyRefresh()
	 */
	public int getUpdateId() {
		return updateId;
	}

	/**
	 * Sets the update counter for this resource. When the resource needs
	 * to be refreshed, its counter should be updated.
	 *
	 * @param updateId The counter value to set.
	 * @since 1.50.0
	 */
	protected void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	/**
	 * Returns the update counter for all resources. When all resources need
	 * to be refreshed, this counter is updated.
	 *
	 * @return The system update counter.
	 * @since 1.50.0
	 */
	public static int getSystemUpdateId() {
		return systemUpdateId;
	}

	/**
	 * Sets the update counter for all resources. When all resources need
	 * to be refreshed, this counter should be updated.
	 *
	 * @param systemUpdateId The system update counter to set.
	 * @since 1.50.0
	 */
	public static void setSystemUpdateId(int systemUpdateId) {
		DLNAResource.systemUpdateId = systemUpdateId;
	}

	/**
	 * Returns whether or not this is a nameless resource.
	 *
	 * @return True if the resource is nameless.
	 */
	public boolean isNoName() {
		return noName;
	}

	/**
	 * Sets whether or not this is a nameless resource. This is particularly
	 * useful in the virtual TRANSCODE folder for a file, where the same file
	 * is copied many times with different audio and subtitle settings. In that
	 * case the name of the file becomes irrelevant and only the settings
	 * need to be shown.
	 *
	 * @param noName Set to true if the resource is nameless.
	 * @since 1.50.0
	 */
	protected void setNoName(boolean noName) {
		this.noName = noName;
	}

	/**
	 * Returns the from - to time range for this resource.
	 *
	 * @return The time range.
	 */
	public Range.Time getSplitRange() {
		return splitRange;
	}

	/**
	 * Sets the from - to time range for this resource.
	 *
	 * @param splitRange The time range to set.
	 * @since 1.50.0
	 */
	protected void setSplitRange(Range.Time splitRange) {
		this.splitRange = splitRange;
	}

	/**
	 * Returns the number of the track to split from this resource.
	 *
	 * @return the splitTrack
	 * @since 1.50.0
	 */
	protected int getSplitTrack() {
		return splitTrack;
	}

	/**
	 * Sets the number of the track from this resource to split.
	 *
	 * @param splitTrack The track number.
	 * @since 1.50.0
	 */
	protected void setSplitTrack(int splitTrack) {
		this.splitTrack = splitTrack;
	}

	/**
	 * Returns the default renderer configuration for this resource.
	 *
	 * @return The default renderer configuration.
	 * @since 1.50.0
	 */
	protected RendererConfiguration getDefaultRenderer() {
		return defaultRenderer;
	}

	/**
	 * Sets the default renderer configuration for this resource.
	 *
	 * @param defaultRenderer The default renderer configuration to set.
	 * @since 1.50.0
	 */
	protected void setDefaultRenderer(RendererConfiguration defaultRenderer) {
		this.defaultRenderer = defaultRenderer;
	}

	/**
	 * Returns whether or not this resource is handled by AviSynth.
	 *
	 * @return True if handled by AviSynth, otherwise false.
	 * @since 1.50.0
	 */
	protected boolean isAvisynth() {
		return avisynth;
	}

	/**
	 * Sets whether or not this resource is handled by AviSynth.
	 *
	 * @param avisynth Set to true if handled by Avisyth, otherwise false.
	 * @since 1.50.0
	 */
	protected void setAvisynth(boolean avisynth) {
		this.avisynth = avisynth;
	}

	/**
	 * Returns true if transcoding should be skipped for this resource.
	 *
	 * @return True if transcoding should be skipped, false otherwise.
	 * @since 1.50.0
	 */
	protected boolean isSkipTranscode() {
		return skipTranscode;
	}

	/**
	 * Set to true if transcoding should be skipped for this resource.
	 *
	 * @param skipTranscode Set to true if trancoding should be skipped, false
	 * 			otherwise.
	 * @since 1.50.0
	 */
	protected void setSkipTranscode(boolean skipTranscode) {
		this.skipTranscode = skipTranscode;
	}

	/**
	 * Returns the list of children for this resource.
	 *
	 * @return List of children objects.
	 */
	public List<DLNAResource> getChildren() {
		return children;
	}

	/**
	 * Sets the list of children for this resource.
	 *
	 * @param children The list of children to set.
	 * @since 1.50.0
	 */
	protected void setChildren(List<DLNAResource> children) {
		this.children = children;
	}

	/**
	 * @deprecated use {@link #getLastChildId()} instead.
	 */
	@Deprecated
	protected int getLastChildrenId() {
		return getLastChildId();
	}

	/**
	 * Returns the numerical ID of the last child added.
	 *
	 * @return The ID.
	 * @since 1.80.0
	 */
	protected int getLastChildId() {
		return lastChildrenId;
	}

	/**
	 * @deprecated use {@link #setLastChildId(int)} instead.
	 */
	@Deprecated
	protected void setLastChildrenId(int lastChildId) {
		setLastChildId(lastChildId);
	}

	/**
	 * Sets the numerical ID of the last child added.
	 *
	 * @param lastChildId The ID to set.
	 * @since 1.80.0
	 */
	protected void setLastChildId(int lastChildId) {
		this.lastChildrenId = lastChildId;
	}

	/**
	 * Returns the timestamp when this resource was last refreshed.
	 *
	 * @return The timestamp.
	 */
	long getLastRefreshTime() {
		return lastRefreshTime;
	}

	/**
	 * Sets the timestamp when this resource was last refreshed.
	 *
	 * @param lastRefreshTime The timestamp to set.
	 * @since 1.50.0
	 */
	protected void setLastRefreshTime(long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
}
