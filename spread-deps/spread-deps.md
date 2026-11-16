# Spread deps

It is possible, but not sure if it's a good idea.

Instead of centralize all deps at the entry point, deps appear closet to whare 
they will be consumed.

## Initial Setup

```bash
mkdir -p src/org/example
mkdir resources
jbang init --java 21 \
--deps=tools.jackson.dataformat:jackson-dataformat-xml:3.3.0,\
org.junit.platform:junit-platform-console-standalone:6.1.3,\
org.junit.jupiter:junit-jupiter:6.1.3,\
org.hamcrest:hamcrest:3.0 \
src/Build.java
```

## How to run

## How to test

## Project export result

## Intellij plugin behavior

## Noteworthy
