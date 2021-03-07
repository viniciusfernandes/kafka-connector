package br.com.viavarejo.kafkaconnector.connector.impl;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.viavarejo.kafkaconnector.connector.KConsumer;

public class KSingleConsumer<K, V> extends KConsumer<K, V, V> {
    final Logger logger = LoggerFactory.getLogger(getClass());

    public KSingleConsumer(final List<String> hosts) {
        super(hosts);
    }

    public KSingleConsumer(final String groupId) {
        super(groupId);
    }

    @Override
    public void commitOffset(final Consumer<V> callback, final Consumer<V> onError,
                    final org.apache.kafka.clients.consumer.Consumer<K, V> kConsumer, final ConsumerRecords<K, V> records) {

        for (final ConsumerRecord<K, V> rec : records) {
            try {
                callback.accept(rec.value());
            } catch (final Exception e) {
                kConsumer.close();
                throw e;
            }
            try {
                kConsumer.commitAsync(Collections.singletonMap(new TopicPartition(rec.topic(), rec.partition()),
                                new OffsetAndMetadata(rec.offset() + 1)), null);

            } catch (final CommitFailedException e) {
                logger.error(String.format(
                                "Falha ao atualizar/commitar o valor do offset para o kafka. Topico: %s. Chave: %s. Mensagem: %s. Particao: %s. Offset: %s.",
                                rec.topic(), String.valueOf(rec.key()), String.valueOf(rec.value()), String.valueOf(rec.partition()),
                                rec.offset()), e);

                if (onError != null) {
                    onError.accept(rec.value());
                }
            }
        }
    }

}
