package org.springframework.cloud.stream.binder.dummy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(Binder.class)
public class DummyBinderConfiguration {

    @Bean
    public DummyStreamProvisioner provisioningProvider() {
        return new DummyStreamProvisioner();
    }

    @Bean
    public DummyMessageChannelBinder dummyMessageChannelBinder(DummyStreamProvisioner provisioningProvider) {
        return new DummyMessageChannelBinder(provisioningProvider);
    }
}
