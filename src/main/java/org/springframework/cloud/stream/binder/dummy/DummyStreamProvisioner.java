package org.springframework.cloud.stream.binder.dummy;

import lombok.Getter;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

import java.util.Random;
import java.util.stream.Collectors;

public class DummyStreamProvisioner implements
                                  ProvisioningProvider<ExtendedConsumerProperties<DummyConsumerProperties>, ExtendedProducerProperties<DummyProducerProperties>> {

    @Getter
    private DummyMQ mq = new DummyMQ();

//    public SqsStreamProvisioner(AmazonSQSAsync amazonSQSAsync, AmazonSNSAsync amazonSNSAsync) {
//        this.amazonSQSAsync = amazonSQSAsync;
//        this.amazonSNSAsync = amazonSNSAsync;
//    }

    @Override
    public ProducerDestination provisionProducerDestination(String name,
                                                            ExtendedProducerProperties<DummyProducerProperties> properties) throws ProvisioningException {

//        CreateTopicResult createTopicResult = amazonSNSAsync.createTopic(name);
//        return new SqsProducerDestination(name, createTopicResult.getTopicArn());
        return new DummyProducerDestination(name);
    }

    @Override
    public ConsumerDestination provisionConsumerDestination(String name, String group,
                                                            ExtendedConsumerProperties<DummyConsumerProperties> properties) throws ProvisioningException {

        String topicName = name;
        String queueName = properties.isPartitioned() ? group + "-" + properties.getInstanceIndex() : group;

        if (group == null || group.isEmpty()) { // anonymous queue
            queueName = name + "_anonymous_" + new Random().ints(22, 0, 62)
                .mapToObj(x -> String.valueOf(x < 36 ? Character.forDigit(x, 36) : (char)('A' - 36 + x)))
                .collect(Collectors.joining());
        }

        mq.createQueue(queueName, properties.getExtension());
        mq.createTopic(topicName);
        mq.subscribe(topicName, queueName);
//        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName)
//                .withAttributes(properties.getExtension().getQueue() != null ? properties.getExtension().getQueue().toQueueAttributes() : Collections.emptyMap());
//        CreateQueueResult createQueueResult = amazonSQSAsync.createQueue(createQueueRequest);
//
//        CreateTopicResult createTopicResult = amazonSNSAsync.createTopic(name);
//        String subscriptionArn = Topics.subscribeQueue(amazonSNSAsync,
//                                                       amazonSQSAsync,
//                                                       createTopicResult.getTopicArn(),
//                                                       createQueueResult.getQueueUrl());
//
//        if (properties.isPartitioned()) {
//            amazonSNSAsync.setSubscriptionAttributes(subscriptionArn,
//                                                     "FilterPolicy",
//                                                     "{\"" + BinderHeaders.PARTITION_HEADER + "\": [" + properties.getInstanceIndex() + "]}");
//        }

        return DummyConsumerDestination.builder().name(queueName).build();
    }
}
