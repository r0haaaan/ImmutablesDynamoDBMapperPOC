package com.github.simy4.poc.repositories;

import java.util.Optional;

public interface CrudRepository<A> {
  A save(A a);

  Optional<A> get(String id);

  void delete(A a);
}
