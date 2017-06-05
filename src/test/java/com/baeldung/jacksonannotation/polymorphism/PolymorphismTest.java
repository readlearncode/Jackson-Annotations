package com.baeldung.jacksonannotation.polymorphism;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class PolymorphismTest {

    @Test
    public void whenSerializingUsingPolymorphism_thenCorrect() throws JsonProcessingException {

        Type internalType = new InternalType();
        internalType.id = 250;
        internalType.name = "staff";

        Order order = new Order(internalType);

        String result = new ObjectMapper().writeValueAsString(order);

        assertThat(from(result).getString("type.ordertype")).isEqualTo("internal");

        /*
            {
              "id": "7fc898e3-b4e7-41b0-8ffa-664cf3663f2e",
              "type": {
                "ordertype": "internal",
                "id": 250,
                "name": "staff"
              }
            }
        */

    }


    @Test
    public void whenSerializingUsingPolymorphism_thenSerialiseAnotherType() throws JsonProcessingException {

        Type anotherType = new AnotherType();
        anotherType.id = 20;
        anotherType.name = "Type2";

        Order order = new Order(anotherType);

        String result = new ObjectMapper().writeValueAsString(order);

        assertThat(from(result).getString("type.ordertype")).isEqualTo("other");

        /*
            {
              "id": "6aee03f5-d09b-4c53-b67b-411247fbe8b0",
              "type": {
                "ordertype": "other",
                "id": 20,
                "name": "Type2"
              }
            }
        */

    }

    @Test
    public void whenDeserializingPolymorphic_thenCorrect() throws IOException {

        String orderJson = "{\"type\":{\"ordertype\":\"internal\",\"id\":100,\"name\":\"directors\"}}";

        Order order = new ObjectMapper().readerFor(Order.class).readValue(orderJson);

        assertThat(from(orderJson).getString("type.ordertype")).isEqualTo("internal");
        assertThat(((InternalType) order.getType()).name).isEqualTo("directors");
        assertThat(((InternalType) order.getType()).id).isEqualTo(100);
        assertThat(order.getType().getClass()).isEqualTo(InternalType.class);
    }
}