package repositories.list;

import java.util.ArrayList;

import entities.AbstractEntity;
import repositories.ITables;

public class Table<T extends AbstractEntity> implements ITables<T> {

    protected ArrayList<T> tables=new ArrayList<>();

    @Override
    public int insert (T data){
       tables.add(data);
        return 1;
    }

    @Override
    public int update (T data){
        int pos=indexOf(data.getId());
        if(pos!=-1){
            tables.set(pos, data) ;
            return 1;
        }
        return 0;
    }

    @Override
    public ArrayList<T>findAll (){
        return tables;
    }

    @Override
    public T findById (int id){
        int pos=indexOf(id);
        if(pos!=-1){
            return tables.get(pos);
        }
        return null;
    }

    @Override
    public int delete (int id){
        int pos=indexOf(id);
        if(pos!=-1){
            tables.remove(pos);
            return 1;
        }
        return 0;
    }

    @Override
    public int indexOf(int id){
        int pos=0;
        for (T data : tables) {
            if(data.getId()==id){
                return pos;
            }
            pos++;
        }
        return -1;
    }
    
}
