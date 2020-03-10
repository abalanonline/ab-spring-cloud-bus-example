package org.springframework.cloud.stream.binder.dummy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedPropertiesBinder;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
public class DummyMessageChannelBinder extends
                                     AbstractMessageChannelBinder<ExtendedConsumerProperties<DummyConsumerProperties>,
                                             ExtendedProducerProperties<DummyProducerProperties>, DummyStreamProvisioner>
        implements ExtendedPropertiesBinder<MessageChannel, DummyConsumerProperties, DummyProducerProperties> {

    public DummyMessageChannelBinder(
//        AmazonSQSAsync amazonSQSAsync, AmazonSNSAsync amazonSNSAsync,
        DummyStreamProvisioner provisioningProvider) {
        super(true, new String[0], provisioningProvider);
    }

    @Override
    protected MessageHandler createProducerMessageHandler(ProducerDestination destination, ExtendedProducerProperties<DummyProducerProperties> producerProperties, MessageChannel errorChannel) throws Exception {
        return DummyMessageHandler.builder()
            .destination(destination).producerProperties(producerProperties).errorChannel(errorChannel)
            .build();
    }

    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ExtendedConsumerProperties<DummyConsumerProperties> properties) throws Exception {
        DummyMessageProducer messageProducer = DummyMessageProducer.builder()
            .destination(destination).group(group).properties(properties)
            .build();
        properties.getExtension().setMessageProducer(messageProducer);
        return messageProducer;
    }

    @Override
    public DummyConsumerProperties getExtendedConsumerProperties(String channelName) {
        return DummyConsumerProperties.builder().mq(provisioningProvider.getMq()).channelName(channelName).build();
    }

    @Override
    public DummyProducerProperties getExtendedProducerProperties(String channelName) {
        return DummyProducerProperties.builder().mq(provisioningProvider.getMq()).channelName(channelName).build();
    }

}
