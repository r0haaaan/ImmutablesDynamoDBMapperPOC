package com.github.simy4.poc;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.github.simy4.poc.model.ModifiableEntity;
import io.awspring.cloud.core.region.RegionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/** Starting point of this application. */
@SpringBootApplication
public class ImmutablesDynamoDbMapperPocApplication {

  public static void main(String[] args) {
    SpringApplication.run(ImmutablesDynamoDbMapperPocApplication.class, args);
  }

  // AWS v1

  @Bean
  public AmazonDynamoDB dynamoDB(
      AWSCredentialsProvider awsCredentialsProvider,
      @Value("${aws.local.endpoint:#{null}}") String localEndpoint,
      RegionProvider regionProvider) {
    var builder = AmazonDynamoDBClientBuilder.standard().withCredentials(awsCredentialsProvider);
    builder =
        null == localEndpoint
            ? builder.withRegion(regionProvider.getRegion().getName())
            : builder.withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(
                    localEndpoint, regionProvider.getRegion().getName()));
    return builder.build();
  }

  @Bean
  public ApplicationRunner dynamoDBInitializer(
      AmazonDynamoDB dynamoDB, DynamoDBMapper dynamoDBMapper) {
    return args -> {
      var request = dynamoDBMapper.generateCreateTableRequest(ModifiableEntity.class);
      TableUtils.createTableIfNotExists(
          dynamoDB, request.withProvisionedThroughput(new ProvisionedThroughput(2L, 2L)));
      TableUtils.waitUntilActive(dynamoDB, request.getTableName());
    };
  }

  @Bean
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB dynamoDB, Environment env) {
    var builder = DynamoDBMapperConfig.builder();
    builder.setTableNameResolver(
        (clazz, config) -> {
          var dynamoDBTable = clazz.getAnnotation(DynamoDBTable.class);
          return env.resolvePlaceholders(dynamoDBTable.tableName());
        });
    return new DynamoDBMapper(dynamoDB, builder.build());
  }
}
