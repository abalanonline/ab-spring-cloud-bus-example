package org.springframework.cloud.stream.binder.sqs.properties;

import lombok.Getter;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;
import org.springframework.cloud.stream.binder.dummy.DummyConsumerProperties;
import org.springframework.cloud.stream.binder.dummy.DummyProducerProperties;

@Getter
public class SqsBindingProperties implements BinderSpecificPropertiesProvider {

    private DummyConsumerProperties consumer = DummyConsumerProperties.builder().build();

    private DummyProducerProperties producer = DummyProducerProperties.builder().build();
}
