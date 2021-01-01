package com.github.simy4.poc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Data
@Value.Immutable
@JsonSerialize(as = ImmutableCreateEntity.class)
@JsonDeserialize(as = ImmutableCreateEntity.class)
public interface CreateEntity {
    String getName();

    default Entity toEntity() {
        return ImmutableEntity.builder()
                .name(getName())
                .build();
    }
}
