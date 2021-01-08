package com.github.simy4.poc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.validation.Valid;

import java.util.List;

@Data
@Value.Immutable
@JsonDeserialize(as = ImmutableCreateEntity.class)
public interface CreateEntity {
  String getName();

  @Valid
  Address getAddress();

  @Valid
  List<Email> getEmails();

  default Entity toEntity(String tenant) {
    return ImmutableEntity.builder()
        .tenant(tenant)
        .name(getName())
        .address(getAddress())
        .addAllEmails(getEmails())
        .build();
  }
}
