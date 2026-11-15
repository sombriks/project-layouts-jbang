# Bare Bones

Basic jbang usage: one single script.

## Initial Setup

```bash
jbang init --java 17 --deps=tools.jackson.core:jackson-databind:3.3.0 Bare.java
```

## How to run

```bash
jbang run Bare.java
```

## The export/eject result

By running:

```bash
jbang export gradle Bare.java
```

## Noteworthy

- If you define the `Item` record outise the `Bare.java` script, you need to 
  add the `//SOURCES Item.java` directive to the script.
