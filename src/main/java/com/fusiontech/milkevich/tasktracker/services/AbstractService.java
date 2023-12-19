package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.dto.AbstractDto;
import com.fusiontech.milkevich.tasktracker.entity.AbstractEntity;
import com.hazelcast.config.Config;
import com.hazelcast.instance.impl.HazelcastInstanceFactory;
import com.hazelcast.map.IMap;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract service.
 *
 * @param <E> - Entity
 * @param <D> - Dto
 * @param <R> - JpaRepository
 */
public abstract class AbstractService<E extends AbstractEntity,
    D extends AbstractDto, R extends JpaRepository<E, Long>> {

  @Autowired
  protected R repository;

  @Autowired
  private Config hazelcastConfig;

  /**
   * Hazelcast cache.
   */
  IMap<Long, D> cache =
      HazelcastInstanceFactory.newHazelcastInstance(hazelcastConfig).getMap(getMapName());

  /**
   * Initializes the cache by populating it with entities from the repository.
   */
  @PostConstruct
  public void init() {
    repository.findAll().forEach(entity -> cache.put(entity.getId(), toDto(entity)));
  }

  /**
   * Get map name for cache.
   *
   * @return - map name
   */
  protected abstract String getMapName();

  /**
   * method Get by id.
   *
   * @param id - id
   * @return - response DTO
   */
  public D getById(Long id) {
    if (cache.containsKey(id)) {
      return cache.get(id);
    }
    return toDto(repository.findById(id).get());
  }

  /**
   * method Get all.
   *
   * @return - response DTO
   */
  public List<D> getAll() {
    if (!cache.isEmpty()) {
      return cache.values().stream().toList();
    }
    repository.findAll().forEach(entity -> cache.put(entity.getId(), toDto(entity)));
    return repository.findAll().stream().map(this::toDto).toList();
  }

  /**
   * method save.
   *
   * @param dto - request DTO
   * @return - response DTO
   */
  @Transactional
  public D save(D dto) {
    dto = toDto(repository.save(toEntity(dto)));
    cache.put(dto.getId(), dto);
    return dto;
  }

  /**
   * method delete.
   *
   * @param id - id
   */
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
    cache.delete(id);
  }

  /**
   * Convert entity to dto.
   *
   * @param entity - entity
   * @return - dto
   */
  public abstract D toDto(E entity);

  /**
   * Convert dto to entity.
   *
   * @param dto - dto
   * @return - entity
   */
  public abstract E toEntity(D dto);
}
