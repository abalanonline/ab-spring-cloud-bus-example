package org.springframework.cloud.stream.binder.dummy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.stream.provisioning.ProducerDestination;

@Getter
@AllArgsConstructor
public class DummyProducerDestination implements ProducerDestination {
  String name;

  @Override
  public String getNameForPartition(int partition) {
    return name;
  }
}
