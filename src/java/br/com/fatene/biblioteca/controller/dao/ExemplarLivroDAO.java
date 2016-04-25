package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.ExemplarLivro;
import javax.persistence.EntityManager;

public class ExemplarLivroDAO {

    private EntityManager manager;

    public ExemplarLivroDAO(EntityManager manager) {
        this.manager = manager;
    }

    public ExemplarLivro consultarCod(String valor) {
        
        String jpql = "SELECT a FROM exemp_livro a "
                + "WHERE a.livro.id = '"+valor+"'";
        
        ExemplarLivro exemplarLivro = this.manager.createQuery(
                jpql, ExemplarLivro.class).getSingleResult();
        return exemplarLivro;
    }


}
