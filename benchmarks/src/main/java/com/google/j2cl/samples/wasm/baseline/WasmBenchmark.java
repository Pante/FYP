package com.google.j2cl.samples.wasm.baseline;

import com.google.j2cl.samples.wasm.shared.*;

public class WasmBenchmark {
  static final int WARMUP_ITERATIONS = 20;
  static final int ITERATIONS = 100;
  static final StringBuilder BLACKHOLE = new StringBuilder(); // Prevent JIT from performing dead code elimination.
  static final int BOUNDS = 12;

  public static String benchmark() {
    for (var i = 0; i < WARMUP_ITERATIONS; i++) {
      BLACKHOLE.append(Fannkuch.compute(Fannkuch.random.nextInt(BOUNDS)));
    }

    var timings = new long[ITERATIONS];
    for (var i = 0; i < ITERATIONS; i++) {
      var number = Fannkuch.random.nextInt(BOUNDS);

      var before = System.nanoTime();
      var computed = Fannkuch.compute(number);
      var after = System.nanoTime();

      BLACKHOLE.append(computed);
      timings[i] = after - before;
    }

    var sum = 0L;
    for (var timing : timings) {
      sum += timing;
    }

    return Metrics.format("WasmBenchmark", timings) + "\nBlackhole data (ignore): " + BLACKHOLE;
  }
}

