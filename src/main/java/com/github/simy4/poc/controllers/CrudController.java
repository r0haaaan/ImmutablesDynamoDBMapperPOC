package com.github.simy4.poc.controllers;

import com.github.simy4.poc.model.CreateEntity;
import com.github.simy4.poc.model.Entity;
import com.github.simy4.poc.repositories.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Sample CRUD REST controller. */
@RestController
@RequestMapping("/crud")
public class CrudController {

  private final CrudRepository<Entity> crudRepository;

  @Autowired
  public CrudController(CrudRepository<Entity> crudRepository) {
    this.crudRepository = crudRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Entity> getEntity(@RequestBody CreateEntity entity) {
    return ResponseEntity.ok(crudRepository.save(entity.toEntity()));
  }

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Entity> getEntity(@PathVariable("id") String id) {
    return crudRepository
        .get(id)
        .<ResponseEntity<Entity>>map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
