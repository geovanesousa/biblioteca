package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Usuario;
import javax.persistence.EntityManager;

public class UsuarioDAO {

    private EntityManager manager;

    public UsuarioDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Usuario consultarPorMatricula(String matricula) {
        
        String jpql = "SELECT a FROM Usuario"
                + " a WHERE a.pessoa.matricula = '"+matricula+"'";
        
        Usuario usuario = this.manager.createQuery(jpql, Usuario.class).
                getSingleResult();
        return usuario;
    }
    

}
