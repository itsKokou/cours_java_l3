package ism.repositories.list;

import ism.entities.Categorie;

public class TableCategories extends Table<Categorie> {

    // private ArrayList<Categorie> tables=new ArrayList<>();

    // @Override
    // public int insert (Categorie categorie){
    //    tables.add(categorie);
    //     return 1;
    // }

    // @Override
    // public int update (Categorie categorie){
    //     int pos=indexOf(categorie.getId());
    //     if(pos!=-1){
    //         tables.set(pos, categorie) ;
    //         return 1;
    //     }
    //     return 0;
    // }

    // @Override
    // public ArrayList<Categorie>findAll (){
    //     return tables;
    // }

    // @Override
    // public Categorie findById (int id){
    //     int pos=indexOf(id);
    //     if(pos!=-1){
    //         return tables.get(pos);
    //     }
    //     return null;
    // }

    // @Override
    // public int delete (int id){
    //     int pos=indexOf(id);
    //     if(pos!=-1){
    //         tables.remove(pos);
    //         return 1;
    //     }
    //     return 0;
    // }

    // @Override
    // public  int indexOf(int id){
    //     int pos=0;
    //     for (Categorie cat : tables) {
    //         if(cat.getId()==id){
    //             return pos;
    //         }
    //         pos++;
    //     }
    //     return -1;
    // }
    
}
