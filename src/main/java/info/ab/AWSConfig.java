package info.ab;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import org.springframework.cloud.aws.core.env.stack.StackResourceRegistry;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
  @Bean
  public RegionProvider regionProvider() {
    return () -> RegionUtils.getRegion(Regions.US_EAST_1.getName());
  }
  @Bean
  public AWSCredentialsProvider awsCredentialsProvider() {
    return new AWSCredentialsProvider() {
      @Override
      public AWSCredentials getCredentials() {
        return new BasicAWSCredentials("xxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
      }
      @Override
      public void refresh() {

      }
    };
  }
  @Bean
  public StackResourceRegistry stackResourceRegistry() {
    return new StackResourceRegistry() {
      @Override
      public String getStackName() {
        return null;
      }
      @Override
      public String lookupPhysicalResourceId(String logicalResourceId) {
        return null;
      }
    };
  }

}
