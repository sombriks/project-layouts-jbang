///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 11
//DEPS com.google.code.gson:gson:2.15.0
//SOURCES Item.java

package sample;

import com.google.gson.Gson;

import static java.lang.System.*;

public class Simple {

    public static void main(String... args) {
        Gson gson = new Gson();
        Item item = new Item("apple", 10);
        out.println(gson.toJson(item));
    }
}
