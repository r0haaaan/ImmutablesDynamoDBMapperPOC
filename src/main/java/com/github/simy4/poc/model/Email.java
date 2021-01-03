package com.github.simy4.poc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Data
@Value.Immutable
@Value.Modifiable
@DynamoDBDocument
@DynamoDBTypeConverted(converter = Email.Converter.class)
@JsonDeserialize(as = ImmutableEmail.class)
public interface Email {
  @javax.validation.constraints.Email
  @Value.Parameter
  @Value.Redacted
  String getEmail();

  @Value.Default
  default boolean isVerified() {
    return false;
  }

  @Value.Default
  default boolean isPrimary() {
    return false;
  }

  final class Converter implements DynamoDBTypeConverter<ModifiableEmail, ImmutableEmail> {
    @Override
    public ModifiableEmail convert(ImmutableEmail email) {
      return new ModifiableEmail().from(email);
    }

    @Override
    public ImmutableEmail unconvert(ModifiableEmail email) {
      return email.toImmutable();
    }
  }
}
