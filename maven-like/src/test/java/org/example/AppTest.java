/// usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS tools.jackson.dataformat:jackson-dataformat-yaml:3.1.1
//DEPS org.hamcrest:hamcrest:3.0
//DEPS org.junit.jupiter:junit-jupiter:6.1.3
//DEPS org.junit.platform:junit-platform-console-standalone:6.1.3
//FILES ../../../resources
//FILES ../../../../main/resources
//SOURCES **/*.java
//SOURCES ../../../../main/java/**/*.java

package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class AppTest {

    @Test
    void shouldPass() throws Exception {
        var result = new App().getProducts();
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
    }

    public static void main(String... args) {
        ConsoleLauncher.main("execute", "--select-package", "org.example");
    }
}
