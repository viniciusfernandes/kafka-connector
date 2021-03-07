package br.com.viavarejo.kafkaconnector.example.model.cliente;

import br.com.viavarejo.kafkaconnector.connector.serializer.CustomObjectDeserializer;

public class ClienteDeserializer extends CustomObjectDeserializer<Cliente> {

    @Override
    public Class<Cliente> typeOf() {
        return Cliente.class;
    }

}
