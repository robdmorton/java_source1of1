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

import com.tymeac.dse.base.TymeacInterface;
import com.tymeac.dse.client.jframe.TyQueThd;

/** 
 *  Que threads display thread
 */
public class TyDemoInternalTestThread extends Thread {

		private TymeacInterface ti = null;

		private volatile boolean stopme = false;

/**
 * Constructor
 * @param TyI com.tymeac.base.TymeacInterface
 */
public TyDemoInternalTestThread(TymeacInterface TyI) {

		// give it a name		
		super("TyQueThdThread");

		// passed reference to tymeac 
		ti = TyI;

} // end-constructor

/**
 * display the QueThd window
 */
public void run() {

	// new object passing the server reference
	TyQueThd threads = new TyQueThd(ti);

	// make visable
	threads.setVisible(true);

	// lock on this monitor
	synchronized (this) {

		// till stopped
		while (!stopme) {

				// wait for a notifyAll
				try {
						wait();

				} // end-try

				catch (InterruptedException e) {}

		} // end while		
	} // end-sync

	// no longer visable
	threads.setVisible(false);

} // end-method

/**
 * stop the display
 */
public synchronized void stopMe() {

	// set to end wait
	stopme = true;

	// wake up thread
	notifyAll();

} // end-method
} // end-class
