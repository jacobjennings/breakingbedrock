# build-env-jdk25

How to build the breakingbedrock mod (JDK 25, JAVA_HOME, no java on PATH)

The breakingbedrock mod (26.1.2 fork branch) requires **JDK 25** to build. The system has no `java` on PATH by default (installed `jdk25-openjdk` via pacman; PATH only updates after re-login). To build, export it explicitly:

```sh
export JAVA_HOME=/usr/lib/jvm/java-25-openjdk
export PATH=$JAVA_HOME/bin:$PATH
./gradlew build --no-daemon
```

Build uses Gradle 9.4.0 + `dev.architectury.loom-no-remap` (Minecraft 26.1 is unobfuscated → no mappings). Loadable jars are the unclassified `fabric/build/libs/*.jar` and `neoforge/build/libs/*.jar`; `*-raw.jar` is the pre-shadow Loom output.
