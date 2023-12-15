package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.entity.AbstractEntity;
import com.fusiontech.milkevich.tasktracker.services.AbstractService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
public abstract class AbstractRestController<S extends AbstractService<E, D, R>, R extends JpaRepository<E, Long>, E extends AbstractEntity, D> {

  @Autowired
  private S service;

  @GetMapping("/{id}")
  public ResponseEntity<D> getById(@PathVariable Long id) {
    try {
      D response = service.getById(id);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<D>> getAll() {
    try {
      List<D> response = service.getAll();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping
  public ResponseEntity<D> save(@RequestBody D request) {
    try {
      D response = service.save(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    try {
      service.delete(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
