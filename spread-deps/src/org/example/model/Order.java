//DEPS tools.jackson.dataformat:jackson-dataformat-xml:3.3.0

package org.example.model;

import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Set;

public record Order(
        @JacksonXmlProperty(isAttribute = true) String client,
        @JacksonXmlProperty(localName = "item") Set<Item> items) {
}
