package org.springframework.cloud.stream.binder.sqs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedPropertiesBinder;
import org.springframework.cloud.stream.binder.dummy.DummyMessageHandler;
import org.springframework.cloud.stream.binder.dummy.DummyMessageProducer;
import org.springframework.cloud.stream.binder.sqs.provisioning.DummyStreamProvisioner;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.binder.dummy.DummyConsumerProperties;
import org.springframework.cloud.stream.binder.dummy.DummyProducerProperties;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * The Spring Cloud Stream Binder implementation for AWS SQS.
 *
 * @author Maciej Walkowiak
 */
@Slf4j
public class DummyMessageChannelBinder extends
                                     AbstractMessageChannelBinder<ExtendedConsumerProperties<DummyConsumerProperties>,
                                             ExtendedProducerProperties<DummyProducerProperties>, DummyStreamProvisioner>
        implements ExtendedPropertiesBinder<MessageChannel, DummyConsumerProperties, DummyProducerProperties> {

    public DummyMessageChannelBinder(
//        AmazonSQSAsync amazonSQSAsync, AmazonSNSAsync amazonSNSAsync,
        DummyStreamProvisioner provisioningProvider) {
        super(new String[0], provisioningProvider);
    }

    @Override
    protected MessageHandler createProducerMessageHandler(ProducerDestination destination, ExtendedProducerProperties<DummyProducerProperties> producerProperties, MessageChannel errorChannel) throws Exception {
        return DummyMessageHandler.builder()
            .destination(destination).producerProperties(producerProperties).errorChannel(errorChannel)
            .build();
    }

    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ExtendedConsumerProperties<DummyConsumerProperties> properties) throws Exception {
        return DummyMessageProducer.builder()
            .destination(destination).group(group).properties(properties)
            .build();
    }

    @Override
    protected void postProcessOutputChannel(MessageChannel outputChannel, ExtendedProducerProperties<DummyProducerProperties> producerProperties) {
        ((AbstractMessageChannel) outputChannel).addInterceptor(new SnsPayloadConvertingChannelInterceptor());
    }

    @Override
    public DummyConsumerProperties getExtendedConsumerProperties(String channelName) {
        return DummyConsumerProperties.builder().mq(provisioningProvider.getMq()).channelName(channelName).build();
    }

    @Override
    public DummyProducerProperties getExtendedProducerProperties(String channelName) {
        return DummyProducerProperties.builder().mq(provisioningProvider.getMq()).channelName(channelName).build();
    }

    @Override
    public String getDefaultsPrefix() {
        return "";
    }

    @Override
    public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
        return null;
    }

}
