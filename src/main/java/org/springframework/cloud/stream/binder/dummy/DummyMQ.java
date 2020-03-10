package org.springframework.cloud.stream.binder.dummy;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class DummyMQ {

  @Autowired
  DummyMessageChannelBinder dummyMessageChannelBinder;

  Map<String, DummyInboundChannelAdapter> queues = new HashMap<>();
  Map<String, BlockingQueue<Message<?>>> topics = new HashMap<>();
  Map<String, Set<String>> subs = new HashMap<>();

  final Thread thread;

  public DummyMQ() {
    thread = new Thread(() -> {

    });
    //thread.start();
  }

  public void createTopic(String topic) {
    topics.computeIfAbsent(topic, x -> new LinkedBlockingDeque<>());
  }

  public void createQueue(String queue, DummyConsumerProperties dummyConsumerProperties) {
    if (queues.containsKey(queue)) return;
    DummyInboundChannelAdapter adapter = new DummyInboundChannelAdapter(queue, dummyConsumerProperties);
    //dummyMessageChannelBinder.doBindConsumer();
    queues.put(queue, adapter);
  }

  public void subscribe(String topic, String queue) {
    subs.computeIfAbsent(topic, x -> new HashSet<>());
    subs.get(topic).add(queue);
  }

  @SneakyThrows
  public void publish(String topic, Message<?> message) {
    //topics.get(topic).add(message);
    queues.forEach((name, adapter) -> adapter.send(message));
  }

}
