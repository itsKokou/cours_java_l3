package services;

import java.util.ArrayList;

import entities.Unite;
import repositories.ITables;

public class UniteServiceImpl implements UniteService{

   private ITables<Unite>  uniteRepository;

   public UniteServiceImpl(ITables<Unite>  uniteRepository){
    this.uniteRepository=uniteRepository;
   }

    @Override
    public int add(Unite data) {
        return uniteRepository.insert(data);
    }

    @Override
    public ArrayList<Unite> getAll() {
        return uniteRepository.findAll();
    }

    @Override
    public int update(Unite data) {
       return uniteRepository.update(data);
    }

    @Override
    public Unite show(int id) {
        return uniteRepository.findById(id);
    }

    @Override
    public int remove(int id) {
        return uniteRepository.delete(id);
    }

    @Override
    public int[] remove(int[] ids) {
        int[] idsNotDelete=new int[ids.length];
        int nbre=0;

        for (int id = 0; id < ids.length; id++) {
            if (uniteRepository.delete(ids[id])==0) {
                idsNotDelete[nbre++]=ids[id];
            }
        }

        return idsNotDelete;
    }
    
    
}
