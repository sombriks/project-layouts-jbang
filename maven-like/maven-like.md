# Maven-like

The `src/{main,test}/{java,resources}` directory structure.

## Initial Setup

```bash
mkdir -p src/{main,test}/{java,resources}
mkdir -p src/{main,test}/java/org/example/{cart,model}
touch src/main/java/org/example/model/{Product,Customer}.java
touch src/main/java/org/example/cart/{CartItem,Cart}.java
touch src/test/java/org/example/cart/CartTest.java
touch src/{main,test}/resources/application.yaml
jbang init --deps=tools.jackson.dataformat:jackson-dataformat-yaml:3.1.1\
 --java 25\
 src/main/java/org/example/App.java
jbang init --deps=tools.jackson.dataformat:jackson-dataformat-yaml:3.1.1,\
org.hamcrest:hamcrest:3.0,\
org.junit.jupiter:junit-jupiter:6.1.3,\
org.junit.platform:junit-platform-console-standalone:6.1.3\
 src/test/java/org/example/AppTest.java
```

## How to run

```bash
jbang src/main/java/org/example/App.java
```

## How to test

```bash
jbang src/test/java/org/example/AppTest.java
```

## Project export

```bash
jbang export gradle src/main/java/org/example/App.java
```

## Intellij integration

Using this layout with two entrypoints, the plugin works flawlessly, syncing 
the classpath accordingly to the current entrypoint. If it complains about a 
missing package, simply sync the project and make sure that the correct jbang 
root is selected.

Once source, resource and test folders get proper setup, both entrypoints 
deliver a good IDE/autocomplete experience.

But individual testcase execution does not work like standard intellij 
projects. For those scenarios, the project export and manual copy of test
resources and sources are the best approach for now.

## Noteworthy

- For this example, two different enty points where provisioned. But the test 
  one also includes sources and resources from the first. That way, the regular 
  classpath doesn't get polluted with test-specific classes.
- For some reason the resource folders does not produce the overrides people 
  expect from maven/gradle. Need more investigation on that.
- The test entry point fails to export a valid project.
