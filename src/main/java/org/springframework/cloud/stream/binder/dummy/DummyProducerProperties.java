package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.Data;

/**
 * The SQS-specific producer binding configuration properties.
 *
 * @author Maciej Walkowiak
 */
@Builder
@Data
public class DummyProducerProperties {
  String channelName;
  final DummyMQ mq;
}
