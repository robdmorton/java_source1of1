package introductionToJava;

import java.util.Vector;

public class Container {
  Vector<Worker> workers = new Vector<Worker>();

  void addPet(Worker w) {
    workers.addElement(w);
  }

  Worker getPet(int x) {
    return workers.elementAt(x);
  }
}
