package com.github.simy4.poc.controllers;

import com.github.simy4.poc.model.CreateEntity;
import com.github.simy4.poc.model.Entity;
import com.github.simy4.poc.model.Identity;
import com.github.simy4.poc.model.UpdateEntity;
import com.github.simy4.poc.repositories.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

/** Sample CRUD REST controller. */
@RestController
@RequestMapping("/crud")
public class CrudController {

  private final CrudRepository<Entity, Identity> crudRepository;

  @Autowired
  public CrudController(CrudRepository<Entity, Identity> crudRepository) {
    this.crudRepository = crudRepository;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Entity> createEntity(@Valid @RequestBody CreateEntity entity) {
    var created = crudRepository.save(entity.toEntity());
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId().getSk())
                .toUri())
        .body(created);
  }

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Entity> getEntity(@PathVariable("id") String id) {
    return crudRepository
        .get(Entity.id(id))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PatchMapping(
      path = "{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Entity> updateEntity(
      @PathVariable("id") String id, @Valid @RequestBody UpdateEntity entity) {
    return crudRepository
        .get(Entity.id(id))
        .map(e -> crudRepository.save(entity.patch(e)))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping(path = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEntity(@PathVariable("id") String id) {
    crudRepository.get(Entity.id(id)).ifPresent(crudRepository::delete);
  }
}
