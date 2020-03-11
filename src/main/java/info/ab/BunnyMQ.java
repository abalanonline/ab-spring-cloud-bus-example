package info.ab;

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class BunnyMQ extends MessageProducerSupport implements MessageHandler, Runnable, MessageChannel {

  BlockingQueue<Message<?>> messageQueue = new LinkedBlockingDeque<>();

  public BunnyMQ() {
    setOutputChannel(this); // set to mock before outputChannel is configured
    new Thread(this, BunnyMQ.class.getSimpleName()).start(); // start BunnyMQ!
  }

  @Override
  public void run() { // core of the Bunny Message Queue
    while (true) {
      try {
        sendMessage(messageQueue.take());
      } catch (InterruptedException e) {
        break; // exiting
      }
    }
  }

  @Override
  public void handleMessage(Message<?> message) {
    messageQueue.add(message);
  }

  @Override // mock MessageChannel
  public boolean send(Message<?> message) {
    return false;
  }

  @Override // mock MessageChannel
  public boolean send(Message<?> message, long l) {
    return false;
  }
}
