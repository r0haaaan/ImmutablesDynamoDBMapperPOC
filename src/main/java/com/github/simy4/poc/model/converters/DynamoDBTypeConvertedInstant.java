package com.github.simy4.poc.model.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Instant;

@DynamoDBTypeConverted(converter = DynamoDBTypeConvertedInstant.Converter.class)
@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface DynamoDBTypeConvertedInstant {
  class Converter implements DynamoDBTypeConverter<String, Instant> {
    @Override
    public String convert(Instant instant) {
      return null == instant ? null : instant.toString();
    }

    @Override
    public Instant unconvert(String text) {
      return null == text ? null : Instant.parse(text);
    }
  }
}
