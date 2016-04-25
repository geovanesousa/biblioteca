package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Cd;
import java.util.List;
import javax.persistence.EntityManager;

public class CdDAO {

    private EntityManager manager;

    public CdDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Cd consultarCdPorTitulo(String valor) {
        
        String jpql = "SELECT a FROM Cd"
                + " a WHERE a.titulo = '"+valor+"'";
        
        Cd cd = this.manager.createQuery(jpql, Cd.class).
                getSingleResult();
        return cd;
    }
    
    public Cd consultarCdPorCodigo(String codigo) {
        
        String jpql = "SELECT a FROM Cd"
                + " a WHERE a.id = '"+codigo+"'";
        
        Cd cd = this.manager.createQuery(jpql, Cd.class).
                getSingleResult();
        return cd;
    }
    
    public String consultarNomePorCodigo(String codigo) {
        
        String jpql = "SELECT a.titulo FROM Cd"
                + " a WHERE a.id = '"+codigo+"'";
        
        String nome = this.manager.createQuery(jpql, String.class).
                getSingleResult();
        return nome;
    }
    
    public String consultarCodigoPorNome(String titulo) {
        
        String jpql = "SELECT a.id FROM Cd"
                + " a WHERE a.titulo = '"+titulo+"'";
        
        String codigo = String.valueOf(this.manager.createQuery(jpql, Long.class).
                getSingleResult());
        return codigo;
    }
    
    public List<String> nomesDosCds(String valor) {
        
        String jpql = "SELECT a.titulo FROM Cd"
                + " a WHERE a.titulo LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }
    

}
