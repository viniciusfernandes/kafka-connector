package br.com.viavarejo.kafkaconnector.connector.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectSerializer extends ObjectSerializer<Object> {

    @Override
    public byte[] serialize(final String topic, final Object obj) {

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj).getBytes();
        } catch (final Exception e) {
            throw new IllegalStateException(String.format("Falha na criaçao do serializador da classe %s", obj.getClass().getName()));
        }
    }

}
