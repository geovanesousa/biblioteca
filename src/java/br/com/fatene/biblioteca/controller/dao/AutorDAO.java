package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Autor;
import java.util.List;
import javax.persistence.EntityManager;

public class AutorDAO {

    private EntityManager manager;

    public AutorDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Autor consultarPorId(Long id) {
        Autor autor = this.manager.find(Autor.class, id);
        return autor;
    }

    public Autor consultarIgual(String valor) {
        
        String jpql = "SELECT a FROM Autor"
                + " a WHERE a.nome = '"+valor+"'";
        
        List<Autor> lista = this.manager.createQuery(jpql, Autor.class).
                getResultList();
        return lista.get(0);
    }
    
    public List<String> nomesDosAutores(String valor) {
        
        String jpql = "SELECT a.nome FROM Autor"
                + " a WHERE a.nome LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }

}
