package com.github.simy4.poc.model.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import java.time.Instant;

public class DynamoDBInstantConverter implements DynamoDBTypeConverter<String, Instant> {
  @Override
  public String convert(Instant instant) {
    return null == instant ? null : instant.toString();
  }

  @Override
  public Instant unconvert(String text) {
    return null == text ? null : Instant.parse(text);
  }
}
