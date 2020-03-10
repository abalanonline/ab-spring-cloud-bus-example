package org.springframework.cloud.stream.binder.dummy;

import lombok.SneakyThrows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DummyInboundChannelAdapter extends MessageProducerSupport implements Runnable {

  String name;
  DummyConsumerProperties dummyConsumerProperties;
  final BlockingQueue<Message<?>> queue = new LinkedBlockingDeque<>();
  final Thread thread;

  public DummyInboundChannelAdapter(String name, DummyConsumerProperties dummyConsumerProperties) {
    this.name = name;
    this.dummyConsumerProperties = dummyConsumerProperties;
    thread = new Thread(this, name);
    thread.start();
  }

  @SneakyThrows
  public void send(Message<?> message) {
    queue.add(message);
  }

  @Override
  public void run() {
    while (true) {
      Message<?> message;
      try {
        message = queue.take();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      Message<Object> messagingMessage = getMessageBuilderFactory()
          .withPayload((Object) Instant.now())
          .copyHeaders(message.getHeaders())
          .build();
      setOutputChannel(dummyConsumerProperties.getMessageProducer().getOutputChannel());
      //sendMessage(messagingMessage);
      sendMessage(message);
    }
  }

}
