package services;


import java.util.ArrayList;

import entities.Categorie;
import repositories.ITables;


public class CategorieServiceImpl implements CategorieService{

   private ITables<Categorie> categorieRepository;

   public CategorieServiceImpl(ITables<Categorie> categorieRepository){
    this.categorieRepository = categorieRepository;
   }
    

    @Override
    public int add(Categorie data) {
        return categorieRepository.insert(data);
    }

    @Override
    public ArrayList<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    @Override
    public int update(Categorie data) {
       return categorieRepository.update(data);
    }

    @Override
    public Categorie show(int id) {
        return categorieRepository.findById(id);
    }

    @Override
    public int remove(int id) {
        return categorieRepository.delete(id);
    }

    @Override
    public int[] remove(int[] ids) {
        int[] idsNotDelete=new int[ids.length];
        int nbre=0;

        for (int id = 0; id < ids.length; id++) {
            if (categorieRepository.delete(ids[id])==0) {
                idsNotDelete[nbre++]=ids[id];
            }
        }

        return idsNotDelete;
    }
    
}
