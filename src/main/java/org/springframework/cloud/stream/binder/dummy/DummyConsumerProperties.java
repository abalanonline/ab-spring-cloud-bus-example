package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.Data;
import org.springframework.integration.core.MessageProducer;

@Data
@Builder
public class DummyConsumerProperties {
    String channelName;
    final DummyMQ mq;
    MessageProducer messageProducer;
}
