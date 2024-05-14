package ru.charushnikov.microauthservice.kafka.publisher;

public interface MessagePublisher<T> {

    void publish(T message);
}
