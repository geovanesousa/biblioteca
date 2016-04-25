package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Dicionario;
import java.util.List;
import javax.persistence.EntityManager;

public class DicionarioDAO {

    private EntityManager manager;

    public DicionarioDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Dicionario consultarDicionarioPorTitulo(String valor) {
        
        String jpql = "SELECT a FROM Dicionario"
                + " a WHERE a.titulo = '"+valor+"'";
        
       Dicionario dicionario = this.manager.createQuery(jpql, Dicionario.class).
                getSingleResult();
        return dicionario;
    }
    
    public Dicionario consultarDicionarioPorCodigo(String codigo) {
        
        String jpql = "SELECT a FROM Dicionario"
                + " a WHERE a.id = '"+codigo+"'";
        
        Dicionario dicionario = this.manager.createQuery(jpql, Dicionario.class).
                getSingleResult();
        return dicionario;
    }
    
    public String consultarNomePorCodigo(String codigo) {
        
        String jpql = "SELECT a.titulo FROM Dicionario"
                + " a WHERE a.id = '"+codigo+"'";
        
        String nome = this.manager.createQuery(jpql, String.class).
                getSingleResult();
        return nome;
    }
    
    public String consultarCodigoPorNome(String titulo) {
        
        String jpql = "SELECT a.id FROM Dicionario"
                + " a WHERE a.titulo = '"+titulo+"'";
        
        String codigo = String.valueOf(this.manager.createQuery(jpql, Long.class).
                getSingleResult());
        return codigo;
    }
    
    public List<String> nomesDosDicionario(String valor) {
        
        String jpql = "SELECT a.titulo FROM Dicionario"
                + " a WHERE a.titulo LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }
    

}
