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
