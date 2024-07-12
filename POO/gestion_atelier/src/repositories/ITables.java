package repositories;

import java.util.ArrayList;


public interface ITables<T> {
   int insert (T data) ;
   int update (T data);
   ArrayList<T>findAll ();
   T findById (int id);
   int delete (int id);
   int indexOf(int id);
}
