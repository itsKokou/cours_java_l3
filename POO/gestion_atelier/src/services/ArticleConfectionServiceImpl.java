package services;


import java.util.ArrayList;

import entities.ArticleConfection;
import repositories.ITables;

public class ArticleConfectionServiceImpl implements ArticleConfectionService {

    //private TableArticleConfections articleRepository = new TableArticleConfections();
    // couplage faible 
    private ITables<ArticleConfection> articleRepository; 

    //injection de dépendance
    public ArticleConfectionServiceImpl(ITables<ArticleConfection> articleRepository){
        this.articleRepository = articleRepository;
    }

    @Override
    public int add(ArticleConfection data) {
        return articleRepository.insert(data);
    }

    @Override
    public ArrayList<ArticleConfection> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public int update(ArticleConfection data) {
       return articleRepository.update(data);
    }

    @Override
    public ArticleConfection show(int id) {
        return articleRepository.findById(id);
    }

    @Override
    public int remove(int id) {
        return articleRepository.delete(id);
    }

    @Override
    public int[] remove(int[] ids) {
        int[] idsNotDelete=new int[ids.length];
        int nbre=0;

        for (int id = 0; id < ids.length; id++) {
            if (articleRepository.delete(ids[id])==0) {
                idsNotDelete[nbre++]=ids[id];
            }
        }

        return idsNotDelete;
    }
    
}
