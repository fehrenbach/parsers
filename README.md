Run with these VM options to see something, at least when nodes are rewritten, which is a start I guess.

```-Dtruffle.TraceRewrites=true -Dtruffle.DetailedRewriteReasons=true -G:+TraceTruffleCompilationDetails -G:+TraceTruffleCompilation -G:TruffleCompilationThreshold=1 -XX:+UnlockDiagnosticVMOptions```

Add `-Xss16m` to run the larger parser tests.

In a Graal/Truffle-JVM, the UNOPTIMIZED nonterminal lookup is two times slower than the CACHEDEXECUTE. CACHEDCALL, which uses Truffle function calls is ten times slower than CACHEDEXECUTE, don't ask me why.
