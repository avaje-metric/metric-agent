package io.avaje.metrics.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper object used to ignore known classes.
 */
class IgnoreClassHelper {

  private static final Set<String> ignoreOneLevel = new HashSet<>();

  private static final Set<String> ignoreTwoLevel = new HashSet<>();

  static  {
    ignoreOneLevel.add("java");
    ignoreOneLevel.add("javax");
    ignoreOneLevel.add("play");
    ignoreOneLevel.add("sbt");
    ignoreOneLevel.add("scala");
    ignoreOneLevel.add("sun");
    ignoreOneLevel.add("sunw");
    ignoreOneLevel.add("oracle");
    ignoreOneLevel.add("groovy");
    ignoreOneLevel.add("kotlin");
    ignoreOneLevel.add("junit");
    ignoreOneLevel.add("microsoft");

    ignoreTwoLevel.add("com/sun");
    ignoreTwoLevel.add("org/aopalliance");
    ignoreTwoLevel.add("org/wc3");
    ignoreTwoLevel.add("org/xml");
    ignoreTwoLevel.add("org/junit");
    ignoreTwoLevel.add("org/apache");
    ignoreTwoLevel.add("org/eclipse");
    ignoreTwoLevel.add("org/joda");
    ignoreTwoLevel.add("com/mysql");
    ignoreTwoLevel.add("org/postgresql");
    ignoreTwoLevel.add("org/h2");
    ignoreTwoLevel.add("com/h2database");
    ignoreTwoLevel.add("org/hsqldb");
    ignoreTwoLevel.add("org/ibex");
    ignoreTwoLevel.add("org/sqlite");
    ignoreTwoLevel.add("ch/qos");
    ignoreTwoLevel.add("org/slf4j");
    ignoreTwoLevel.add("org/codehaus");
    ignoreTwoLevel.add("com/fasterxml");
    ignoreTwoLevel.add("org/assertj");
    ignoreTwoLevel.add("org/hamcrest");
    ignoreTwoLevel.add("org/mockito");
    ignoreTwoLevel.add("org/objenesis");
    ignoreTwoLevel.add("org/objectweb");
    ignoreTwoLevel.add("org/jboss");
    ignoreTwoLevel.add("org/testng");
    ignoreTwoLevel.add("com/intellij");
    ignoreTwoLevel.add("com/google");
    ignoreTwoLevel.add("com/squareup");
    ignoreTwoLevel.add("com/microsoft");
    ignoreTwoLevel.add("io/avaje");
  }

  private final String[] processPackages;

  IgnoreClassHelper(Collection<String> packages) {
    List<String> packageList = new ArrayList<>();
    if (packages != null) {
      for (String aPackage : packages) {
        packageList.add(convertPackage(aPackage));
      }
    }
    this.processPackages = packageList.toArray(new String[packageList.size()]);
  }

  /**
   * Convert dots/periods to slashes in the package name.
   */
  private String convertPackage(String pkg) {

    pkg = pkg.trim().replace('.', '/');

    if (pkg.endsWith("**")) {
      // wild card, remove the **
      return pkg.substring(0, pkg.length() - 2);

    } else if (pkg.endsWith("*")) {
      // wild card, remove the *
      return pkg.substring(0, pkg.length() - 1);

    } else if (pkg.endsWith("/")) {
      // already ends in "/"
      return pkg;

    } else {
      // add "/" so we don't pick up another
      // package with a similar starting name
      return pkg + "/";
    }
  }

  /**
   * Use specific positive matching to determine if the class needs to be
   * processed.
   * <p>
   * Any class at any depth under the package can be processed and all others
   * ignored.
   * </p>
   *
   * @return true if the class can be ignored
   */
  private boolean specificMatching(String className) {

    for (String processPackage : processPackages) {
      if (className.startsWith(processPackage)) {
        // a positive match
        return false;
      }
    }
    // we can ignore this class
    return true;
  }

  /**
   * Try to exclude JDK classes and known JDBC Drivers and Libraries.
   * <p>
   * We want to do this for performance reasons - that is skip checking for
   * enhancement on classes that we know are not part of the application code
   * and should not be enhanced.
   * </p>
   *
   * @param className
   *          the className of the class being defined.
   * @return true if this class should not be processed.
   */
  boolean isIgnoreClass(String className) {

    if (className == null || "bsh/Interpreter".equals(className)) {
      return true;
    }

    className = className.replace('.', '/');

    if (processPackages.length > 0) {
      // use specific positive matching
      return specificMatching(className);
    }

    // we don't have specific packages to process so instead
    // we will ignore packages that we know we don't want to
    // process (they won't contain entity beans etc).

    // ignore $Proxy classes
    if (className.startsWith("$")) {
      return true;
    }

    int firstSlash = className.indexOf('/');
    if (firstSlash == -1) {
      return true;
    }
    String firstPackage = className.substring(0, firstSlash);
    if (ignoreOneLevel.contains(firstPackage)) {
      return true;
    }
    int secondSlash = className.indexOf('/', firstSlash + 1);
    if (secondSlash == -1) {
      return false;
    }
    String secondPackage = className.substring(0, secondSlash);
    return ignoreTwoLevel.contains(secondPackage);
  }
}
