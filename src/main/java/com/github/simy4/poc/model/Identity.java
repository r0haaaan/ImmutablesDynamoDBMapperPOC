package com.github.simy4.poc.model;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Data
@Value.Immutable(builder = false)
public interface Identity {
  @Value.Parameter
  String getPk();

  @Value.Parameter
  @Nullable
  String getSk();
}
