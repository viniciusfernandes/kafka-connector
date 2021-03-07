package br.com.viavarejo.kafkaconnector.config;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.ShortDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SubscriptionConfig extends ConnectionConfig {

    private int pollAttempts = 10;
    private int maxNumberMessages = 100;

    private Class<? extends Deserializer<?>> keyDeserializer;
    private Class<? extends Deserializer<?>> messageDeserializer;

    public SubscriptionConfig configStringMessageDeserializer() {
        messageDeserializer = StringDeserializer.class;
        return this;
    }

    public SubscriptionConfig configStringKeyDeserializer() {
        keyDeserializer = StringDeserializer.class;
        return this;
    }

    public SubscriptionConfig configLongMessageDeserializer() {
        messageDeserializer = LongDeserializer.class;
        return this;
    }

    public SubscriptionConfig configLongKeyDeserializer() {
        keyDeserializer = LongDeserializer.class;
        return this;
    }

    public SubscriptionConfig configDoubleMessageDeserializer() {
        messageDeserializer = DoubleDeserializer.class;
        return this;
    }

    public SubscriptionConfig configDoubleKeyDeserializer() {
        keyDeserializer = DoubleDeserializer.class;
        return this;
    }

    public SubscriptionConfig configFloatMessageDeserializer() {
        messageDeserializer = FloatDeserializer.class;
        return this;
    }

    public SubscriptionConfig configFloatKeyDeserializer() {
        keyDeserializer = FloatDeserializer.class;
        return this;
    }

    public SubscriptionConfig configIntegerMessageDeserializer() {
        messageDeserializer = IntegerDeserializer.class;
        return this;
    }

    public SubscriptionConfig configIntegerKeyDeserializer() {
        keyDeserializer = IntegerDeserializer.class;
        return this;
    }

    public SubscriptionConfig configShortMessageDeserializer() {
        messageDeserializer = ShortDeserializer.class;
        return this;
    }

    public SubscriptionConfig configShortKeyDeserializer() {
        keyDeserializer = ShortDeserializer.class;
        return this;
    }

    public SubscriptionConfig configBytesMessageDeserializer() {
        messageDeserializer = ByteArrayDeserializer.class;
        return this;
    }

    public SubscriptionConfig configBytesKeyDeserializer() {
        keyDeserializer = ByteArrayDeserializer.class;
        return this;
    }

    public SubscriptionConfig configCustomMessageDeserializer(final Class<? extends Deserializer<?>> deserializerClass) {
        messageDeserializer = deserializerClass;
        return this;
    }

    public SubscriptionConfig configCustomKeyDeserializer(final Class<? extends Deserializer<?>> deserializerClass) {
        keyDeserializer = deserializerClass;
        return this;
    }

    public Class<? extends Deserializer<?>> getKeyDeserializer() {
        return keyDeserializer;
    }

    public Class<? extends Deserializer<?>> getMessageDeserializer() {
        return messageDeserializer;
    }

    public boolean hasDeserializer() {
        return keyDeserializer != null && messageDeserializer != null;
    }

    public SubscriptionConfig configPollAttempts(final int attempts) {
        pollAttempts = attempts;
        return this;
    }

    public int getPollAttempts() {
        return pollAttempts;
    }

    public SubscriptionConfig configMaxNumberMessages(final int maxNumberMessages) {
        this.maxNumberMessages = maxNumberMessages;
        return this;
    }

    public int getMaxNumberMessages() {
        return maxNumberMessages;
    }

}
