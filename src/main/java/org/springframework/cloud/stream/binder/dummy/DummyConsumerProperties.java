package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DummyConsumerProperties {
    String channelName;
    final DummyMQ mq;
}
