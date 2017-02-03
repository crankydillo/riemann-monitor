# riemann-monitor

Submits riemann events as long as the process it launches is running.

I should work on a fork of [this
project](https://github.com/samn/run-and-report); however, I couldn't get it to
'build' and I only have a few hours to work on this.  Beyond existing
functionality, I'm assuming python would have a much smaller footprint than a
JVM application, which is a very desirable feature for this type of app.
Submitted [this issue](https://github.com/samn/run-and-report/issues/5).

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
mvn install
```

### Running

```sh
mvn compile
mvn exec:java
```

### SBT

I often use SBT if I'm doing a vim+tmux thing.  It's there if you want it.  At
time of writing, I'm using sbt 0.13.13.