package br.com.viavarejo.kafkaconnector.connector.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CustomObjectDeserializer<T> extends ObjectDeserializer<Object> {

    @Override
    public T deserialize(final String topic, final byte[] data) {

        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, typeOf());
        } catch (final Exception e) {
            throw new IllegalStateException(String.format("Falha na criação do deserializador da classe %s", typeOf().getName()), e);
        }
    }

    public abstract Class<T> typeOf();
}
