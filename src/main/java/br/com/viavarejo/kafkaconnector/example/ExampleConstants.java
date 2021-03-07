package br.com.viavarejo.kafkaconnector.example;

public enum ExampleConstants {

    VOUCHER_LIBERADO_TOPIC("VOUCHER_LIBERADO"),
    CREDITO_LIBERADO_TOPIC("CREDITO_LIBERADO"),
    DATA_VALIDADE_VOUCHER_TOPIC("DATA_VALIDADE_VOUCHER"),
    VOUCHER_CLIENTE_TOPIC("VOUCHER_CLIENTE"),
    BROKER("10.220.45.167:9092");

    private final String description;

    private ExampleConstants(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
