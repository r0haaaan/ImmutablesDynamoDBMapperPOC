package com.github.simy4.poc.model;

import org.immutables.value.Value;

@Data
@Value.Immutable(builder = false)
public interface Identity {
  @Value.Parameter
  String getPk();

  @Value.Parameter
  String getSk();
}
