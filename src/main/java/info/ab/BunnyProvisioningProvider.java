package info.ab;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;
import org.springframework.stereotype.Service;

@Service
public class BunnyProvisioningProvider
    implements ProvisioningProvider<ConsumerProperties, ProducerProperties>, ProducerDestination, ConsumerDestination {

  @Getter @Setter
  String name;

  @Override
  public String getNameForPartition(int partition) {
    return this.name;
  }

  @Override
  public ProducerDestination provisionProducerDestination(String name, ProducerProperties properties) throws ProvisioningException {
    setName(name);
    return this;
  }

  @Override
  public ConsumerDestination provisionConsumerDestination(String name, String group, ConsumerProperties properties) throws ProvisioningException {
    setName(name);
    return this;
  }
}
