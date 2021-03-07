package br.com.viavarejo.kafkaconnector.connector.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.viavarejo.kafkaconnector.connector.KConsumer;

public class KBatchConsumer<K, V> extends KConsumer<K, V, List<V>> {
    final Logger logger = LoggerFactory.getLogger(getClass());

    public KBatchConsumer(final List<String> hosts) {
        super(hosts);
    }

    public KBatchConsumer(final String groupId) {
        super(groupId);
    }

    @Override
    public void commitOffset(final Consumer<List<V>> callback, final Consumer<List<V>> onError,
                    final org.apache.kafka.clients.consumer.Consumer<K, V> kConsumer, final ConsumerRecords<K, V> records) {
        final List<V> values = new ArrayList<>(50);
        for (final ConsumerRecord<K, V> rec : records) {
            values.add(rec.value());
        }
        try {
            callback.accept(values);
        } catch (final Exception e) {
            kConsumer.close();
            throw e;
        }

        try {
            kConsumer.commitAsync();
        } catch (final CommitFailedException e) {
            logger.error("Falha ao atualizar/commitar o valor do offset em BATCH para o kafka. ", e);

            if (onError != null) {
                onError.accept(values);
            }

        }
    }

}
