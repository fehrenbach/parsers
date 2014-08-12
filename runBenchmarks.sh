#!/bin/bash

mvn scala:run -DmainClass=ChainBenchmark -DaddArgs="-CresultDir tmp" > /dev/null
mvn scala:run -DmainClass=ChainOuterBenchmark -DaddArgs="-CresultDir tmp" > /dev/null
mvn scala:run -DmainClass=HandwrittenChainBenchmark -DaddArgs="-CresultDir tmp" > /dev/null
