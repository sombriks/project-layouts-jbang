///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//DEPS tools.jackson.dataformat:jackson-dataformat-yaml:3.1.1
//SOURCES **/*.java
//FILES ../../../resources

package org.example;

public class App {
    void main(String... args) {
        IO.println("Hello World");
    }
}

