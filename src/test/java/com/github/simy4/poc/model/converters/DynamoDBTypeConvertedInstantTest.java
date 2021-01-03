package com.github.simy4.poc.model.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDBTypeConvertedInstantTest {
  private final DynamoDBTypeConverter<String, Instant> typeConverter =
      new DynamoDBTypeConvertedInstant.Converter();

  @Test
  void shouldConvert() {
    assertEquals("1970-01-01T00:00:00Z", typeConverter.convert(Instant.EPOCH));
  }

  @Test
  void shouldUnconvert() {
    assertEquals(Instant.EPOCH, typeConverter.unconvert("1970-01-01T00:00:00Z"));
  }

  @Test
  void shouldRoundtrip() {
    var instant = Instant.EPOCH;
    assertEquals(instant, typeConverter.unconvert(typeConverter.convert(Instant.EPOCH)));
  }
}
