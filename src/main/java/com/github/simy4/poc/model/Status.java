package com.github.simy4.poc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

@DynamoDBTypeConvertedEnum
public enum Status {
  @JsonProperty("active") ACTIVE,
  @JsonProperty("inactive") INACTIVE
}
