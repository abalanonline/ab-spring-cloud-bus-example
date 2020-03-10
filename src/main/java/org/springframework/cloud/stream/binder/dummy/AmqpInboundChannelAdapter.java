package org.springframework.cloud.stream.binder.dummy;

import org.springframework.integration.endpoint.MessageProducerSupport;

public class AmqpInboundChannelAdapter extends MessageProducerSupport {

/*
  private final AbstractMessageListenerContainer messageListenerContainer;

  public AmqpInboundChannelAdapter(AbstractMessageListenerContainer listenerContainer) {
    this.messageListenerContainer = listenerContainer;
    this.messageListenerContainer.setAutoStartup(false);
  }

  @Override
  protected void onInit() {
    this.messageListenerContainer.setMessageListener(new Listener());
    this.messageListenerContainer.afterPropertiesSet();
    super.onInit();
  }

  protected class Listener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(final Message message, final Channel channel) throws Exception {
      Object payload = (new SimpleMessageConverter()).fromMessage(message);
      Map<String, Object> headers = DefaultAmqpHeaderMapper.inboundMapper()
          .toHeadersFromRequest(message.getMessageProperties());
      final org.springframework.messaging.Message<Object> messagingMessage = getMessageBuilderFactory()
          .withPayload(payload)
          .copyHeaders(headers)
          .build();
      sendMessage(messagingMessage);
    }

  }
*/

}
