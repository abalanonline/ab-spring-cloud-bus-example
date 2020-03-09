package org.springframework.cloud.stream.binder.sqs.provisioning;

//import com.amazonaws.services.sns.AmazonSNSAsync;
//import com.amazonaws.services.sns.model.CreateTopicResult;
//import com.amazonaws.services.sns.util.Topics;
//import com.amazonaws.services.sqs.AmazonSQSAsync;
//import com.amazonaws.services.sqs.model.CreateQueueRequest;
//import com.amazonaws.services.sqs.model.CreateQueueResult;

import lombok.Getter;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.dummy.DummyMQ;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;
import org.springframework.cloud.stream.binder.dummy.DummyConsumerProperties;
import org.springframework.cloud.stream.binder.dummy.DummyProducerProperties;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * The {@link ProvisioningProvider} implementation for Amazon SQS. Provisions both SNS topics and SQS queues.
 *
 * @author Maciej Walkowiak
 */
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
        return new SqsProducerDestination(name, "");
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

        mq.createQueue(queueName);
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

        return DummySqsConsumerDestination.builder().name(queueName).build();
    }
}
