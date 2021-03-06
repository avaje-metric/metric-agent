package org.test.web.api;

import io.avaje.metrics.annotation.NotTimed;
import io.avaje.metrics.annotation.Timed;

@Timed(prefix = "myapi")
public class CustomTimedResource {

  public static void aStaticMethodNotAnnotated() {

  }

  @Timed(name="staticGeneral")
  public static void aStaticMethodWithTimedAnnotation() {

  }

  @Timed(name=" ")
  public String publicMethodWithFullNameWhiteSpace() {
    return null;
  }

  @Timed(name="myname.fully.defined")
  public String publicMethodWithFullName() {
    return null;
  }

  @Timed(name="  ")
  public String publicMethodWithNameWhiteSpace() {
    return null;
  }

  @Timed(name="someRandomName")
  public String publicMethodWithName() {
    return null;
  }

  @NotTimed
  public String publicMethodNotTimed() {
    return null;
  }

  public String publicMethodNormal() {
    System.out.println("IN nakedProtectedMethod");
    System.out.println("... calling myPrivateMethod()");
    myPrivateMethod();
    return null;
  }

  private String myPrivateMethod() {
    System.out.println("IN nakedProtectedMethod");
    return null;
  }

  @Timed
  private String myTimedPrivateMethod() {
    System.out.println("IN myTimedPrivateMethod");
    return "Foo";
  }

  public int hashCode() {
    return 1;
  }

  public String toString() {
    return "bar";
  }

}
