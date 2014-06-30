import org.scalameter.Parameters
import org.scalameter.api._
import parsers.truffle.{NonterminalName, ParserState, Tests, UninitializedNonterminalCall}

import scala.collection.mutable

object ChainBenchmark
extends PerformanceTest.OfflineReport {

  val sizes = biggerWarmupset(Gen.enumeration("chainLength")(50, 100, 150))

  def biggerWarmupset[T](t: Gen[T]): Gen[T] = new Gen[T] {
    def warmupset = dataset map generate
    def dataset = t.dataset
    def generate(params: Parameters) = t.generate(params)
  }

  performance of "LongChains" config (
    // Just want to run one VM, but the Graal-enabled one with custom flags.
    exec.independentSamples -> 1,
    exec.jvmcmd -> "/home/stefan/opt/graalvm-jdk1.8.0-0.3/bin/java",
    exec.jvmflags -> "-server -Xss32m -Dtruffle.TraceRewrites=true -Dtruffle.DetailedRewriteReasons=true -G:+TraceTruffleCompilationDetails -G:+TraceTruffleCompilation -G:TruffleCompilationThreshold=1 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*::executeHelper"
    ) in {
    val parsers: mutable.Map[Int, (ParserState, NonterminalName)] = mutable.HashMap()

    def setupParser(mode: UninitializedNonterminalCall.CallNodeType) = (chainLength: Int) => {
      parsers getOrElseUpdate(chainLength, {
        UninitializedNonterminalCall.callNodeType = mode
        val parser = new ParserState(Tests.repeat('a', 150))
        val startSymbol = Tests.createChainedProductions(parser, chainLength)

        println(s"start our warmup loop for ${chainLength}")
        for (i <- 1 to 10000) {
          parser.resetParserState()
          parser.parse(startSymbol)
        }
        println(s"finished our warmup loop for ${chainLength}")

        (parser, startSymbol)
      })
    }

    performance of "unoptimized" in {
      using(sizes) setUp {
        setupParser(UninitializedNonterminalCall.CallNodeType.UNOPTIMIZED)
      } in {
        chainLength => {
          val (parser, startSymbol) = parsers(chainLength)
          parser.resetParserState()
          parser.parse(startSymbol)
        }
      }
    }

    performance of "cached" in {
      using(sizes) setUp {
        setupParser(UninitializedNonterminalCall.CallNodeType.CACHEDCALL)
      } in {
        chainLength => {
          val (parser, startSymbol) = parsers(chainLength)
          parser.resetParserState()
          parser.parse(startSymbol)
        }
      }
    }
  }
}
