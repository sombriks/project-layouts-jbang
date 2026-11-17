//DEPS tools.jackson.dataformat:jackson-dataformat-xml:3.3.0

package org.example.model;

import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record Item(
        @JacksonXmlProperty(isAttribute = true) String description,
        @JacksonXmlProperty(isAttribute = true) int quantity) {
}
