package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
@Builder
public class DummyMessageHandler implements MessageHandler {
    ProducerDestination destination;
    ExtendedProducerProperties<DummyProducerProperties> producerProperties;
    MessageChannel errorChannel;
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        producerProperties.getExtension().getMq().publish(destination.getName(), message);
    }
}
