package com.google.j2cl.samples.wasm.baseline;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.*;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.util.concurrent.*;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 20)
@Measurement(iterations = 100)
public class JvmBenchmark {
  @State(Scope.Thread)
  public static class Input {
    public int number = Fannkuch.random.nextInt(12);
  }

  @Benchmark
  public void benchmark(Blackhole blackhole, Input input) {
    blackhole.consume(Fannkuch.compute(input.number));
  }

  public static void main(String[] args) throws RunnerException {
    var opt = new OptionsBuilder()
      .include(JvmBenchmark.class.getSimpleName())
      .forks(1)
      .result("baseline-jvm-benchmark.csv")
      .build();

    new Runner(opt).run();
  }

}
