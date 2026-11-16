///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//DEPS tools.jackson.dataformat:jackson-dataformat-yaml:3.1.1
//FILES ../../../resources
//SOURCES **/*.java

package org.example;

import org.example.model.Product;
import org.example.model.ProductCatalog;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.List;

public class App {

    public List<Product> getProducts() throws IOException {
        ObjectMapper mapper = YAMLMapper.builder().build();
        try (var input = App.class.getResourceAsStream("/application.yaml")) {
            if (input == null) {
                throw new IllegalStateException("application.yaml was not found on the classpath");
            }
            ProductCatalog catalog = mapper.readValue(input, ProductCatalog.class);
            return catalog.products();
        }
    }

    void main(String... args) throws IOException {
        IO.println(new App().getProducts());
    }
}
