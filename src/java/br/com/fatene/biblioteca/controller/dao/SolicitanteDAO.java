package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.Solicitante;
import java.util.List;
import javax.persistence.EntityManager;

public class SolicitanteDAO {

    private EntityManager manager;

    public SolicitanteDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Solicitante consultarPorMatricula(String matricula) {
        
        String jpql = "SELECT a FROM Solicitante"
                + " a WHERE a.pessoa.matricula = '"+matricula+"'";
        
        Solicitante solicitante = this.manager.createQuery(jpql, Solicitante.class).
                getSingleResult();
        return solicitante;
    }
    
    public Solicitante consultarPorNome(String nome) {
        
        PessoaDAO pessoaDAO = new PessoaDAO(manager);
        String matricula = pessoaDAO.consultaMatriculaPorNome(nome);
        Solicitante solicitante = this.consultarPorMatricula(matricula);
        return solicitante;
    }
    
    public List<String> nomesSolicitantes(String valor) {
        
        String jpql = "SELECT a.pessoa.nome FROM Solicitante a "
                + "WHERE a.pessoa.nome LIKE '"+valor+"%'";
        
        List<String> resultados = this.manager.createQuery(jpql, String.class).
                getResultList();
        return resultados;
    }
    
    /*public Long qtdEmprLivro(String codSolicitante){
        String jpql = "SELECT COUNT(a) FROM empr_livro a " +
                   "WHERE a.emprestimo.solicitante.id = '"+codSolicitante+"'";
        Long quantidade = this.manager.createQuery(jpql, Long.class).
                getSingleResult();
        return quantidade;
        
    }
    
    public Long qtdEmprMonog(String codSolicitante){
        String jpql = "SELECT COUNT(a) FROM emp_monog a " +
                   "WHERE a.emprestimo.solicitante.id = '"+codSolicitante+"'";
        Long quantidade = this.manager.createQuery(jpql, Long.class).
                getSingleResult();
        return quantidade;
        
    }
    
    public Long qtdEmprCd(String codSolicitante){
        String jpql = "SELECT COUNT(a) FROM empr_cd a " +
                   "WHERE a.emprestimo.solicitante.id = '"+codSolicitante+"'";
        Long quantidade = this.manager.createQuery(jpql, Long.class).
                getSingleResult();
        return quantidade;
        
    }*/
    
}
