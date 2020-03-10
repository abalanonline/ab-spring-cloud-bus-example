package org.springframework.cloud.stream.binder.dummy;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;

@Data
@Builder
public class DummyConsumerDestination implements ConsumerDestination {
    String name;
}
