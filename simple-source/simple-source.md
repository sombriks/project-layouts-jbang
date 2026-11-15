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
jbang export maven src/sample/Simple.java
```

Which works as expected.

## Jbang plugin integration

Also works as expected, with a small note.

## Noteworthy

- In IDE configuration, the `src` folder must added as source folder.
- But then, the IDE will scan and identify all classes and packagers, but jbang 
  fails if `//SOURCES Item.java` isn't present in the main script. Without it, 
  jbang does not see the other class in the package, even project export fais. 
  The **IDE** says that everything if fine but the project does not run.
