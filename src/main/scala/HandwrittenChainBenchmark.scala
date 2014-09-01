import org.scalameter.api._
import parsers.truffle.Tests
import parsers.{ChainsOuterRD, ChainsRD}

object HandwrittenChainBenchmark
  extends PerformanceTest.OfflineReport {

  val sizes = Gen.enumeration("dummy")(42)

  override def reporter = Reporter.Composite(CSVReporter(), RegressionReporter(Tester.Accepter(), Historian.Window(1)), DsvReporter(','), super.reporter)

  performance of "handwritten" config(
    exec.minWarmupRuns -> 1000,
    exec.maxWarmupRuns -> 10000,
    exec.benchRuns -> 10000,
    // Just want to run one VM, but the Graal-enabled one with custom flags.
    exec.independentSamples -> 1,
    exec.jvmcmd -> "/home/stefan/opt/graalvm-jdk1.8.0-0.3/bin/java",
    //exec.jvmflags -> "-server -Xss64m -G:+TruffleCompilationExceptionsAreFatal -G:+TraceTruffleInlining -Dtruffle.TraceRewrites=true -Dtruffle.DetailedRewriteReasons=true -G:+TraceTruffleCompilationDetails -G:+TraceTruffleCompilation -G:TruffleCompilationThreshold=1 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*::callRoot"
    exec.jvmflags -> "-server -Xss64m -G:TruffleCompilationThreshold=1"
    ) in {
    val s = Tests.repeat('a', 150)

    measure method("optimal") in {
      using(sizes) in {
        _ => {
          ChainsRD.optimal(s)
        }
      }
    }

    measure method ("inner") in {
      using(sizes) in {
        _ => {
          new ChainsRD(s).s()
        }
      }
    }

    measure method("outer") in {
      using(sizes) in {
        _ => {
          new ChainsOuterRD(s).s()
        }
      }
    }

  }
}