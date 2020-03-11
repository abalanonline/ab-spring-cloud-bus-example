package info.ab;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class BunnyMessageChannelBinder
    extends AbstractMessageChannelBinder<ConsumerProperties, ProducerProperties,
    ProvisioningProvider<ConsumerProperties, ProducerProperties>> {

  final BunnyMQ bunnyMQ;

  @Autowired
  public BunnyMessageChannelBinder(BunnyProvisioningProvider provisioningProvider, BunnyMQ bunnyMQ) {
    super(true, new String[0], provisioningProvider);
    this.bunnyMQ = bunnyMQ;
  }

  @Override
  protected MessageHandler createProducerMessageHandler(ProducerDestination destination, ProducerProperties producerProperties, MessageChannel errorChannel) {
    return bunnyMQ;
  }

  @Override
  protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ConsumerProperties properties) {
    return bunnyMQ;
  }
}
