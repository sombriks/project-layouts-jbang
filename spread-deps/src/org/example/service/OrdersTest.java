//DEPS org.junit.jupiter:junit-jupiter:6.1.3
//DEPS org.hamcrest:hamcrest:3.0

package org.example.service;

import org.example.model.Item;
import org.example.model.Order;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class OrdersTest {

    @Test
    void shouldLoad() throws Exception {
        var orders = new Orders();
        var order = new Order("Jackson", Set.of(new Item("Banana", 11)));
        orders.save(order);
        assertThat(orders.list(null), notNullValue());
    }
}
