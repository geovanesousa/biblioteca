package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Livro;
import java.util.List;
import javax.persistence.EntityManager;

public class LivroDAO {

    private EntityManager manager;

    public LivroDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Livro consultarLivroPorTitulo(String valor) {
        
        String jpql = "SELECT a FROM Livro"
                + " a WHERE a.titulo = '"+valor+"'";
        
        Livro livro = this.manager.createQuery(jpql, Livro.class).
                getSingleResult();
        return livro;
    }
    
    public Livro consultarLivroPorCodigo(String codigo) {
        
        String jpql = "SELECT a FROM Livro"
                + " a WHERE a.id = '"+codigo+"'";
        
        Livro livro = this.manager.createQuery(jpql, Livro.class).
                getSingleResult();
        return livro;
    }
    
    public String consultarNomePorCodigo(String codigo) {
        
        String jpql = "SELECT a.titulo FROM Livro"
                + " a WHERE a.id = '"+codigo+"'";
        
        String nome = this.manager.createQuery(jpql, String.class).
                getSingleResult();
        return nome;
    }
    
    public String consultarCodigoPorNome(String titulo) {
        
        String jpql = "SELECT a.id FROM Livro"
                + " a WHERE a.titulo = '"+titulo+"'";
        
        String codigo = String.valueOf(this.manager.createQuery(jpql, Long.class).
                getSingleResult());
        return codigo;
    }
    
    public Long ultimoLivro(String isbn) {
        
        String jpql = "SELECT a.id FROM Livro"
                + " a WHERE a.isbn = '"+isbn+"'";
        
        Long id = this.manager.createQuery(jpql, Long.class).
                getSingleResult();
        return id;
        
    }
    
    public List<String> nomesDosLivros(String valor) {
        
        String jpql = "SELECT a.titulo FROM Livro"
                + " a WHERE a.titulo LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }
    

}
