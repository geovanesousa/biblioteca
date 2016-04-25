package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Editora;
import java.util.List;
import javax.persistence.EntityManager;

public class EditoraDAO {

    private EntityManager manager;

    public EditoraDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Editora consultarPorId(Long id) {
        Editora editora = this.manager.find(Editora.class, id);
        return editora;
    }

    public Editora consultarIgual(String valor) {
        
        String jpql = "SELECT a FROM Editora"
                + " a WHERE a.nome = '"+valor+"'";
        
        List<Editora> lista = this.manager.createQuery(jpql, Editora.class).
                getResultList();
        return lista.get(0);
    }
    
    public List<String> nomesDasEditoras(String valor) {
        
        String jpql = "SELECT a.nome FROM Editora"
                + " a WHERE a.nome LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }

}
