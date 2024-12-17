package org.acme.application.ports.messaging;

public interface MessageConsumer<T> {

   void consume(T payload);
}
