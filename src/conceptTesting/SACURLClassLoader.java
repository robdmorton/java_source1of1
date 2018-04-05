package conceptTesting;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;

class SACURLClassLoader extends URLClassLoader {
  /**
   * Creates a new SACURLClassLoader using the default parent class loader for delegation except
   * reversing the order of finding classes. i.e. defer parent loading until AFTER all URLS have
   * been searched.
   *
   * @param urls the URLs from which to load classes and resources
   * @param parent the parent class loader from which to load classes and resources
   *
   */
  public SACURLClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }

  public URL findResource(final String name) {

    TestUtility.printer("SACURLClassLoader:running findResource for class: '" + name + "'");

    return super.findResource(name);
  }

  /**
   *
   *
   * @param name - class name
   * @param resolve - resolve the class now, yes/no?
   *
   */
  protected synchronized Class<?> loadClass(String name, boolean resolve)
      throws ClassNotFoundException {
    TestUtility.printer("SACURLClassLoader:running loadClass for class: '" + name + "'");

    // First, check if the class has already been loaded
    Class<?> c = findLoadedClass(name);

    if (c == null) {
      try {
        TestUtility.printer(
            "SACURLClassLoader:loading class via local URL first for class: '" + name + "'");
        // try URLs FIRST and parent last
        c = findClass(name);
      } catch (ClassNotFoundException e) {
        TestUtility
            .printer("SACURLClassLoader:loading class via delegate for class: '" + name + "'");
        // If still not found, then invoke parent delegate in order
        // to find the class.
        c = super.loadClass(name, resolve);
      }
    }

    if (resolve) {
      resolveClass(c);
    }
    return c;
  }

}

