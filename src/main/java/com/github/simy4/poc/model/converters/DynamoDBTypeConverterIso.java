package com.github.simy4.poc.model.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.function.Function;

public abstract class DynamoDBTypeConverterIso<S, T> implements DynamoDBTypeConverter<S, T> {
  private final Function<? super T, ? extends S> convert;
  private final Function<? super S, ? extends T> unconvert;

  protected DynamoDBTypeConverterIso(
      Function<? super T, ? extends S> convert, Function<? super S, ? extends T> unconvert) {
    this.convert = convert;
    this.unconvert = unconvert;
  }

  @Override
  public S convert(T t) {
    return convert.apply(t);
  }

  @Override
  public T unconvert(S s) {
    return unconvert.apply(s);
  }
}
