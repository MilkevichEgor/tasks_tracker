package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.dto.AbstractDto;
import com.fusiontech.milkevich.tasktracker.entity.AbstractEntity;
import com.fusiontech.milkevich.tasktracker.services.AbstractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Abstract rest controller.
 *
 * @param <S> - Service
 * @param <R> - Repository
 * @param <E> - Entity
 * @param <D> - Dto
 */
@Log4j2
public abstract class AbstractRestController<S extends AbstractService<E, D, R>,
    R extends JpaRepository<E, Long>, E extends AbstractEntity, D extends AbstractDto> {

  @Autowired
  private S service;

  /**
   * Get by id.
   *
   * @param id - id
   * @return - response DTO
   */
  @Operation(summary = "Get by id", description = "Get by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
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

  /**
   * method Get all.
   *
   * @return - response DTO
   */
  @Operation(summary = "Get all", description = "Get all")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
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

  /**
   * method save.
   *
   * @param request - request DTO
   * @return - response DTO
   */
  @Operation(summary = "Save", description = "Save")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
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

  /**
   * method delete.
   *
   * @param id - id
   * @return - response
   */
  @Operation(summary = "Delete", description = "Delete")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
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
