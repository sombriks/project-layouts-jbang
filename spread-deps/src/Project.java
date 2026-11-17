///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS org.junit.platform:junit-platform-console-standalone:6.1.3
//SOURCES **/*.java
//FILES ../resources

import org.junit.platform.console.ConsoleLauncher;

import static java.lang.System.*;

public class Project {

    public static void main(String... args) {
        if(args.length == 0)
            out.println("missing argument: run|test");
        else if("test".equals(args[0]))
            ConsoleLauncher.main("execute", "--select-package", "org.example");
    }
}
