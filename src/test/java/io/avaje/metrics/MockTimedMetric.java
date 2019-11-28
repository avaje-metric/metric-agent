package io.avaje.metrics;

/**
 * Test Double
 */
public class MockTimedMetric implements TimedMetric {

  private final String name;

  private int count;

  MockTimedMetric(String name) {
    this.name = name;
  }

  @Override
  public void add(long startNanos) {
    testOperationEnd(true, startNanos, false);
  }

  @Override
  public void add(long startNanos, boolean activeThreadContext) {
    testOperationEnd(true, startNanos, activeThreadContext);
  }

  @Override
  public void addErr(long startNanos) {
    testOperationEnd(false, startNanos, false);
  }

  @Override
  public void addErr(long startNanos, boolean activeThreadContext) {
    testOperationEnd(false, startNanos, activeThreadContext);
  }

  private void testOperationEnd(boolean success, long startNanos, boolean activeThreadContext) {
    long exeNanos = System.nanoTime() - startNanos;
    System.out.println("... " + name + " testOperationEnd exe:" + exeNanos + " success:" + success + " activeThreadContext:" + activeThreadContext);
    count++;
    MetricManager.testOperationEnd(name, success, activeThreadContext);
  }

  @Override
  public boolean isRequestTiming() {
    return true;
  }

  /**
   * Return the count for the metric.
   */
  public int testGetCount() {
    return count;
  }

  /**
   * Reset count back to 0.
   */
  public void testReset() {
    count = 0;
  }
}
