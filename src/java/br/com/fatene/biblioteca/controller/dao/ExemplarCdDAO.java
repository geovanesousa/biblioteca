package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.ExemplarCd;
import javax.persistence.EntityManager;

public class ExemplarCdDAO {

    private EntityManager manager;

    public ExemplarCdDAO(EntityManager manager) {
        this.manager = manager;
    }

    public ExemplarCd consultarCod(String valor) {
        
        String jpql = "SELECT a FROM exemp_cd a "
                + "WHERE a.cd.id = '"+valor+"'";
        
        ExemplarCd exemplarCd = this.manager.createQuery(
                jpql, ExemplarCd.class).getSingleResult();
        return exemplarCd;
    }


}
