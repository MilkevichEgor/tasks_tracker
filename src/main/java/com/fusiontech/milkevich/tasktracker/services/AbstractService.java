package com.fusiontech.milkevich.tasktracker.services;


import com.fusiontech.milkevich.tasktracker.entity.AbstractEntity;
import com.hazelcast.instance.impl.HazelcastInstanceFactory;
import com.hazelcast.map.IMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<E extends AbstractEntity, D, R extends JpaRepository<E, Long>> {

  @Autowired
  protected R repository;

  IMap<Long, D> cache = HazelcastInstanceFactory.newHazelcastInstance("TaskProject").getMap(getMapName()); // <Long, D>

  protected abstract String getMapName();

  public D getById(Long id) {
    if (cache.containsKey(id)) {
      return cache.get(id);
    }
    return toDto(repository.findById(id).get());
  }

  public List<D> getAll() {
    if (!cache.isEmpty()) {
      return cache.values().stream().toList();
    }
    repository.findAll().forEach(entity -> cache.put(entity.getId(), toDto(entity)));
    return repository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public D save(D dto) {
    return toDto(repository.save(toEntity(dto)));
  }

  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public abstract D toDto(E entity);

  public abstract E toEntity(D dto);
}
