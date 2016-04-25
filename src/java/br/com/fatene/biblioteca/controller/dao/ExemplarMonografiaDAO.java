package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.ExemplarMonografia;
import javax.persistence.EntityManager;

public class ExemplarMonografiaDAO {

    private EntityManager manager;

    public ExemplarMonografiaDAO(EntityManager manager) {
        this.manager = manager;
    }

    public ExemplarMonografia consultarCod(String valor) {
        
        String jpql = "SELECT a FROM ex_monografia a "
                + "WHERE a.monografia.id = '"+valor+"'";
        
        ExemplarMonografia exemplarMonografia = this.manager.createQuery(
                jpql, ExemplarMonografia.class).getSingleResult();
        return exemplarMonografia;
    }


}
