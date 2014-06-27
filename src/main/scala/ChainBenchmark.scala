import org.scalameter.api._
import parsers.truffle.{ParserState, Tests, UninitializedNonterminalCall}

object ChainBenchmark
extends PerformanceTest {

  val sizes = Gen.enumeration("chainLength")(50, 100, 150)

  val unoptimizedParsers = sizes map (chainLength => {
    UninitializedNonterminalCall.callNodeType = UninitializedNonterminalCall.CallNodeType.UNOPTIMIZED
    val parser = new ParserState(Tests.repeat('a', 150));
    val startSymbol = Tests.createChainedProductions(parser, chainLength)
    (parser, startSymbol)
  })

  performance of "Long chains" config (
    // Just want to run one VM, but the Graal-enabled one with custom flags.
    exec.independentSamples -> 1,
    exec.jvmcmd -> "/home/stefan/opt/graalvm-jdk1.8.0-0.3/bin/java",
    exec.jvmflags -> "-server -Xss32m -Dtruffle.TraceRewrites=true -Dtruffle.DetailedRewriteReasons=true -G:+TraceTruffleCompilationDetails -G:+TraceTruffleCompilation -G:TruffleCompilationThreshold=1 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*::executeHelper"
    ) in {
    performance of "unoptimized" in {
      using(unoptimizedParsers) in {
        unoptimizedParser => {
          val (parser, startSymbol) = unoptimizedParser
          parser.resetParserState()
          parser.parse(startSymbol)
        }
      }
    }
  }

  override def executor: Executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.min,
    new Measurer.Default)

  override def reporter: Reporter = LoggingReporter()

  override def persistor: Persistor = Persistor.None
}
