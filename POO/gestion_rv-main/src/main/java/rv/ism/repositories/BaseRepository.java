package rv.ism.repositories;

import java.util.List;

public interface BaseRepository<T,ID> {
   void save(T entity);
   void remove(ID id);
   List<T> findAll();
   T findById(ID id);
}
