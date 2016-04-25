package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Monografia;
import java.util.List;
import javax.persistence.EntityManager;

public class MonografiaDAO {

    private EntityManager manager;

    public MonografiaDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Monografia consultarMonografiaPorTitulo(String valor) {
        
        String jpql = "SELECT a FROM Monografia"
                + " a WHERE a.titulo = '"+valor+"'";
        
    Monografia monografia = this.manager.createQuery(jpql, Monografia.class).
                getSingleResult();
        return monografia;
    }
    
    public Monografia consultarMonografiaPorCodigo(String codigo) {
        
        String jpql = "SELECT a FROM Monografia"
                + " a WHERE a.id = '"+codigo+"'";
        
        Monografia monografia = this.manager.createQuery(jpql, Monografia.class).
                getSingleResult();
        return monografia;
    }
    
    public String consultarNomePorCodigo(String codigo) {
        
        String jpql = "SELECT a.titulo FROM Monografia"
                + " a WHERE a.id = '"+codigo+"'";
        
        String nome = this.manager.createQuery(jpql, String.class).
                getSingleResult();
        return nome;
    }
    
    public String consultarCodigoPorNome(String titulo) {
        
        String jpql = "SELECT a.id FROM Monografia"
                + " a WHERE a.titulo = '"+titulo+"'";
        
        String codigo = String.valueOf(this.manager.createQuery(jpql, Long.class).
                getSingleResult());
        return codigo;
    }
    
    public List<String> nomesDasMonografias(String valor) {
        
        String jpql = "SELECT a.titulo FROM Monografia"
                + " a WHERE a.titulo LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }
    

}
