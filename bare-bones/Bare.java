///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17
//DEPS tools.jackson.core:jackson-databind:3.3.0

import tools.jackson.databind.json.JsonMapper;

record Item(String name, int quantity) { }

public class Bare {

    public static void main(String... args) {
        var naooer = new JsonMapper();
        var item = new Item("naooer", 1);
        System.out.println(naooer.writeValueAsString(item));
    }
}
