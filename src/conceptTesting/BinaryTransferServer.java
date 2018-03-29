package conceptTesting;

import introductionToJava.Person;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.timesten.jdbc.TimesTenDriver;

import fileIO.MiscFileReadWrite;
import genericsinheritanceinterfacesetc.GenericsMethods;
import genericsinheritanceinterfacesetc.GenericsType;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

import java.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@SuppressWarnings("unused")
class BinaryTransferServerThreadFactory implements ThreadFactory {
  private String name;
  private AtomicInteger threadCounter;

  public BinaryTransferServerThreadFactory(String aInname) {
    name = aInname;
    threadCounter = new AtomicInteger(0);
  }

  public Thread newThread(Runnable r) {
    Thread aThread = null;
    aThread = new Thread(r, name + "-" + "SocketWorkerPool" + "-"
        + threadCounter.incrementAndGet());
    aThread.setDaemon(true);
    return aThread;
  }
}

class BinaryTransferServerPortListenerThreadFactory implements ThreadFactory {
  private String name;
  private AtomicInteger threadCounter;

  public BinaryTransferServerPortListenerThreadFactory(String aInname) {
    name = aInname;
    threadCounter = new AtomicInteger(0);
  }

  public Thread newThread(Runnable r) {
    Thread aThread = null;
    aThread = new Thread(r, name + "-" + "PortListenerWorkerPool" + "-"
        + threadCounter.incrementAndGet());
    aThread.setDaemon(true);
    aThread.setDaemon(false); // QQQ non-daemon threads will prevent JVM shutdown
    return aThread;
  }
}

class MiscThreadFactory implements ThreadFactory {
  private String name;
  protected AtomicInteger threadCounter;

  public MiscThreadFactory(String aInname) {
    name = aInname;
    threadCounter = new AtomicInteger(0);
  }

  public Thread newThread(Runnable r) {
    Thread aThread = null;
    aThread = new Thread(r, name + "-" + "WorkerPool" + "-"
        + threadCounter.incrementAndGet());
    aThread.setDaemon(false); // QQQ non-daemon[setDaemon(false)] threads will prevent JVM shutdown
    aThread.setDaemon(true);

    //threadpool catches exceptions before this handler can get them...catch them thrown from Future.get()
    aThread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHander()); 

    TestUtility.printer("Created new thread (via " + this.getClass().getName() + ")..." + aThread.getName());
    return aThread;
  }	
}

class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    TestUtility.printer(r.toString() + " is rejected");
  }
}

class BinaryTransferServerPortListenerTask implements Callable<String> {

  static private AtomicInteger staticCtr = new AtomicInteger(0);
  ExecutorService aTransferThreadPool;
  ServerSocket aServerSocket = null;
  int port = 0;

  BinaryTransferServerPortListenerTask() {
    // staticCtr.incrementAndGet();
  }

  BinaryTransferServerPortListenerTask(ExecutorService aInThreadPool,
      ServerSocket aInServerSocket, int aInPort) {
    aTransferThreadPool = aInThreadPool;
    aServerSocket = aInServerSocket;
    port = aInPort;
  }

  public String call() {
    Quote bart = new Quote();
    // try(ServerSocket ss = new ServerSocket(port,1);)
    try {
      TestUtility.printer("starting " + Thread.currentThread().getName()
          + " thread...");
      TestUtility.printer("Binary Transfer Server 1.0");
      TestUtility.printer("Listening on port: " + port);
      TestUtility.printer("Server Socket: " + aServerSocket.toString());
      while (true) {
        // accept cannot be interrupted
        Socket s = aServerSocket.accept();
        TestUtility.printer("Connection established!" + " line #: "
            + Thread.currentThread().getStackTrace()[1].getLineNumber());

        // spin off a thread to take care of this request when a connection is
        // made...
        Future<String> aBinaryTransferThreadFuture = aTransferThreadPool
            .submit(new BinaryTransferTask(s, bart));
        // or...
        // Thread t = new Thread(new BinaryTransferThread(s, bart));
        // t.start();
        // or...
        // System.out.println(
        // "Connection established!");
        // threadPool.execute(new BinaryTransferThread(s, bart));
        // @SuppressWarnings("unchecked")
        // threadPool.execute(new BinaryTransferThread(ss.accept(), bart));

        // BinaryTransferThread
        String aStr = null;
        try {
          aStr = aBinaryTransferThreadFuture.get(1L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
          boolean successfulyCancelled = aBinaryTransferThreadFuture
              .cancel(true);
          if (successfulyCancelled) {
            TestUtility
            .printer("computation cancelled (closing connection(socket closed))"
                + " line #: "
                + Thread.currentThread().getStackTrace()[1].getLineNumber());
            s.close();
          }
          TestUtility.printer("caught exception: " + e + " line #: "
              + Thread.currentThread().getStackTrace()[1].getLineNumber());
          if (e instanceof java.lang.InterruptedException) {
            TestUtility.printer("throwing InterruptedException: " + e
                + " line #: "
                + Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw e;
          }
        } finally {
          TestUtility.printer("aStr:" + aStr + " line #: "
              + Thread.currentThread().getStackTrace()[1].getLineNumber());
        }
      }
    } catch (Exception e) {
      TestUtility.printer("interrupted: " + Thread.interrupted());
      TestUtility.printer("staticCtr=" + staticCtr.get() + ":"
          + "caught exception: " + e);
      return (System.currentTimeMillis() + ":" + "thread->" + Thread
          .currentThread().getName());
    } finally {
      try {
        if (aServerSocket != null) {
          TestUtility.printer("Server socket(listener socket) being closed...");
          aServerSocket.close();
        }
      } catch (Exception e) {
        TestUtility.printer("Server socket(listener socket) being closed...FAILED");
      }
      aTransferThreadPool.shutdownNow();
      TestUtility.printer("returning from thread...");
    }
  }
}

class BinaryTransferTask implements Callable<String> {
  private Socket s;
  private Quote bart;
  private byte[] b = new byte[1024 * 8];

  public BinaryTransferTask(Socket socket, Quote bart) {
    this.s = socket;
    this.bart = bart;
    // TestUtility.printer("Connection established!");
    // System.out.println(System.currentTimeMillis() + ":" +
    // Thread.currentThread().getName() + ":Connection established!");
  }

  public String call() {
    long threadId = Thread.currentThread().getId();
    String client = s.getInetAddress().toString();

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    TestUtility.printer("Connected to " + client + " (in thread: "
        + Thread.currentThread().getName() + ":" + threadId + ")");
    try {
      s.getOutputStream().write(
          "Welcome to the Binary Transfer Server\n".getBytes("UTF-8"));
      s.getOutputStream().flush();
      s.getOutputStream().write("Enter BYE to exit.\n".getBytes("UTF-8"));
      s.getOutputStream().flush();

      while (true) {
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          String input = new String(bc, "UTF-8");
          if (input.equalsIgnoreCase("disconnect")) {
            TestUtility.printer("Received disconnect request from " + client);
            break;
          } else if (input.equalsIgnoreCase("getstring")) {
            TestUtility.printer("Serving getstring for " + client);
            s.getOutputStream().write(bart.getQuote().getBytes("UTF-8"));
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getbyte")) {
            TestUtility.printer("Serving getbyte for " + client);
            Byte c = 'a';
            byte[] result = ByteBuffer.allocate(Byte.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).put(c).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getchar")) {
            TestUtility.printer("Serving getchar for " + client);
            Character c = 'a';
            byte[] result = ByteBuffer.allocate(Character.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).putChar(c).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getboolean")) {
            TestUtility.printer("Serving getboolean for " + client);
            Boolean i = true;

            // byte[] result = ByteBuffer.allocate(Boolean.SIZE / Byte.SIZE)
            // .order(ByteOrder.LITTLE_ENDIAN).putBoolean(i).array();

            // ObjectOutputStream oos = new
            // ObjectOutputStream(s.getOutputStream());
            // oos.writeObject(i);
            // oos.writeBoolean(i);
            // oos.flush();

            byte[] result = new byte[] { (byte) (i ? 0x01 : 0x00) }; // bool ->
            // {1 byte}
            s.getOutputStream().write(result);
            s.getOutputStream().flush();

          } else if (input.equalsIgnoreCase("getshort")) {
            TestUtility.printer("Serving getshort for " + client);
            Short i = 456;
            byte[] result = ByteBuffer.allocate(Short.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).putShort(i).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getint")) {
            TestUtility.printer("Serving getint for " + client);
            Integer i = 456;
            byte[] result = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getlong")) {
            TestUtility.printer("Serving getlong for " + client);
            Long i = 456l;
            byte[] result = ByteBuffer.allocate(Long.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).putLong(i).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getfloat")) {
            TestUtility.printer("Serving getfloat for " + client);
            Float i = 2376.87f;
            byte[] result = ByteBuffer.allocate(Float.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).putFloat(i).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else if (input.equalsIgnoreCase("getdouble")) {
            TestUtility.printer("Serving getdouble for " + client);
            Double i = 2377.8777d;
            byte[] result = ByteBuffer.allocate(Double.SIZE / Byte.SIZE)
                .order(ByteOrder.LITTLE_ENDIAN).putDouble(i).array();
            s.getOutputStream().write(result);
            s.getOutputStream().flush();
          } else {
            TestUtility.printer("Client " + client
                + " sent us something weird: \"" + input + "\"");
            s.getOutputStream().write("Huh?".getBytes("UTF-8"));
            s.getOutputStream().flush();
          }
        }
      }
    } catch (Exception e) {
      TestUtility.printer("Got exception in callable:" + e.getMessage());
      // e.printStackTrace();
    } finally {
      try {
        s.close();
        TestUtility.printer("Closed connection to " + client);
      } catch (Exception e) {
        e.printStackTrace();
        TestUtility.printer("Problem encountered closing connection to "
            + client);
      }
      TestUtility.printer("returning from thread...");
    }
    return System.currentTimeMillis() + ":thread:" + "thread->"
    + Thread.currentThread().getName() + ", threadID:" + threadId;
  }

}

class TestCancelledTask implements Callable<String> {

  static private AtomicInteger staticCtr = new AtomicInteger(0);

  TestCancelledTask() {
    // staticCtr.incrementAndGet();
  }

  public String call() {
    int ctr = 0;

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    synchronized (staticCtr) {
      staticCtr.incrementAndGet();
      TestUtility.printer("staticCtr=" + staticCtr.get() + ":starting...:"
          + this.toString());
    }
    while (true) {
      try {
        if ((ctr % 10000) == 0) {
          TestUtility.printer("spinning...ctr:" + ctr);
        }
        for (long i = 0; i < 100000; i++) {
          Math.exp(i);
          // ctr++;
          // TestUtility.printer( "spinning...ctr:" + ctr );
          // if I remove this then the thread will spin until it completes
          // or forever if infinite loop
          if (Thread.interrupted()) {
            TestUtility.printer("...interrupted");
            throw new InterruptedException();
          }
        }
        // TestUtility.printer( "spinning...ctr:" + ctr );
        ctr++;
      } catch (Exception e) {
        TestUtility.printer("staticCtr=" + staticCtr.get() + ":"
            + "caught exception: " + e + ":" + this.toString());
        TestUtility.printer("returning from "
            + Thread.currentThread().getName() + " thread...");
        return (System.currentTimeMillis() + ":" + "thread->" + Thread
            .currentThread().getName());
      } finally {
      }
    }
  }
}

class TestCancellerTask implements Callable<String> {

  ThreadPoolExecutor aMiscThreadPool;

  TestCancellerTask() {

  }

  void stopCancellerThreadPool() {
    TestUtility.printer("calling aMiscThreadPool.shutdownNow()...");
    aMiscThreadPool.shutdownNow();
  }

  public String call() {
    aMiscThreadPool = null;
    try {
      List<Future<String>> aListOfFutures = new ArrayList<Future<String>>();
      ThreadFactory aMiscThreadFactory = new MiscThreadFactory(
          "taskToBeCancelled") {
        @Override
        public Thread newThread(Runnable r) {
          Thread t = null;
          t = new Thread(r, "taskToBeCancelled-AnonymousThreadFactory" + "-"
              + "WorkerPool" + "-" + threadCounter.incrementAndGet());
          t.setDaemon(false);
          TestUtility.printer("Created new thread..." + t.getName());
          return t;
        }
      };
      // aMiscThreadPool = Executors.newFixedThreadPool(2, aMiscThreadFactory);
      RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
      aMiscThreadPool = new ThreadPoolExecutor(2, 2, 10, TimeUnit.SECONDS,
          new ArrayBlockingQueue<Runnable>(2), aMiscThreadFactory,
          rejectionHandler);

      Future<String> aFuture = null;

      TestUtility.printer("starting " + Thread.currentThread().getName()
          + " thread...");

      // for ( int i = 0;i<100;i++)
      for (int i = 0; i < 1; i++) {
        aFuture = aMiscThreadPool.submit(new TestCancelledTask());
        aListOfFutures.add(i, aFuture);
      }
      // for ( int i = 0;i<100;i++ )
      for (int i = 0; i < 1; i++) {
        aFuture = aListOfFutures.get(i);
        if (aFuture != null) {
          aListOfFutures.set(i, null);
          // aFuture.cancel(true);
          try {
            // aFuture.cancel(true);
            // System.out.println(System.currentTimeMillis() + ":" +
            // Thread.currentThread().getName() + ":" + "aFuture: " +
            // aFuture.toString() +
            // ":" + "thread cancelled...");
            @SuppressWarnings("unused")
            String aStr = aFuture.get(1L, TimeUnit.MILLISECONDS);
          } catch (Exception e) {
            TestUtility.printer("aFuture: " + aFuture.toString() + ":"
                + "caught exception: " + e);
            boolean successfulyCancelled = aFuture.cancel(true);
            if (successfulyCancelled) {
              TestUtility.printer("computation cancelled: TestCancelTask");
            }
            TestUtility.threadPoolMonitor("aMiscThreadPool", aMiscThreadPool);
          }
        }
      }
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
      TestUtility.printer("caught exception: " + e);
      stopCancellerThreadPool();
      TestUtility.threadPoolMonitor("aMiscThreadPool", aMiscThreadPool);
    } finally {
      // long startTime = System.currentTimeMillis();
      // long currentTime = System.currentTimeMillis();
      // while(!aMiscThreadPool.isTerminated() && (currentTime - startTime <
      // 1000))
      // {
      // try {
      // Thread.sleep(1);
      // currentTime = System.currentTimeMillis();
      // } catch (InterruptedException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // TestUtility.printer( "waiting for aCancellerThreadPool to terminate..."
      // );
      // }
      TestUtility.threadPoolMonitor("aMiscThreadPool", aMiscThreadPool);
      TestUtility.printer("returning from thread...");
    }
    return null;
  }
}

class ThreadThrowingExceptionTask implements Runnable {

  ThreadThrowingExceptionTask() {
  }

  public void run() {
    int i = 1;
    Integer i2 = null;

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread..." + Thread.currentThread().getId());

    TestUtility.printer(Thread.currentThread().getName()
        + " throwing exception now...did thread \'" + Thread.currentThread().getName() + "\' die???");		

    // div by 0...does NOT kill thread
    //		i = i / 0;

    //		try {
    //			i = i / 0;
    //		} catch (Exception e) {
    //			TestUtility.printer("caught exception: " + e);
    //		} finally {
    //			TestUtility.printer("returning from " + Thread.currentThread().getName()
    //			    + " thread...");
    //		}
    // return((new Integer(i)).toString());

    // NPE...does NOT kill thread
    //		i2.floatValue();

    // explicitly throwing a RuntimeException...does NOT kill thread
    throw new RuntimeException();
  }
}

class ThreadReadModifyWriteTask implements Runnable {
  // volatile int i=0;
  int i = 0;

  ThreadReadModifyWriteTask() {
    TestUtility.printer("i = " + i);
  }

  public void run() {

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    try {
      i++;
      TestUtility.printer("i = " + i);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    } finally {
      TestUtility.printer("returning from " + Thread.currentThread().getName()
          + " thread...");
    }
    // return((new Integer(i)).toString());
  }
}

class ThreadReadingFileTask implements Callable<String> {
  ThreadReadingFileTask() {
  }

  public String call() {
    File file = new File("bob.jpg");
    FileInputStream reader = null;

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    try {
      reader = new FileInputStream(file);
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      int c = 0;
      int ctr = 0;
      TestUtility.printer("starting to read: " + file.getName());
      while ((c = reader.read()) != -1) {
        ctr++;
        byte b = (byte) c;
        if ((ctr % 40000) == 0)
          TestUtility.printer("byte read: " + b + " character read: " + c);
        if (Thread.interrupted()) {
          TestUtility.printer("interrupted...");
          throw (new InterruptedException());
        }
      }
      TestUtility.printer("last character read: " + c);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    } finally {
      try {
        reader.close();
      } catch (Exception e) {
      }
      TestUtility.printer("returning from " + Thread.currentThread().getName()
          + " thread...");
    }
    return null;
  }
}

class ThreadWritingFileTask implements Callable<String> {
  ThreadWritingFileTask() {
  }

  public String call() {
    File file = new File("bob.jpg");
    File fileOut = new File("bob-testwrite.jpg");
    FileInputStream reader = null;
    FileOutputStream writer = null;

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    try {
      reader = new FileInputStream(file);
      writer = new FileOutputStream(fileOut);
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      int c = 0;
      TestUtility.printer("starting to read:" + file.getName()
      + " starting to write:" + fileOut.getName());
      while ((c = reader.read()) != -1) {
        byte b = (byte) c;
        // TestUtility.printer( "byte read: " + b + " character read: " + c +
        // " byte written: " + b);
        byte[] buffer = new byte[1];
        buffer[0] = b;
        writer.write(buffer, 0, 1);
        if (Thread.interrupted()) {
          TestUtility.printer("interrupted...");
          throw (new InterruptedException());
        }
      }
      // TestUtility.printer( "character read: " + c);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    } finally {
      try {
        reader.close();
        writer.close();
      } catch (Exception e) {
      }
      TestUtility.printer("returning from " + Thread.currentThread().getName()
          + " thread...");
    }
    return null;
  }
}

/**
 * ForkBlur implements a simple horizontal image blur. It averages pixels in the
 * source array and writes them to a destination array. The sThreshold value
 * determines whether the blurring will be performed directly or split into two
 * tasks.
 * 
 * This is not the recommended way to blur images; it is only intended to
 * illustrate the use of the Fork/Join framework.
 */
@SuppressWarnings("serial")
class ForkBlur extends RecursiveAction {

  private int[] mSource;
  private int mStart;
  private int mLength;
  private int[] mDestination;
  private int mBlurWidth = 15; // Processing window size, should be odd.

  public ForkBlur(int[] src, int start, int length, int[] dst) {
    mSource = src;
    mStart = start;
    mLength = length;
    mDestination = dst;
  }

  // Average pixels from source, write results into destination.
  protected void computeDirectly() {
    int sidePixels = (mBlurWidth - 1) / 2;
    for (int index = mStart; index < mStart + mLength; index++) {
      // Calculate average.
      float rt = 0, gt = 0, bt = 0;
      for (int mi = -sidePixels; mi <= sidePixels; mi++) {
        int mindex = Math.min(Math.max(mi + index, 0), mSource.length - 1);
        int pixel = mSource[mindex];
        rt += (float) ((pixel & 0x00ff0000) >> 16) / mBlurWidth;
        gt += (float) ((pixel & 0x0000ff00) >> 8) / mBlurWidth;
        bt += (float) ((pixel & 0x000000ff) >> 0) / mBlurWidth;
      }

      // Re-assemble destination pixel.
      int dpixel = (0xff000000) | (((int) rt) << 16) | (((int) gt) << 8)
          | (((int) bt) << 0);
      mDestination[index] = dpixel;
    }
  }

  protected static int sThreshold = 10000;

  @Override
  protected void compute() {

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    if (mLength < sThreshold) {
      computeDirectly();
      return;
    }

    int split = mLength / 2;

    invokeAll(new ForkBlur(mSource, mStart, split, mDestination), new ForkBlur(
        mSource, mStart + split, mLength - split, mDestination));
  }
}

// class TestRejectedTask implements Callable<String>
class TestRejectedTask implements Runnable {

  static private AtomicInteger staticCtr = new AtomicInteger(0);

  TestRejectedTask() {
    // staticCtr.incrementAndGet();
  }

  // public String call()
  public void run() {
    int ctr = 0;
    boolean keepGoing = true;

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    synchronized (staticCtr) {
      staticCtr.incrementAndGet();
      TestUtility.printer("staticCtr=" + staticCtr.get() + ":starting...:"
          + this.toString());
    }
    while (keepGoing) {
      try {
        if ((ctr % 1000) == 0) {
          TestUtility.printer("spinning...ctr:" + ctr);
          if (ctr > 2000000)
            keepGoing = false;
        }
        for (long i = 0; i < 100000; i++) {
          Math.exp(i);
          ctr++;
          // TestUtility.printer( "spinning...ctr:" + ctr );
          // if I remove this then the thread will spin until it completes
          // or forever if infinite loop
          if (Thread.interrupted()) {
            TestUtility.printer("...interrupted");
            // throw new InterruptedException();
          }
        }
        // TestUtility.printer( "spinning...ctr:" + ctr );
        ctr++;
      } catch (Exception e) {
        TestUtility.printer("staticCtr=" + staticCtr.get() + ":"
            + "caught exception: " + e + ":" + this.toString());
        TestUtility.printer("returning from "
            + Thread.currentThread().getName() + " thread...");
        // return(System.currentTimeMillis() + ":" + "thread->" +
        // Thread.currentThread().getName());
      } finally {
      }
    }
  }
}

//class TestRejectedTask implements Callable<String>
class TestJNIJNATask implements Runnable {

  static private AtomicInteger staticCtr = new AtomicInteger(0);

  TestJNIJNATask() {
    // staticCtr.incrementAndGet();
  }

  // public String call()
  public void run() {
    HelloJNI.mainJNICall(3,"-tab2space","2","inputTestFile-JNI.txt");
  }
}



// class TestRejectedTask implements Callable<String>
class TestComputationTask implements Runnable {

  static private AtomicInteger staticCtr = new AtomicInteger(0);

  TestComputationTask() {
    // staticCtr.incrementAndGet();
  }

  // public String call()
  public void run() {
    int ctr = 0;

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    synchronized (staticCtr) {
      staticCtr.incrementAndGet();
      TestUtility.printer("staticCtr=" + staticCtr.get() + ":starting...:"
          + this.toString());
    }
    while (ctr <= 10000) {
      try {
        if ((ctr % 10000) == 0) {
          TestUtility.printer("spinning...ctr:" + ctr);
        }
        for (long i = 0; i < 1000; i++) {
          Math.exp(i);
          // ctr++;
          // TestUtility.printer( "spinning...ctr:" + ctr );
          // if I remove this then the thread will spin until it completes
          // or forever if infinite loop
          if (Thread.interrupted()) {
            TestUtility.printer("...interrupted");
            // throw new InterruptedException();
          }
        }
        // TestUtility.printer( "spinning...ctr:" + ctr );
        ctr++;
      } catch (Exception e) {
        TestUtility.printer("staticCtr=" + staticCtr.get() + ":"
            + "caught exception: " + e + ":" + this.toString());
        TestUtility.printer("returning from "
            + Thread.currentThread().getName() + " thread...");
        // return(System.currentTimeMillis() + ":" + "thread->" +
        // Thread.currentThread().getName());
      } finally {
      }
    }
  }
}

class B {
  public B() {
  }

  void func() {
    TestUtility.printer("Running B.func...");
  }
}

class A {
  private static B b = null;

  static {
    TestUtility.printer("static{}:Creating an instance of B: b...");
    b = new B();
  }

  public A() {
    if (b == null) {
      TestUtility.printer("A's constructor:Creating an instance of B: b...");
      b = new B();
    }
  }

  void f1() {
    TestUtility.printer("b: " + b);
    b.func();
  }
}

class MyClassLoader extends ClassLoader {

  public MyClassLoader(ClassLoader parent) {
    super(parent);
    TestUtility.printer("MyClassLoader loaded...");
  }

  @Override
  public Class<?> loadClass(String name, boolean resolve)
      throws ClassNotFoundException {
    TestUtility.printer("MyClassLoader:loading class '" + name + "'");
    return super.loadClass(name, resolve);
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException {
    TestUtility.printer("MyClassLoader:finding class '" + name + "'");
    return super.findClass(name);
  }
}

class DefaultUncaughtExceptionHander implements UncaughtExceptionHandler {

  public void uncaughtException(Thread t, Throwable e) {
    TestUtility.printer("******* Thread " + t.getName() + " has thrown an uncaught exception ******* " + e);
  }

}

public class BinaryTransferServer {

  int port;
  ServerSocket aServerSocket;
  ExecutorService aBinaryTransferServerPortListenerThreadPool;
  ExecutorService aBinaryTransferThreadPool;
  ExecutorService aCancellerThreadPool;
  ScheduledExecutorService aScheduledTaskThreadPool;
  Future<String> aServerPortListenerFuture;
  Future<String> aTestCancellerFuture;
  Future<String> aTestScheduledFuture;
  protected static int sThreshold = 10000;

  static {
    Thread.setDefaultUncaughtExceptionHandler(new DefaultUncaughtExceptionHander());
  }


  public BinaryTransferServer() {
    port = 1234;
    try {
      aServerSocket = new ServerSocket(port, 1);
    } catch (Exception e) {
    }
  }

  public static BufferedImage blur(BufferedImage srcImage) {
    int w = srcImage.getWidth();
    int h = srcImage.getHeight();
    int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
    int[] dst = new int[src.length];

    TestUtility.printer("Array size is " + src.length);
    TestUtility.printer("Threshold is " + sThreshold);

    int processors = Runtime.getRuntime().availableProcessors();
    TestUtility.printer(Integer.toString(processors) + " processor"
        + (processors != 1 ? "s are " : " is ") + "available");

    ForkBlur fb = new ForkBlur(src, 0, src.length, dst);

    ForkJoinPool pool = new ForkJoinPool();

    long startTime = System.currentTimeMillis();
    pool.invoke(fb);
    long endTime = System.currentTimeMillis();

    TestUtility.printer("Image blur took " + (endTime - startTime)
        + " milliseconds.");

    BufferedImage dstImage = new BufferedImage(w, h,
        BufferedImage.TYPE_INT_ARGB);
    dstImage.setRGB(0, 0, w, h, dst, 0, w);

    return dstImage;
  }

  class Drop {
    // Message sent from producer
    // to consumer.
    private String message;
    // True if consumer should wait
    // for producer to send message,
    // false if producer should wait for
    // consumer to retrieve message.
    private boolean empty = true;

    public Drop() {

    }

    public synchronized String take() {
      // Wait until message is
      // available.
      while (empty) {
        try {
          wait();
        } catch (InterruptedException e) {
        }
      }
      // Toggle status.
      empty = true;
      // Notify producer that
      // status has changed.
      notifyAll();
      return message;
    }

    public synchronized void put(String message) {
      // Wait until message has
      // been retrieved.
      while (!empty) {
        try {
          wait();
        } catch (InterruptedException e) {
        }
      }
      // Toggle status.
      empty = false;
      // Store message.
      this.message = message;
      // Notify consumer that status
      // has changed.
      notifyAll();
    }
  }

  class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
      this.drop = drop;
    }

    public void run() {
      String importantInfo[] = { "Mares eat oats", "Does eat oats",
          "Little lambs eat ivy", "A kid will eat ivy too", "wouldn't you" };
      @SuppressWarnings("unused")
      Random random = new Random();

      TestUtility.printer("starting " + Thread.currentThread().getName()
          + " thread...");

      for (int i = 0; i < importantInfo.length; i++) {
        drop.put(importantInfo[i]);
        TestUtility.printer("MESSAGE SENT: " + importantInfo[i]);
        try {
          // Thread.sleep(random.nextInt(5000));
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
      }
      drop.put("DONE");
      TestUtility.printer("returning from " + Thread.currentThread().getName()
          + " thread...");
    }
  }

  class Consumer implements Runnable {
    private Drop drop;
    @SuppressWarnings("unused")
    private AtomicInteger anAtomicInt = new AtomicInteger();

    public Consumer(Drop drop) {
      this.drop = drop;
    }

    public void run() {
      @SuppressWarnings("unused")
      Random random = new Random();

      TestUtility.printer("starting " + Thread.currentThread().getName()
          + " thread...");

      for (String message = drop.take(); !message.equals("DONE"); message = drop
          .take()) {
        TestUtility.printer("MESSAGE RECEIVED: " + message);
        try {
          // Thread.sleep(random.nextInt(5000));
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
      }
      TestUtility.printer("returning from " + Thread.currentThread().getName()
          + " thread...");
    }
  }

  // if you comment out the method signature then you
  // create a static initializer block
  // e.g.
  // private void testNotifyWaitThreads()
  private void testNotifyWaitThreads() {
    Drop drop = new Drop();
    @SuppressWarnings("unused")
    Drop drop2 = new Drop();
    Thread aThreadA = new Thread(new Producer(drop));
    Thread aThreadB = new Thread(new Consumer(drop));
    aThreadA.start();
    aThreadB.start();
  }

  @SuppressWarnings("unused")
  private void startListener() {
    try {

      aServerPortListenerFuture = aBinaryTransferServerPortListenerThreadPool
          .submit(new BinaryTransferServerPortListenerTask(
              aBinaryTransferThreadPool, aServerSocket, port));

      try {
        // give listener thread enough time to run...
        TestUtility.printer("aServerPortListenerFuture: waiting a second before getting future object and timing out");
        Thread.sleep(1000);
        String aStr = aServerPortListenerFuture.get(1L, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
        TestUtility.printer("aServerPortListenerFuture: "
            + ((aServerPortListenerFuture == null) ? "null"
                : aServerPortListenerFuture.toString()) + ":"
                + "caught exception: " + e);
      }
    } catch (Exception e) {
      TestUtility.printer("System exception! " + e);
    } finally {
    }
  }

  public void stopListener() {
    // interrupt it while it's in accept...
    boolean successfulyCancelled = aServerPortListenerFuture.cancel(true);
    if (successfulyCancelled) {
      TestUtility.printer("computation cancelled: aServerPortListenerFuture");
    } else {
      TestUtility
      .printer("computation *not* cancelled: aServerPortListenerFuture");
    }
    try {
      //can close a listening socket while in accept
      aServerSocket.close();
      boolean isClosed = aServerSocket.isClosed();
      TestUtility.printer("aServerSocket.isClosed():" + isClosed);
    } catch (Exception e) {
      TestUtility.printer("Exception: " + e);
    } finally {
      aBinaryTransferServerPortListenerThreadPool.shutdownNow();
    }
  }

  private void testSocketListenerThreadCancellations() {
    ThreadFactory aBinaryTransferServerPortListenerThreadFactory = new BinaryTransferServerPortListenerThreadFactory(
        "serverPortListener");
    aBinaryTransferServerPortListenerThreadPool = Executors.newFixedThreadPool(
        1, aBinaryTransferServerPortListenerThreadFactory);

    ThreadFactory aBinaryTransferThreadFactory = new BinaryTransferServerThreadFactory(
        BinaryTransferServer.class.getSimpleName());
    aBinaryTransferThreadPool = Executors.newFixedThreadPool(Runtime
        .getRuntime().availableProcessors(), aBinaryTransferThreadFactory);

    this.startListener();
    try {
      TestUtility.printer("Sleeping...");
      Thread.sleep(10000);
      TestUtility.printer("Waking up now...");
    } catch (InterruptedException e) {
      TestUtility
      .printer("InterruptedException: calling stopListener...closing accept socket");
      this.stopListener();
    } finally {
      // This is not necessary if you have the listener threads set as daemon so
      // the JVM
      // may exit regardless of the listener thread being in socket.accept()
      // ...can set daemon status via myThread.setDaemon(true)
      // TestUtility.printer(
      // "finally: calling stopListener...closing accept socket" );
      // this.stopListener();
    }
  }

  private void testFileReadThreadCancellations() {
    ThreadFactory aMiscThreadReadFileFactory = new MiscThreadFactory(
        "threadReadFileTest");
    ExecutorService aMiscThreadReadingFileThreadPool = Executors
        .newFixedThreadPool(10, aMiscThreadReadFileFactory);

    Future<String> aFuture = aMiscThreadReadingFileThreadPool
        .submit(new ThreadReadingFileTask());
    try {
      Thread.sleep(200);
      aFuture.cancel(true);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    } finally {
      aMiscThreadReadingFileThreadPool.shutdownNow();
    }
  }

  private void testFileWriteThreadCancellations() {
    ThreadFactory aMiscThreadWriteFileFactory = new MiscThreadFactory(
        "threadWriteFileTest");
    ExecutorService aMiscThreadWritingFileThreadPool = Executors
        .newFixedThreadPool(10, aMiscThreadWriteFileFactory);

    Future<String> aFuture = aMiscThreadWritingFileThreadPool
        .submit(new ThreadWritingFileTask());

    try {
      Thread.sleep(10);
      aFuture.get(1, TimeUnit.SECONDS);
      // aFuture.cancel(true);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    }
    aMiscThreadWritingFileThreadPool.shutdownNow();
  }

  private void startCanceller() {
    aTestCancellerFuture = aCancellerThreadPool.submit(new TestCancellerTask());

    // aSchedulesTaskThreadPool.scheduleWithFixedDelay(
    // new TestCancellerTask(),
    // 30000L,
    // 10000L,
    // TimeUnit.MILLISECONDS);

  }

  public void stopCanceller() {
    boolean successfulyCancelled = aTestCancellerFuture.cancel(true);
    if (successfulyCancelled) {
      TestUtility.printer("computation cancelled");
    }
  }

  private void stopCancellerThreadPool() {
    TestUtility.printer("calling aCancellerThreadPool.shutdownNow()...");
    aCancellerThreadPool.shutdownNow();
  }

  private void testBasicThreadCancellations() {

    ThreadFactory aCancellerThreadFactory = new MiscThreadFactory("canceller");
    aCancellerThreadPool = Executors.newFixedThreadPool(2,
        aCancellerThreadFactory);

    ThreadFactory aScheduledCancellerThreadFactory = new MiscThreadFactory(
        "scheduledCanceller-NotUsed");
    aScheduledTaskThreadPool = Executors.newScheduledThreadPool(10,
        aScheduledCancellerThreadFactory);

    this.startCanceller();
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    } finally {
      this.stopCanceller();
      this.stopCancellerThreadPool();
      long startTime = System.currentTimeMillis();
      long currentTime = System.currentTimeMillis();
      while (!aCancellerThreadPool.isTerminated()
          && (currentTime - startTime < 1000)) {
        try {
          Thread.sleep(1);
          currentTime = System.currentTimeMillis();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        TestUtility.printer("waiting for aCancellerThreadPool to terminate...");
      }
      TestUtility.threadPoolMonitor("aCancellerThreadPool",
          (ThreadPoolExecutor) aCancellerThreadPool);
    }
  }

  private void testTaskRejected() {
    int numCoreThreads = 2;
    int numMaxThreads = 2;
    int keepAliveTime = 10;
    int queueSize = 2;
    RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
    ThreadFactory aRejectedTaskThreadFactory = new MiscThreadFactory(
        "rejectedTask");
    ThreadPoolExecutor aRejectedTaskThreadPool = null;
    aRejectedTaskThreadPool = new ThreadPoolExecutor(numCoreThreads,
        numMaxThreads, keepAliveTime, TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(queueSize),
        aRejectedTaskThreadFactory, rejectionHandler);

    Future<?> aFuture = null;

    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    TestUtility.printer("suspected rejected task: " + aFuture.toString());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    TestUtility.printer("suspected rejected task: " + aFuture.toString());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    TestUtility.printer("suspected rejected task: " + aFuture.toString());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    TestUtility.printer("suspected rejected task: " + aFuture.toString());
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    TestUtility.printer("suspected rejected task: " + aFuture.toString());

    TestUtility.threadPoolMonitor("aRejectedTaskThreadPool",
        aRejectedTaskThreadPool);
    aRejectedTaskThreadPool.shutdownNow();
    long startTime = System.currentTimeMillis();
    long currentTime = System.currentTimeMillis();
    while (!aRejectedTaskThreadPool.isTerminated()
        && (currentTime - startTime < 1000)) {
      try {
        Thread.sleep(1000);
        TestUtility
        .printer("waiting for aRejectedTaskThreadPool to terminate...");
        currentTime = System.currentTimeMillis();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    TestUtility.threadPoolMonitor("aRejectedTaskThreadPool",
        aRejectedTaskThreadPool);
    aFuture = aRejectedTaskThreadPool.submit(new TestRejectedTask());
    TestUtility.printer("suspected rejected task: " + aFuture.toString());
    TestUtility.threadPoolMonitor("aRejectedTaskThreadPool",
        aRejectedTaskThreadPool);

  }

  private void testJNIJNA() {
    int numCoreThreads = 2;
    int numMaxThreads = 15;
    int keepAliveTime = 10;
    int queueSize = 2;
    RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
    ThreadFactory aJNIJNATaskThreadFactory = new MiscThreadFactory(
        "JNIJNATask");
    ThreadPoolExecutor aJNIJNATaskThreadPool = null;
    aJNIJNATaskThreadPool = new ThreadPoolExecutor(numCoreThreads,
        numMaxThreads, keepAliveTime, TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(queueSize),
        aJNIJNATaskThreadFactory, rejectionHandler);

    Future<?> aFuture = null;

     aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());		
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());		
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);
    aFuture = aJNIJNATaskThreadPool.submit(new TestJNIJNATask());		
    TestUtility.threadPoolMonitor("aJNIJNATaskThread",
        aJNIJNATaskThreadPool);

    int ctr = 1000;
    while(ctr > 0)
    {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      TestUtility.threadPoolMonitor("aJNIJNATaskThread",
          aJNIJNATaskThreadPool);

      ctr--;

      if(aJNIJNATaskThreadPool.getActiveCount() == 0)
      {
        ctr = 0;
      }
    }


    aJNIJNATaskThreadPool.shutdownNow();

  }

  private void testThreadPoolWorkerThrowingException() {
    ThreadFactory aMiscThreadThrowingExceptionFactory = new MiscThreadFactory(
        "threadThrowingExceptionTest");
    ExecutorService aMiscThreadThrowingExceptionThreadPool = Executors
        .newFixedThreadPool(10, aMiscThreadThrowingExceptionFactory);

    Collection<Future> aFutures = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      aFutures.add(aMiscThreadThrowingExceptionThreadPool
          .submit(new ThreadThrowingExceptionTask()));
    }

    for( Future aFuture : aFutures ) {
      try
      {
        TestUtility.printer("task returned: " + aFuture.get(1000L, TimeUnit.MILLISECONDS));
      }
      catch(Throwable t)
      {
        TestUtility.printer("Caught throwable: " + t);
      }
    }



    Thread aThreadC = new Thread(new ThreadThrowingExceptionTask());
    aThreadC.start();
    TestUtility.printer("shutting down  "
        + aMiscThreadThrowingExceptionThreadPool + " thread pool...");
    aMiscThreadThrowingExceptionThreadPool.shutdownNow();
  }

  private void testThreadDataModificationConcurrency() {
    ThreadFactory aMiscThreadReadModifyWriteFactory = new MiscThreadFactory(
        "readModifyWriteTest");
    ExecutorService aMiscThreadReadModifyWriteThreadPool = Executors
        .newFixedThreadPool(10, aMiscThreadReadModifyWriteFactory);
    ThreadReadModifyWriteTask aRMWVar = new ThreadReadModifyWriteTask();
    for (int i = 0; i < 100; i++) {
      aMiscThreadReadModifyWriteThreadPool.submit(aRMWVar);
    }
    aMiscThreadReadModifyWriteThreadPool.shutdownNow();
  }

  private void testForkJoin() {
    String srcName = "C:\\Users\\rob\\Desktop\\temp\\photosFromBkup\\1966\\CertificateOfBaptism.jpg";
    // srcName = "C:\\Users\\rob\\Desktop\\bob.jpg";
    // srcName =
    // "H:\\Google Drive\\work-career\\03-ProfDev\\10-misc\\java_source1of1\\eclipseWorkspace\\misc\\bob.jpg";
    srcName = "bob.jpg";
    String dstName = "C:\\Users\\rob\\Desktop\\temp\\photosFromBkup\\1966\\blurred-CertificateOfBaptism.jpg";
    // dstName = "C:\\Users\\rob\\Desktop\\blurred-bob.jpg";
    // dstName =
    // "H:\\Google Drive\\work-career\\03-ProfDev\\10-misc\\java_source1of1\\eclipseWorkspace\\misc\\bob-blurred.jpg";
    dstName = "bob-blurred.jpg";

    try {
      TestUtility.printer("Source image: " + srcName);
      File srcFile = new File(srcName);
      BufferedImage image = ImageIO.read(srcFile);

      BufferedImage blurredImage = blur(image);

      File dstFile = new File(dstName);
      ImageIO.write(blurredImage, "jpg", dstFile);
    } catch (Exception e) {
      TestUtility.printer("caught exception: " + e);
    }

    TestUtility.printer("Output image: " + dstName);
  }

  private void testTimeToRunTwoTasksInParallelThenInSeries() {
    int numCoreThreads = 2;
    int numMaxThreads = 4;
    int keepAliveTime = 10;
    int queueSize = 1;
    RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
    ThreadFactory aComputationTaskThreadFactory = new MiscThreadFactory(
        "computationTask");
    ThreadPoolExecutor aComputationTaskThreadPool = null;
    aComputationTaskThreadPool = new ThreadPoolExecutor(numCoreThreads,
        numMaxThreads, keepAliveTime, TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(queueSize),
        aComputationTaskThreadFactory, rejectionHandler);

    Future<?> aFuture1 = null;
    Future<?> aFuture2 = null;

    TestUtility.threadPoolMonitor("aComputationTaskThreadPool",
        aComputationTaskThreadPool);
    TestUtility.printer("starting to run both tasks in parallel ");
    long startTime = System.currentTimeMillis();
    aFuture1 = aComputationTaskThreadPool.submit(new TestComputationTask());
    aFuture2 = aComputationTaskThreadPool.submit(new TestComputationTask());

    TestUtility.threadPoolMonitor("aComputationTaskThreadPool",
        aComputationTaskThreadPool);

    try {
      aFuture1.get();
      aFuture2.get();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    TestUtility.printer("time to run both tasks in parallel: "
        + (endTime - startTime) + "ms");

    TestUtility.threadPoolMonitor("aComputationTaskThreadPool",
        aComputationTaskThreadPool);

    TestUtility.printer("starting to run both tasks in series ");
    startTime = System.currentTimeMillis();
    Runnable task1 = new TestComputationTask();
    task1.run();
    Runnable task2 = new TestComputationTask();
    task2.run();
    endTime = System.currentTimeMillis();
    TestUtility.printer("time to run both tasks in series: "
        + (endTime - startTime) + "ms");

    TestUtility.threadPoolMonitor("aComputationTaskThreadPool",
        aComputationTaskThreadPool);

    startTime = System.currentTimeMillis();
    long currentTime = System.currentTimeMillis();
    aComputationTaskThreadPool.shutdownNow();

    TestUtility.threadPoolMonitor("***aComputationTaskThreadPool",
        aComputationTaskThreadPool);

    long aWaitTime = 1;
    try {
      TestUtility.printer("waiting " + aWaitTime
          + " milliseconds for aComputationTaskThreadPool to terminate...");
      Thread.sleep(aWaitTime);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    // while(!aComputationTaskThreadPool.isTerminated() && ((currentTime -
    // startTime) < aWaitTime))
    while (!aComputationTaskThreadPool.isTerminated()) {
      try {
        // Thread.sleep(aWaitTime);

        TestUtility.threadPoolMonitor("*******aComputationTaskThreadPool",
            aComputationTaskThreadPool);

        currentTime = System.currentTimeMillis();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    TestUtility.threadPoolMonitor("aComputationTaskThreadPool",
        aComputationTaskThreadPool);

  }

  // Generics Upper Bounded Wildcard
  public static double sum(List<? extends Number> list) {
    double sum = 0;
    for (Number n : list) {

      sum += n.doubleValue();
    }
    return sum;
  }

  // Generics Unbounded Wildcard
  public static void printData(List<?> list) {
    for (Object obj : list) {
      System.out.print(obj + "::");
    }
  }

  // Generics Lower bounded Wildcard
  public static void addIntegers(List<? super Integer> list) {
    list.add(new Integer(50));
  }

  private void testGenerics() {
    // need to create different generics and play with them
    GenericsType<String> type = new GenericsType<>();
    type.set("Pankaj"); // valid

    // GenericsType type1 = new GenericsType(); //raw type -> type defaults to
    // Object
    // type1.set("Pankaj"); //valid
    // type1.set(10); //valid and autoboxing support

    GenericsType<String> g1 = new GenericsType<>();
    g1.set("Pankaj");
    GenericsType<String> g2 = new GenericsType<>();
    g2.set("Pankaj");

    boolean isEqual = GenericsMethods.<String> isEqual(g1, g2);
    // above statement can be written simply as
    isEqual = GenericsMethods.isEqual(g1, g2);
    TestUtility.printer("isEqual = GenericsMethods.isEqual(g1, g2):" + isEqual);
    // without specifying a type between angle brackets.
    // Compiler will infer the type that is needed

    String str = "abc";
    Object obj = new Object();
    obj = str; // works because String is-a Object, inheritance in java

    // We know that Java inheritance allows us to assign a variable A to another
    // variable B if A is subclass of B. So we might think that any generic type
    // of
    // A can be assigned to generic type of B, but it’s not the case. Lets see
    // this with a simple program.
    GenericsType<String> myClass1 = new GenericsType<>();
    GenericsType<Object> myClass2 = new GenericsType<>();
    // myClass2=myClass1; // compilation error since MyClass<String> is not a
    // MyClass<Object>
    obj = myClass1; // MyClass<T> parent is Object

    // Generics Upper Bounded Wildcard
    List<Integer> ints = new ArrayList<>();
    ints.add(3);
    ints.add(5);
    ints.add(10);
    double sum = sum(ints);
    TestUtility.printer("Sum of ints=" + sum);

    // Subtyping using Generics Wildcard
    List<? extends Integer> intList = new ArrayList<>();
    List<? extends Number> numList = intList; // OK. List<? extends Integer> is
    // a subtype of List<? extends
    // Number>
  }

  <T> T invoke(Callable<T> c) throws Exception {
    return c.call();
  }
  
  private void testClosuresAndLambdaExpressions() {
    // available in JSE8
    System.out.println("lambda expressions test (JSE8)");

    List<Person> roster = null;
    
    Person.printPersons(
        roster,
        (Person p) -> p.getGender() == Person.Gender.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25
    );
    
    try {
      //see: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
      // the lambda expr () -> "done" is equivalent to:
      // String s = invoke(                          
      //     new Callable<String>(){                 
      //       @Override                             
      //       public String call(){return("done");} 
      //     });                                     
      String s = invoke(() -> "done(via lambda expression)");
      System.out.println(s);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
        
    try {
      String s = invoke(
          new Callable<String>(){
            @Override 
            public String call(){return("done(via anonymous class)");}
          });
      System.out.println(s);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static void testString(String args[]) {

    String fileName = "c://lines.txt";
    List<String> list = new ArrayList<>();

    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

        //1. filter line 3
        //2. convert all content to upper case
        //3. convert it into a List
        list = stream
                .filter(line -> !line.startsWith("line3"))
                .map(String::toUpperCase)
                .collect(Collectors.toList());

    } catch (IOException e) {
        e.printStackTrace();
    }

    list.forEach(System.out::println);

  }

  
  private void testStaticVariableInitialization() {
    A a = new A();
    A b = new A();
    A c = new A();
    A d = new A();

    a.f1();
    b.f1();
    c.f1();
    d.f1();

  }

  private void testMultipleClassLoaders() {
    ClassLoader defaultClassLoader = Thread.currentThread()
        .getContextClassLoader();
    Collection<URL> classPathURL = new LinkedList<URL>();
    ClassLoader currentClassLoader = defaultClassLoader;
    URL lURL = null;
    try {
      // lURL = new URL("file:/F:/misc/eclipseWorkspace/misc/"); // bad
      lURL = new URL("file:/F:/misc/eclipseWorkspace/misc/bin/"); // good
    } catch (Exception e) {
    }
    classPathURL.add(lURL);
    SACURLClassLoader loader1 = new SACURLClassLoader(
        classPathURL.toArray(new URL[classPathURL.size()]),
        BinaryTransferServer.class.getClassLoader());
    SACURLClassLoader loader2 = new SACURLClassLoader(
        classPathURL.toArray(new URL[classPathURL.size()]),
        BinaryTransferServer.class.getClassLoader());
    // SACURLClassLoader loader1 = new
    // SACURLClassLoader(classPathURL.toArray(new URL[classPathURL.size()]),
    // null);
    // SACURLClassLoader loader2 = new
    // SACURLClassLoader(classPathURL.toArray(new URL[classPathURL.size()]),
    // null);
    SACURLClassLoader loader3 = new SACURLClassLoader(
        classPathURL.toArray(new URL[classPathURL.size()]), null);
    try {

      Class<?> c1 = loader1.loadClass("conceptTesting.ClassForCustomLoading");
      Object o1 = c1.newInstance();
      // Method m1 = c1.getMethod("f1");
      Method m1 = c1.getMethod("toString");
      TestUtility.printer("m1.invoke(o1)=" + m1.invoke(o1));
      // m1.invoke(o1);
      TestUtility.printer("c1.getClassLoader()=" + c1.getClassLoader());

      Class<?> c2 = loader2.loadClass("conceptTesting.ClassForCustomLoading");
      Object o2 = c2.newInstance();
      // Method m2 = c2.getMethod("f1");
      Method m2 = c2.getMethod("toString");
      TestUtility.printer("m2.invoke(o2)=" + m2.invoke(o2));
      // m2.invoke(o1);
      TestUtility.printer("c2.getClassLoader()=" + c2.getClassLoader());

      // It seems that classes loaded with the conventional syntax (i.e. not via
      // reflection)
      // gets the AppClassLoader
      Thread aThread = new Thread() {
        @Override
        public void run() {
          ClassLoader currentClassLoader = Thread.currentThread()
              .getContextClassLoader();

          TestUtility
          .printer("TestClassForCustomLoading.class.getClassLoader()="
              + ClassForCustomLoading.class.getClassLoader());

          TestUtility.printer("CLASSPATH for ClassForCustomLoading: "
              + Arrays.toString(((URLClassLoader) ClassForCustomLoading.class
                  .getClassLoader()).getURLs()));

          TestUtility.printer("currentClassLoader=" + currentClassLoader
              + " ClassForCustomLoading.class.getClassLoader()="
              + ClassForCustomLoading.class.getClassLoader());

          TestUtility.printer(ClassForCustomLoading.class
              .getClassLoader()
              .getResource(
                  ClassForCustomLoading.class.getName().replace('.', '/')
                  + ".class").toString());

          ClassForCustomLoading c3 = new ClassForCustomLoading();
          TestUtility.printer("c3.toString()= " + c3.toString());
        }
      };
      Thread.currentThread().setContextClassLoader(loader3);
      aThread.setContextClassLoader(Thread.currentThread()
          .getContextClassLoader());
      aThread.setDaemon(false);
      aThread.setPriority(Thread.MAX_PRIORITY);
      aThread.setName("THREAD:CHANGE-CLASS-LOADER");
      aThread.start();

      // Thread.currentThread().setContextClassLoader(loader1);
      // currentClassLoader = Thread.currentThread().getContextClassLoader();
      // TestUtility.printer("currentClassLoader=" + currentClassLoader +
      // " C.class.getClassLoader()=" +
      // ClassForCustomLoading.class.getClassLoader());
      // ClassForCustomLoading c3 = new ClassForCustomLoading();
      // TestUtility.printer("c3.toString()= " + c3.toString());
      // TestUtility.printer("ClassForCustomLoading.class.getClassLoader()=" +
      // ClassForCustomLoading.class.getClassLoader());

      // Thread.currentThread().setContextClassLoader(loader2);
      // currentClassLoader = Thread.currentThread().getContextClassLoader();
      // TestUtility.printer("currentClassLoader=" + currentClassLoader +
      // " C.class.getClassLoader()=" +
      // ClassForCustomLoading.class.getClassLoader());
      // ClassForCustomLoading c4 = new ClassForCustomLoading();
      // TestUtility.printer("c4.toString()= " + c4.toString());
      // TestUtility.printer("ClassForCustomLoading.class.getClassLoader()=" +
      // ClassForCustomLoading.class.getClassLoader());

      // Thread.currentThread().setContextClassLoader(defaultClassLoader);
      // currentClassLoader = Thread.currentThread().getContextClassLoader();
      // TestUtility.printer("currentClassLoader=" + currentClassLoader +
      // " C.class.getClassLoader()=" +
      // ClassForCustomLoading.class.getClassLoader());
      // ClassForCustomLoading c5 = new ClassForCustomLoading();
      // TestUtility.printer("c5.toString()= " + c5.toString());
      // c5.f1();
      // TestUtility.printer("ClassForCustomLoading.class.getClassLoader()=" +
      // ClassForCustomLoading.class.getClassLoader());

      loader1.close();
      loader2.close();
      loader3.close();

    } catch (Exception e) {
      TestUtility.printer("Exception= " + e);
    } finally {
      Thread.currentThread().setContextClassLoader(defaultClassLoader);
    }
  }

  private void fizzbuzz() {
    for (int i = 1; i <= 100; i++) {
      if ((i % 3 == 0) && (i % 5 == 0))
        TestUtility.printer("fizzbuzz");
      else if (i % 3 == 0)
        TestUtility.printer("fizz");
      else if (i % 5 == 0)
        TestUtility.printer("buzz");
      else
        TestUtility.printer("i= " + i);
    }
  }

  private int factorial(int n) {
    int result;

    // base case of recursion
    if (n == 1 || n == 0)
      result = 1;
    else
      result = factorial(n - 1) * n;
    return result;
  }

  private int fibonacci(int number) {
    if (number < 1) {
      throw new IllegalArgumentException(
          "Invalid argument for Fibonacci series: " + number);
    }

    // base case of recursion
    if (number == 1 || number == 2) {
      return 1;
    }

    // recursive method call in java
    return fibonacci(number - 2) + fibonacci(number - 1);

  }

  private void XMLParseTest() {

    TestUtility.printer("starting SAX parser test...");
    XMLSAXParseTest.mainMethod();
    TestUtility.printer("finished SAX parser test...");

    TestUtility.printer("starting DOM parser test...");
    XMLDOMParseTest.mainMethod();
    TestUtility.printer("finished DOM parser test...");

  }

  private void spitOutXML() {
    CreateXml aCreateXML = new CreateXml();
    aCreateXML.createXMLString();
  }

  private void testRegex() {
    // isolate host
    // String aInUrl = "billy:bob";
    // String aInUrl = "bob/bubba";
    // String aInUrl = "billy:bob/bubba";
    String aInUrl = "billy:bob:1234/bubba";
    {
      Pattern p = Pattern.compile("(.*:*)(.*)(/*.*)");
      Matcher m = p.matcher(aInUrl);
      while (m.find()) {
        System.out.println("0:" + m.group(0) + ' ' + "1:" + m.group(1) + ' '
            + "2:" + m.group(2) + ' ' + "3:" + m.group(3));
      }
    }

    {
      String REGEX = ":|/";
      Pattern p = Pattern.compile(REGEX);
      String[] items = p.split(aInUrl);
      for (String s : items) {
        System.out.println(s);
      }
    }
  }

  // We've spec'd CBC mode, so set up the IV now as part of the algorithm parameters.
  protected final static IvParameterSpec GOOD_IV_16 = 
      new IvParameterSpec(
          new byte[] { 0x0F, 0x0E, 0x0D, 0x0C, 0x0B, 0x0A, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 });

  static
  {
    Security.addProvider(new BouncyCastleProvider());
  }
  private static final byte[] IV = { // Hard coded for now
      -85, -67, -5, 88, 28, 49, 49, 85,
      114, 83, -40, 119, -65, 91, 76, 108};
  private static final IvParameterSpec ivSpec = new IvParameterSpec(IV);

  private void testCrypto()
  {
    String algorithm = "AES/CBC/NoPadding";
    String keyAlgorithm = "AES";

    try {
      // Use BounceyCastle to generate a 128 bit key as it seems LunaProvider generates 256 bit keys as a minimum.
      KeyGenerator kg = KeyGenerator.getInstance(keyAlgorithm, "BC");
      kg.init(128);
      Key key = kg.generateKey();

      //	    AlgorithmParameters goodParams = AlgorithmParameters.getInstance("IV", "LunaProvider");
      //	    goodParams.init(GOOD_IV_16);

      // Create and init an instance of the cipher from Luna.
      //	    Cipher cipher = Cipher.getInstance(algorithm, "LunaProvider");
      Cipher cipher = Cipher.getInstance(algorithm, "BC");
      //	    cipher.init(Cipher.ENCRYPT_MODE, key, goodParams);
      cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

      // Get a basic input block of 16 bytes.
      byte[] input = new byte[16];
      Random r = new Random();
      r.nextBytes(input);
      TestUtility.printer("ClearText length:" + input.length);

      // Do the encryption.
      byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
      int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
      ctLength += cipher.doFinal(cipherText, ctLength);
      TestUtility.printer("CipherText length:" + cipherText.length);
    } catch (InvalidKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchProviderException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      //    } catch (InvalidParameterSpecException e) {
      //	    // TODO Auto-generated catch block
      //	    e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ShortBufferException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (BadPaddingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }		
  }
  
  private int testFinallyClause()
  {
    try {
      return 1;
    }
    finally
    {
      return 2;
    }
  }

  public static void main(String[] args) 
  {
    
    //test push to GitHub
    
    BinaryTransferServer aBinXfrSvc = new BinaryTransferServer();
    MiscFileReadWrite aMiscFileRW = new MiscFileReadWrite();

    TestUtility.printer("starting " + Thread.currentThread().getName()
        + " thread...");

    // thread pool interruption stuff...
    aBinXfrSvc.testSocketListenerThreadCancellations();//QQQ - this test is used for socket manipulation
//    		 aBinXfrSvc.testBasicThreadCancellations();
//    		 aBinXfrSvc.testFileReadThreadCancellations();
//    		 aBinXfrSvc.testFileWriteThreadCancellations();

    // notify/wait stuff...
    //		 aBinXfrSvc.testNotifyWaitThreads();

    // thread throwing an exception...does it kill the thread requiring the pool
    // to create a new thread...apparently not
    //		 aBinXfrSvc.testThreadPoolWorkerThrowingException();

    // Read-Modify-Write issue
    //		 aBinXfrSvc.testThreadDataModificationConcurrency();

    // Fork-Join sample...seems to require the problem to be solvable via
    // recursion
    //		 aBinXfrSvc.testForkJoin();

    // random access of flat file test...think BitTorrent
    // ...also think of Dropbox blacklist of file hashes...
    // test for random insertion of 'bad byte' in middle of video file
//    aMiscFileRW.testRandomAccessOfFlatFile();

    // thread pool rejecting task behaviour
    //		 aBinXfrSvc.testTaskRejected();

    // multiple core test
    //		 aBinXfrSvc.testTimeToRunTwoTasksInParallelThenInSeries();

    // generics test - generic classes/generic methods
    //		 aBinXfrSvc.testGenerics();

    // closures/"funcptr" and lambda expressions test (JSE8)\
    aBinXfrSvc.testClosuresAndLambdaExpressions();

    // static variable initialization java - done only once when class is loaded
    //		 aBinXfrSvc.testStaticVariableInitialization();

    // test multiple class loaders
    //		 aBinXfrSvc.testMultipleClassLoaders();

    // test for JDBC compliance in ttjdbc6.jar
    // TimesTenDriver aTTDriver = new TimesTenDriver();
    // TestUtility.printer( "Is ttjdbc6.jar JDBC compliant? " +
    // aTTDriver.jdbcCompliant());

    // fizz-buzz
    //		 aBinXfrSvc.fizzbuzz();

    // simple recursion
    //		TestUtility.printer( "0!= " + aBinXfrSvc.factorial(0));
    //		TestUtility.printer( "5!= " + aBinXfrSvc.factorial(5));
    //		TestUtility.printer( "10th fibonacci number = " + aBinXfrSvc.fibonacci(10));
    //		TestUtility.printer( "2nd fibonacci number = " + aBinXfrSvc.fibonacci(2));

    // JNI call test - requires 64-bit JVM because dll is 64-bit (built via
    // mingw64...see jni/readme file)
    //		 HelloJNI.mainJNICall(null);
    // String[] argv = {"-tab2space", "2", "inputTestFile-JNI.txt"};
    //		 HelloJNI.mainJNICall(3,"-tab2space","2","inputTestFile-JNI.txt");
    aBinXfrSvc.testJNIJNA();//QQQ - am normally running this test...

    // SAX Parser Test
    //		aBinXfrSvc.XMLParseTest();

    // generate XML file
    //		aBinXfrSvc.spitOutXML();

    // test for interrupts/signals/etc
    // aBinXfrSvc.catchSignals();

    // test for String split
    //		aBinXfrSvc.testRegex();

    // test crypto
    aBinXfrSvc.testCrypto();

    // test finally clause
//    int rc = aBinXfrSvc.testFinallyClause();
//    System.out.printf("rc=%d\n",rc);

    TestUtility
    .printer("Only non-daemon threads will prevent JVM shutdown at this point...");
    TestUtility.printer("returning from " + Thread.currentThread().getName()
        + " thread...");

    // The JVM goes away thus all resources are released...thread pools,
    // sockets/file descriptors, etc.
    // System.exit(0);
  }

}// class defn

