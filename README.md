# riemann-monitor

Submits [riemann](http://riemann.io) events as long as the process it launches is running.

I should work on a fork of [this
project](https://github.com/samn/run-and-report); however, I couldn't get it to
'build' and I only have a few hours to work on this.  Beyond existing
functionality, I'm assuming python would have a much smaller footprint than a
JVM application, which is a very desirable feature for this type of app.
Submitted [this issue](https://github.com/samn/run-and-report/issues/5).

A big issue will be how much OS resources we use..

# Development

### Need a riemann server

Do what you want, but you can use [this](docker/riemann-server).  Install docker.  Then

```sh
cd docker/riemann-server
./build.sh
./run.sh riemann-cli-monitor
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
