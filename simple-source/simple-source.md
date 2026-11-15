# Simple source folder

Everything in a `src` folder.

## Initial Setup

```bash
mkdir -p src/sample
jbang init --java 11\
 --deps=com.google.code.gson:gson:2.15.0\
 src/sample/Simple.java
touch src/sample/Item.java
```

## How to run

```bash
jbang run src/sample/Simple.java
```

## Project export

```bash
jbang export gradle src/sample/Simple.java
```

## Jbang plugin integration

## Noteworthy

