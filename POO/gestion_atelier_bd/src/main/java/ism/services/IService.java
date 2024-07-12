package ism.services;

import java.util.ArrayList;


public interface IService<ENTITE> {
      int  add(ENTITE data);
      ArrayList<ENTITE> getAll();
      int update(ENTITE data);
      ENTITE show(int id);
      int remove(int id);
      int[] remove(int[] ids);
}
