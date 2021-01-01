package com.github.simy4.poc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
  @JsonIgnore
  @Value.Derived
  @DynamoDBIgnore
  default Identity getId() {
    return ImmutableIdentity.of(getPk(), getSk());
  }

  @Override
  @Value.Default
  default String getPk() {
    return "Entity#";
  }

  String getName();

  @Nullable Instant getUpdated();

  @Value.Default
  default Instant getCreated() {
    return Instant.now();
  }
}
