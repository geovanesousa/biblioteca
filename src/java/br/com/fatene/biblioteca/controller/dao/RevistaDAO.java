package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Revista;
import java.util.List;
import javax.persistence.EntityManager;

public class RevistaDAO {

    private EntityManager manager;

    public RevistaDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Revista consultarRevistaPorTitulo(String valor) {
        
        String jpql = "SELECT a FROM Revista"
                + " a WHERE a.titulo = '"+valor+"'";
        
        Revista revista = this.manager.createQuery(jpql, Revista.class).
                getSingleResult();
        return revista;
    }
    
    public Revista consultarRevistaPorCodigo(String codigo) {
        
        String jpql = "SELECT a FROM Revista"
                + " a WHERE a.id = '"+codigo+"'";
        
        Revista revista = this.manager.createQuery(jpql, Revista.class).
                getSingleResult();
        return revista;
    }
    
    public String consultarNomePorCodigo(String codigo) {
        
        String jpql = "SELECT a.titulo FROM Revista"
                + " a WHERE a.id = '"+codigo+"'";
        
        String nome = this.manager.createQuery(jpql, String.class).
                getSingleResult();
        return nome;
    }
    
    public String consultarCodigoPorNome(String titulo) {
        
        String jpql = "SELECT a.id FROM Revista"
                + " a WHERE a.titulo = '"+titulo+"'";
        
      String codigo = String.valueOf(this.manager.createQuery(jpql, Long.class).
                getSingleResult());
        return codigo;
    }
    
    public Long ultimaRevista(String isbn) {
        
        String jpql = "SELECT a.id FROM Revista"
                + " a WHERE a.isbn = '"+isbn+"'";
        
        Long id = this.manager.createQuery(jpql, Long.class).
                getSingleResult();
        return id;
        
    }
    
    public List<String> nomesDasRevistas(String valor) {
        
        String jpql = "SELECT a.titulo FROM Revista"
                + " a WHERE a.titulo LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }
    

}
