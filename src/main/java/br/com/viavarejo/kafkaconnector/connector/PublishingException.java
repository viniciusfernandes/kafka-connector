package br.com.viavarejo.kafkaconnector.connector;

public class PublishingException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -6836169897567854005L;

    public PublishingException() {}

    public PublishingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PublishingException(final String message) {
        super(message);
    }

}
