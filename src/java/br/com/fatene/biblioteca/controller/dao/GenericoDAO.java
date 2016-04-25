package br.com.fatene.biblioteca.controller.dao;

import java.util.List;
import javax.persistence.EntityManager;

public class GenericoDAO {

    private EntityManager manager;

    public GenericoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void inserir(Object objeto) {
        this.manager.persist(objeto);
    }

    public void atualizar(Object objeto) {
        this.manager.merge(objeto);
    }

    public void excluir(Object objeto) {
        this.manager.remove(objeto);
    }

    public Object consultarPorId(Class classe, Long id) {
        Object objeto = this.manager.find(classe, id);
        return objeto;
    }

    public List<Object> listarIgual(String valor, String nomeDaClasse, 
            String nomeDoAtributo, Class classe, String dadoDeRetorno) {
        
        String jpql = "SELECT a."+dadoDeRetorno+" FROM "+nomeDaClasse+""
                + " a WHERE a"+nomeDoAtributo+" = "+valor;
        
        List<Object> lista = this.manager.createQuery(jpql, classe).
                getResultList();
        return lista;
    }
    
    public List<Object> listarPorInicio(String valor, String nomeDaClasse, 
            String nomeDoAtributo, Class classe, String dadoDeRetorno) {
        
        String jpql = "SELECT a"+dadoDeRetorno+" FROM "+nomeDaClasse+""
                + " a WHERE a"+nomeDoAtributo+" LIKE "+valor+"%";
        
        List<Object> lista = this.manager.createQuery(jpql, classe).
                getResultList();
        return lista;
    }

}
