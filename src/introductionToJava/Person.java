package introductionToJava;

import java.util.List;

public class Person {
  private String name;
  private Integer age;
  private Gender gender;

  public enum Gender {
    MALE, FEMALE
  }

  public Person() {

  }

  Person(String name) {
    this.name = name;

    // defaults for now
    age = 21;
    gender = Gender.MALE;
  }

  void test() {
    int i = name.length();
    i++;
  }

  public Integer getAge() {
    return age;
  }

  public Gender getGender() {
    return gender;
  }

  private void printPerson() {
    // TODO Auto-generated method stub

  }

  public static void printPersons(List<Person> roster, CheckPerson tester) {
    if (roster != null) {
      for (Person p : roster) {
        if (tester.test(p)) {
          p.printPerson();
        }
      }
    }
  }

  public static void main(String a[]) {
    Person p = new Person(a[0]);
    p.toString();
  }
}
