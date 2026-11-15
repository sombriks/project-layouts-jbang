/// usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS tools.jackson.dataformat:jackson-dataformat-yaml:3.1.1
//DEPS org.hamcrest:hamcrest:3.0
//DEPS org.junit.jupiter:junit-jupiter:6.1.3
//DEPS org.junit.platform:junit-platform-console-standalone:6.1.3
//SOURCES **/*.java
//SOURCES ../../../../main/java/**/*.java

package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class AppTest {

    @Test
    void shouldPass() {
        assertThat(new App(), notNullValue());
    }

    public static void main(String... args) {
        ConsoleLauncher.main("execute", "--select-package", "org.example");
    }
}
