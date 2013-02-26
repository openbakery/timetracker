#!/bin/sh

export GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"

gradle -Divy.cache.ttl.default=eternal jettyRun