package com.github.simy4.poc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.simy4.poc.model.converters.DynamoDBInstantConverter;
import java.time.Instant;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Data
@Value.Immutable
@Value.Modifiable
@DynamoDBTable(tableName = "${db.entities.table-name}")
@JsonDeserialize(as = ImmutableEntity.class)
public interface Entity extends Identifiable {
  @Override
  @DynamoDBHashKey
  @Value.Default
  default String getPk() {
    return "Entity#";
  }

  String getName();

  @Nullable
  @DynamoDBTypeConverted(converter = DynamoDBInstantConverter.class)
  Instant getUpdated();

  @Value.Default
  @DynamoDBTypeConverted(converter = DynamoDBInstantConverter.class)
  default Instant getCreated() {
    return Instant.now();
  }

  default Entity patch(CreateEntity entity) {
    return ImmutableEntity.builder().from(this).name(entity.getName()).build();
  }
}
