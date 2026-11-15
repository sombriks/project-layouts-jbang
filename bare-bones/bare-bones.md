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

## Project export results

By running:

```bash
jbang export gradle Bare.java
```

A perfectly valid gradle project is produced, no major issues. The same is 
valid for `jbang export maven`.

## Intellij jbang plugin

This is the basic case for the plugin. Works perfectly.

## Noteworthy

- If you define the `Item` record outise the `Bare.java` script, you need to 
  add the `//SOURCES Item.java` directive to the script.
