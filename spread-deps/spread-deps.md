# Spread deps

It is possible, but not sure if it's a good idea.

Instead of centralize all deps at the entry point, deps appear closet to whare 
they will be consumed.

## Initial Setup

```bash
mkdir -p src/org/example/{model,service,ui}
mkdir resources
touch src/org/example/service/Orders.java
touch src/org/example/ui/Checkout.java
touch src/org/example/model/{Order,Item}.java
touch resources/application.properties
jbang init --java 21 \
--deps=org.junit.platform:junit-platform-console-standalone:6.1.3 \
src/Project.java
```

## How to run

```bash
jbang src/Project.java run
```

## How to test

```bash
jbang src/Project.java test
```

## Project export result

Both `jbang export maven src/Project.java` and
`jbang export gradle src/Project.java` produces _almost_ valid projects. Since 
there are several duplicated `//DEPS` entries all over the project, those 
duplicates also lands in `build.gradle` and `pom.xml`. That is, however, not a 
deal-breaker like the export failure due to files with the same name as seen in 
the [maven-like][maven-like] experiment.

[maven-like]: ../maven-like/maven-like.md

## Intellij plugin behavior

The plugin works with no surprises, jbang is flexible enough to allow some 
degree of _jazz_ in project setup. However, each file containing a `//DEPS` 
directive is considered a jbang root. This might become troublesome, since the 
real root remains being the `Project.java` script.

Have the dependencies declared next to where they are used has mostly 
educational value, therefore makes little sense to pollute the jbang 
project with several possible faulty roots.

Again, for jbang itself that's completely fine. The IDEs are the ones failing 
to keep up with.

## Noteworthy

- One thing to really avoid is bad `//SOURCES` patterns. A
  `//SOURCES src/**/*.java` with the jbang root outside the `src` folder will 
  run just fine with jbang, but the ide will collapse in classpath errors, 
  unable to find classes that where supposed to be in the same package.
- The single root with distinct options to run and test approach avoid the 
  convoluted configuration seen in the [maven-like][maven-like] experiment.
