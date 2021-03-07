package br.com.viavarejo.kafkaconnector.config;

import java.util.ArrayList;
import java.util.List;

class ConnectionConfig {

    private final List<String> hosts = new ArrayList<>();
    private String clientId;

    public ConnectionConfig addHosts(final List<String> hosts) {
        this.hosts.addAll(hosts);
        return this;
    }

    public ConnectionConfig addHost(final String host) {
        hosts.add(host);
        return this;
    }

    public String appendHosts() {
        if (hosts.isEmpty()) {
            throw new IllegalStateException("É necessário que se configure ao menos 1 host para efetuar uma conexão com o Kafka Server.");
        }

        if (hosts.size() == 1) {
            return hosts.get(0);
        }

        final StringBuilder appendedHosts = new StringBuilder();
        for (final String host : hosts) {

            if (host.trim().length() <= 0) {
                continue;
            }
            if (appendedHosts.length() > 0) {
                appendedHosts.append(",");
            }
            appendedHosts.append(host);
        }

        return appendedHosts.toString();
    }

    public List<String> getHosts() {
        return hosts;
    }

    public ConnectionConfig configClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

}
