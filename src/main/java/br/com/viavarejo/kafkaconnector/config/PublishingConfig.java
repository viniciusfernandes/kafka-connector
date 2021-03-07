package br.com.viavarejo.kafkaconnector.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.FloatSerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.ShortSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class PublishingConfig extends ConnectionConfig {

    private Class<? extends Serializer<?>> keySerializer;
    private Class<? extends Serializer<?>> messageSerializer;

    private static final Map<Class<?>, Class<? extends Serializer<?>>> serializerMap;

    static {
        serializerMap = new HashMap<>();
        serializerMap.put(String.class, StringSerializer.class);
        serializerMap.put(Long.class, LongSerializer.class);
        serializerMap.put(Integer.class, IntegerSerializer.class);
        serializerMap.put(Short.class, ShortSerializer.class);
        serializerMap.put(byte[].class, ByteArraySerializer.class);
        serializerMap.put(Float.class, FloatSerializer.class);
        serializerMap.put(Double.class, DoubleSerializer.class);
    }

    public PublishingConfig configMessageSerializer(final Class<?> serializerType) {
        messageSerializer = resolveSerializer(serializerType);
        return this;
    }

    public PublishingConfig configKeySerializer(final Class<?> serializerType) {
        keySerializer = resolveSerializer(serializerType);
        return this;
    }

    public PublishingConfig configCustomMessageSerializer(final Class<? extends Serializer<?>> serializerClass) {
        messageSerializer = serializerClass;
        return this;
    }

    public PublishingConfig configCustomKeySerializer(final Class<? extends Serializer<?>> serializerClass) {
        keySerializer = serializerClass;
        return this;
    }

    public Class<? extends Serializer<?>> getKeySerializer() {
        return keySerializer;
    }

    public Class<? extends Serializer<?>> getMessageSerializer() {
        return messageSerializer;
    }

    private Class<? extends Serializer<?>> resolveSerializer(final Class<?> clazz) {
        if (clazz == null) {
            return StringSerializer.class;
        }

        return serializerMap.get(clazz);
    }

    public boolean hasConfigSerializer() {
        return keySerializer != null && messageSerializer != null;
    }

}
