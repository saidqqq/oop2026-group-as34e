package studenttaskmanager.DATA.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findByCriteria(String field, Object value);
    T save(T entity);
    T update(T entity);
    void delete(ID id);
}