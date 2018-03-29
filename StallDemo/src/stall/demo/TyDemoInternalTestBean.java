package stall.demo;

/*
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import com.tymeac.dse.base.*;

/** 
 *  This is the logic module in support of Frame class TyDemoInternalTest.
 */
public class TyDemoInternalTestBean {

	// reference to the server
	private TymeacInterface ti = null;

	// implementation methods
	private InternalServer impl = null;

	// threads window thread
	private TyDemoInternalTestThread it = null;
	
	// blocking object for blocking test
	private Object block_on_me = new Object();
	
	// blocking thread instance
	private Sync local_thread = new Sync(block_on_me);
	
	// return from server
  private TymeacReturn back = null;
	
	/**
	 * This thread holds the synchronized object so
	 *   any other thread that synchronizes on this same
	 *   object will wait.
	 */
	private class Sync extends Thread {
	  
	  private Object sync_me;
	  
	  private volatile boolean wakeup = false;
	  
	  /**
	   * Single constructor
	   * @param obj to sync on
	   */
	  protected Sync (Object obj) {
	    
	    super("InternalSync");
	    
	    sync_me = obj;
	    
	  } // end-constructor
	  
	  @Override
	  public void run () {
	    
	    synchronized (sync_me) {
	      
	      while (!wakeup) {
	        
	        // sleep does not release the sync	        
	        try { Thread.sleep(60000 * 30); } 
	        catch (InterruptedException e){}
	      
	      } // end-while	
	      
	      return;
	      
	    } // end-sync
	  } // end-method
	} // end-inner-Class

/**
 * TyDemoInternalTestBean constructor 
 */
public TyDemoInternalTestBean() {
  
  local_thread.start();  
	
} // end-constructor

/**
 * Call the server
 * @param TymeacParm
 * @return result of call
 */
private int callServer(TymeacParm parm) {
  
  try {                             
      // do an async request   
      back = ti.asyncRequest(parm);
  }
  catch (Exception e) {}
  
  // When nothing back
  if  (back == null)  {
          
      return -1;
  
  } // endif      
  
  return back.getReturnCode(); 
  
} // end-method

/**
 * Close the tymeac GUI display 
 */
public void closeMenu() {

		// stop thread
		it.stopMe();

} // end-if

/**
 * open the tymeac GUI display
 */
public void openMenu() {

		// new threads window thread
		it = new TyDemoInternalTestThread(ti);

		it.start();

} // end-if

/**
 * Send four async requests to the server
 * @return java.lang.String
 */
public String sendSingle() {
  
  // class objects for the four tests	
  Class<? extends Task> timedC    = new StallTimedWaiting().getClass();
  Class<? extends Task> waitingC  = new StallWaiting().getClass();
  Class<? extends Task> blockingC = new StallBlocking().getClass();
  Class<? extends Task> runningC  = new StallRunning().getClass();
           
// waiting  
  int rc = callServer(new TymeacParm (waitingC));
  
  // When failed, say so
  if  (rc != 0) return "Waiting RC= " + rc;
  
// timed waiting  
  rc = callServer(new TymeacParm (timedC));
  
  // When failed, say so
  if  (rc != 0) return "TimedWaiting RC= " + rc;
 
// blocking  
  rc = callServer(new TymeacParm (blockingC, block_on_me));
  
  // When failed, say so
  if  (rc != 0) return "Blocking RC= " + rc;
  
// running  
  rc = callServer(new TymeacParm (runningC));
  
  // When failed, say so
  if  (rc != 0) return "Running RC= " + rc;  
  
  return "Async submissions good";

} // end-method

/**
 * Start up the server 
 * @return com.tymeac.base.TymeacInterface
 */
public TymeacInterface startServer() {

	// server constructor
	impl = new InternalServer();

	// create the server without start up overrides
	ti = impl.createServer(null);
	
	return ti;

} // end-method

/**
 * stop the server
 * @return java.lang.String
 */
public String stopServer() {
	
		// returned string
		TymeacReturn back = null;
		
		try {                             
		    // do a normal, not kill, shut request   
  		  back = ti.shutRequest();
		}
		catch (Exception e) {}
  
    // When nothing
    if  (back == null)  {
            
        // bye
        return "Did not complete properly";

    } // endif            
    
    // what came back
    return back.getTyMessage();

} // end-method
} // end-class
