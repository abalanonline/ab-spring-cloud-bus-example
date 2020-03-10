package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DummyProducerProperties {
  String channelName;
  final DummyMQ mq;
}
