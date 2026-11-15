package org.example.cart;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class CartTest {

    @Test
    void shouldPass() {
        assertThat(new Cart(), not(nullValue()));

    }
}
