package org.springframework.cloud.stream.binder.sqs.provisioning;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.stream.binder.dummy.DummyMQ;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;

@Data
@Builder
public class DummySqsConsumerDestination implements ConsumerDestination {
    String name;
}
