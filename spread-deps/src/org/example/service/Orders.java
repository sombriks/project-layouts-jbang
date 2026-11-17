//DEPS tools.jackson.dataformat:jackson-dataformat-xml:3.3.0

package org.example.service;

import org.example.model.Order;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Orders {

    private final ObjectMapper mapper = XmlMapper.builder().build();

    private Set<Order> orders = new HashSet<>();

    public Orders() {
        var ordersFile = Paths.get("orders.xml");
        if (Files.exists(ordersFile)) {
            Set<Order> loadedOrders = mapper.readValue(ordersFile, new TypeReference<Set<Order>>() {
            });
            orders = loadedOrders == null ? new HashSet<>() : loadedOrders;
        }
    }

    public Set<Order> list(String q) {
        return orders
                .stream()
                .filter(o -> q == null || o.client() == null || o.client().contains(q))
                .collect(Collectors.toSet());
    }

    public void save(Order order) {
        orders.add(order);
        mapper.writeValue(Paths.get("orders.xml"), orders);
    }

    public void remove(Order order) {
        orders.remove(order);
        mapper.writeValue(Paths.get("orders.xml"), orders);
    }
}
