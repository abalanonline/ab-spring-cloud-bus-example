package org.springframework.cloud.stream.binder.dummy;

import lombok.SneakyThrows;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DummyMQ {
  Map<String, BlockingQueue<Message<?>>> queues = new HashMap<>();
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
  public void createQueue(String queue) {
    queues.computeIfAbsent(queue, x -> new LinkedBlockingDeque<>());
  }
  public void subscribe(String topic, String queue) {
    subs.computeIfAbsent(topic, x -> new HashSet<>());
    subs.get(topic).add(queue);
  }
  @SneakyThrows
  public void publish(String topic, Message<?> message) {
    topics.get(topic).put(message);
  }
}
