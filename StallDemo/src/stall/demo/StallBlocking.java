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

import com.tymeac.dse.base.Task;

/**
 * Task that goes into a never-ending blocking state.
 */
public class StallBlocking extends Task {  
 
    private static final long serialVersionUID = 266374798056742783L;     
    
@Override
public Object compute() {
  
  /*
   * Caller sync'ed on this object in an internal
   *   thread. Therefore, the sync here will wait forever.
   */
  
  Object passed = getInput();
  
  synchronized (passed) {
    
    return Integer.valueOf(-1);
    
  } // end-sync      
  
} // end-method 
  
@Override
public Object complete() {                
  
  // ignored for async calls
  return getOutput();
  
} // end-method
} // end-class
