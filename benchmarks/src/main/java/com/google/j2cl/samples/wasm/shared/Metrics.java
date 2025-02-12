package com.google.j2cl.samples.wasm.shared;

// This is a mess because J2CL doesn't support String.format yet.

public class Metrics {
  public static String format(String benchmarkName, long[] timingsNanos) {
    var run = "";
    for (var timing : timingsNanos) {
      run += (timing / 1000.0) + "\n";
    }

    return run;
  }

  static String compute(long[] timingsNanos) {
    // Convert to microseconds
    double[] timingsUs = new double[timingsNanos.length];
    for (int i = 0; i < timingsNanos.length; i++) {
      timingsUs[i] = timingsNanos[i] / 1000.0; // Convert ns to µs
    }

    // Calculate mean
    double sum = 0;
    for (double timing : timingsUs) {
      sum += timing;
    }
    double mean = sum / timingsUs.length;

    // Calculate standard deviation
    double sumSquaredDiff = 0;
    for (double timing : timingsUs) {
      double diff = timing - mean;
      sumSquaredDiff += diff * diff;
    }
    double standardDeviation = Math.sqrt(sumSquaredDiff / (timingsUs.length - 1));

    // Calculate standard error
    double standardError = standardDeviation / Math.sqrt(timingsUs.length);

    // Calculate margin of error (99.9% confidence level, t-value ≈ 3.291 for large samples)
    double marginOfError = 3.291 * standardError;

    // Format output with 3 decimal places
    return mean + " ± " + marginOfError + " us/op";
  }
}