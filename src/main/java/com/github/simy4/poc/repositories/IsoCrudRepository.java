package com.github.simy4.poc.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Optional;
import java.util.function.UnaryOperator;

public abstract class IsoCrudRepository<A, Image, Id> implements CrudRepository<A, Id> {
  private final DynamoDBMapper dynamoDBMapper;

  protected IsoCrudRepository(DynamoDBMapper dynamoDBMapper) {
    this.dynamoDBMapper = dynamoDBMapper;
  }

  protected abstract Image toImage(A a);

  protected abstract A fromImage(Image a);

  protected abstract Image fromId(Id id);

  protected final A modify(A a, UnaryOperator<Image> operator) {
    return fromImage(operator.apply(toImage(a)));
  }

  @Override
  public final A save(A a) {
    return modify(
        a,
        modifiable -> {
          dynamoDBMapper.save(modifiable);
          return modifiable;
        });
  }

  @Override
  public final Optional<A> get(Id id) {
    return Optional.ofNullable(dynamoDBMapper.load(fromId(id))).map(this::fromImage);
  }

  @Override
  public final void delete(A immutable) {
    dynamoDBMapper.delete(toImage(immutable));
  }
}
