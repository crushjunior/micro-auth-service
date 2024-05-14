package ru.charushnikov.microauthservice.kafka.listener;

public interface MessageListener<T> {

    void listenMessage(T message);
}
