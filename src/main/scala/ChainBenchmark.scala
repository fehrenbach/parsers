import org.scalameter.api._

object ChainBenchmark
extends PerformanceTest {

  val sizes = Gen.range("size")(300000, 1500000, 300000)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "Range" in {
    measure method "map" config (
      // Just want to run one VM, but the Graal-enabled one with custom flags.
      exec.independentSamples -> 1,
      exec.jvmcmd -> "/home/stefan/opt/graalvm-jdk1.8.0-0.3/bin/java",
      exec.jvmflags -> "-server -Xss32m -Dtruffle.TraceRewrites=true -Dtruffle.DetailedRewriteReasons=true -G:+TraceTruffleCompilationDetails -G:+TraceTruffleCompilation -G:TruffleCompilationThreshold=1 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*::executeHelper"
      ) in {
      using(ranges) in {
        r => r.map(_ + 1)
      }
    }
  }

  override def executor: Executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.min,
    new Measurer.Default)

  override def reporter: Reporter = ChartReporter(ChartFactory.XYLine())

  override def persistor: Persistor = Persistor.None
}
