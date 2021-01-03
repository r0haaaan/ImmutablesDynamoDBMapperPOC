package com.github.simy4.poc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Data
@Value.Immutable
@Value.Modifiable
@DynamoDBDocument
@DynamoDBTypeConverted(converter = Address.Converter.class)
@JsonDeserialize(as = ImmutableAddress.class)
public interface Address {
  String getLine1();

  @Nullable
  String getLine2();

  @Nullable
  String getCity();

  String getCountry();

  final class Converter implements DynamoDBTypeConverter<ModifiableAddress, ImmutableAddress> {
    @Override
    public ModifiableAddress convert(ImmutableAddress address) {
      return new ModifiableAddress().from(address);
    }

    @Override
    public ImmutableAddress unconvert(ModifiableAddress address) {
      return address.toImmutable();
    }
  }
}
