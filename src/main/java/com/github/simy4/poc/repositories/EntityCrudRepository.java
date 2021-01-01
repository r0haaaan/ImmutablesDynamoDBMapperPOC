package com.github.simy4.poc.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.simy4.poc.model.Entity;
import com.github.simy4.poc.model.ImmutableEntity;
import com.github.simy4.poc.model.ModifiableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EntityCrudRepository extends IsoCrudRepository<Entity, ModifiableEntity> {

  @Autowired
  public EntityCrudRepository(DynamoDBMapper dynamoDBMapper) {
    super(dynamoDBMapper, ModifiableEntity.class);
  }

  @Override
  protected ModifiableEntity toImage(Entity entity) {
    var modifiableEntity = new ModifiableEntity();
    modifiableEntity.from(entity);
    return modifiableEntity;
  }

  @Override
  protected ImmutableEntity fromImage(ModifiableEntity modifiableEntity) {
    return modifiableEntity.toImmutable();
  }
}
