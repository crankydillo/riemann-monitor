# Introduction

Submits [riemann](http://riemann.io) events as long as the process it launches is running.

I should work on a fork of [this
project](https://github.com/samn/run-and-report); however, I couldn't get it to
'build' and I only have a few hours to work on this.  Beyond existing
functionality, I'm assuming python would have a much smaller footprint than a
JVM application, which is a very desirable feature for this type of app.
Submitted [this issue](https://github.com/samn/run-and-report/issues/5).

A big issue will be how much OS resources we use..

# Install

I only have a snapshot deployed.  Instructions for linux/mac.  Install Java 8.
Have write permisions to `/opt`.

```sh
curl -L "http://oss.sonatype.org/service/local/artifact/maven/content?r=snapshots&g=org.beeherd.riemann&a=riemann-monitor&e=zip&v=LATEST" > riemann-monitor-latest-snapshot.zip
unzip riemann-monitor-latest-snapshot.zip -d /opt/
export PATH=/opt/riemann-monitor-0.0.1-SNAPSHOT/bin:$PATH
riemann-monitor
```

# Development

### Need a riemann server?

Do what you want, but you can use [this](docker/riemann-server).  Install docker.  Then

```sh
cd docker/riemann-server
./build.sh
./run-server.sh
```

### Building

```sh
mvn compile
```

### Running

Assuming you have riemann listening to port 5555 on localhost.  The command assumes bash/sh/etc (something that has `sleep 30`).

```sh
mvn compile
mvn exec:java -Dexec.args="localhost 5555 riemann-cli-monitor 5 -- sleep 30"
```

### SBT

I often use SBT if I'm doing a vim+tmux thing.  It's there if you want it.  At
time of writing, I'm using sbt 0.13.13.

# On riemann

[Good article](https://kartar.net/2015/01/using-riemann-for-fault-detection/)
on how riemann uses a push-based system for determining system anomalies.
