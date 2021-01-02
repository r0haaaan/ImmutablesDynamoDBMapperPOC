package com.github.simy4.poc.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.simy4.poc.model.Entity;
import com.github.simy4.poc.model.Identity;
import com.github.simy4.poc.model.ImmutableEntity;
import com.github.simy4.poc.model.ModifiableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EntityCrudRepository extends IsoCrudRepository<Entity, ModifiableEntity, Identity> {

  @Autowired
  public EntityCrudRepository(DynamoDBMapper dynamoDBMapper) {
    super(dynamoDBMapper);
  }

  @Override
  protected ModifiableEntity toImage(Entity entity) {
    return new ModifiableEntity().from(entity);
  }

  @Override
  protected ImmutableEntity fromImage(ModifiableEntity modifiableEntity) {
    return modifiableEntity.toImmutable();
  }

  @Override
  protected ModifiableEntity fromId(Identity id) {
    var keyObject = new ModifiableEntity();
    keyObject.setPk(id.getPk());
    keyObject.setSk(id.getSk());
    return keyObject;
  }
}
