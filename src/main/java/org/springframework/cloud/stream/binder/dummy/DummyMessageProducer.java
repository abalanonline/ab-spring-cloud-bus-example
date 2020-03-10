package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;

@Data
@Builder
public class DummyMessageProducer implements MessageProducer {
  private MessageChannel outputChannel;
  private ConsumerDestination destination;
  private String group;
  private ExtendedConsumerProperties<DummyConsumerProperties> properties;
}
