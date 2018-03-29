
package pkg_asynch;

import java.util.concurrent.Future;
import javax.ejb.Remote;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@Remote
public interface AsynchronousSessionBeanRemote {

  void slowMethod();

  Future<Long> slowMethodWithReturnValue();
    
}
