package com.github.simy4.poc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Data
@Value.Immutable
@JsonDeserialize(as = ImmutableUpdateEntity.class)
public interface UpdateEntity {
  @Nullable
  String getName();
}
