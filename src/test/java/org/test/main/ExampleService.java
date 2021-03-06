package org.test.main;

import io.avaje.metrics.MetricManager;
import io.avaje.metrics.TimedMetric;
import io.avaje.metrics.annotation.NotTimed;

@NotTimed
public class ExampleService extends BaseService {

  private static TimedMetric _$metric_1;
  private static TimedMetric _$metric_2;
  private static TimedMetric _$metric_3;

  static {
    _$initMetrics();
  }

  private static void _$initMetrics() {
    _$metric_1 = MetricManager.timed("simpleSerivce.doSomething");
    _$metric_2 = MetricManager.timed("simpleSerivce.doSomethingElse");
    _$metric_3 = MetricManager.timed("simpleSerivce.doBucketSomething", 100, 200);
  }

  public void doSomething() throws InterruptedException {
    long _$metricStart = System.nanoTime();
    try {
      System.out.println("123");
      // Thread.sleep(100);
      _$metric_1.add(_$metricStart);
    } catch (RuntimeException e) {
      _$metric_1.addErr(_$metricStart);
    }
  }


  public void doBucketSomething() throws InterruptedException {
    long _$metricStart = System.nanoTime();
    try {
      System.out.println("123");
      // Thread.sleep(100);
      _$metric_3.add(_$metricStart);
    } catch (RuntimeException e) {
      _$metric_3.addErr(_$metricStart);
    }
  }

  public void doSomethingElse(int opCode) {
    long start = System.nanoTime();
    System.out.println("not very interesting");
    // int opCode = 123;
    //_$metric_2.operationEnd(System.nanoTime() - start, opCode);
    _$metric_2.add(start);
  }

  // public String findAll(String orderBy) {
  // return super.findAll(orderBy);
  // }

  public String delete() {
    return super.delete();
  }
}
