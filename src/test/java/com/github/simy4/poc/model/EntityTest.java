package com.github.simy4.poc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityTest {
  private final Entity entity =
      ImmutableEntity.builder()
          .tenant("tenant")
          .name("name")
          .address(ImmutableAddress.builder().line1("123 ABC st.").country("US").build())
          .status(Status.ACTIVE)
          .version(1L)
          .build();
  private final Entity newerEntity =
      ImmutableEntity.copyOf(entity).withVersion(2L).withName("another-name");

  @Test
  void immutableEntitiesShouldBeEqual() {
    assertEquals(entity, newerEntity);
  }

  @Test
  void modifiableEntitiesShouldBeEqual() {
    assertEquals(new ModifiableEntity().from(entity), new ModifiableEntity().from(newerEntity));
  }
}
