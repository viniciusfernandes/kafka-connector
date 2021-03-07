package br.com.viavarejo.kafkaconnector.connector.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

public abstract class ObjectDeserializer<T> implements Deserializer<T> {

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {}

    @Override
    public void close() {}

}
