package br.com.fatene.biblioteca.controller.dao;

import javax.persistence.EntityManager;

public class PessoaDAO {

    private EntityManager manager;

    public PessoaDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Long consultaMatriculaPorCpf(String cpf) {

        String jpql = "SELECT a.matricula FROM Pessoa a"
                + " WHERE a.cpf = '" + cpf + "'";

        Long matricula = this.manager.createQuery(jpql, Long.class).
                getSingleResult();
        return matricula;

    }
    
    public String consultaMatriculaPorNome(String nome) {

        String jpql = "SELECT a.matricula FROM Pessoa a"
                + " WHERE a.nome = '" +nome+ "'";

        String matricula = String.valueOf(this.manager.createQuery(jpql, Long.class).
                getSingleResult());
        return matricula;

    }
    
}
