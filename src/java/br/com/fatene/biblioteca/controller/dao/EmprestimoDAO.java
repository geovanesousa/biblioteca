package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.EmprestimoCd;
import br.com.fatene.biblioteca.model.EmprestimoLivro;
import br.com.fatene.biblioteca.model.EmprestimoMonografia;
import javax.persistence.EntityManager;

public class EmprestimoDAO {

    private EntityManager manager;

    public EmprestimoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public EmprestimoLivro ConsultEmprLivroMatriculaCodLivro(
            String matricula, 
            String codLivro) {
        
        String jpql = "SELECT a FROM empr_livro a"
        + " WHERE a.emprestimo.solicitante.pessoa.matricula = '"+matricula+"'"
        +" AND a.exemplarLivro.livro.id = '"+codLivro+"'"
        +" AND a.pendente = 'true'";
        
        EmprestimoLivro emprestimoLivro = 
                this.manager.createQuery(jpql, EmprestimoLivro.class).
                getSingleResult();
        return emprestimoLivro;
    }
    
    public Integer qtdEmprLivroPorSolic(String codLivro, String matricula) {
        
        String jpql = "SELECT COUNT(a) FROM empr_livro a"
        +" WHERE a.emprestimo.solicitante.pessoa.matricula = '"+matricula+"'"
        +" AND a.exemplarLivro.livro.id = '"+codLivro+"'"
        +" AND a.pendente = 'true'";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
    
    public Integer qtdEmprCdPorSolic(String codCd, String matricula) {
        
        String jpql = "SELECT COUNT(a) FROM empr_cd a"
        +" WHERE a.emprestimo.solicitante.pessoa.matricula = '"+matricula+"'"
        +" AND a.exemplarCd.cd.id = '"+codCd+"'"
        +" AND a.pendente = 'true'";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
    
    public Integer qtdEmprMonogPorSolic(String codMonog, String matricula) {
        
        String jpql = "SELECT COUNT(a) FROM emp_monog a"
        +" WHERE a.emprestimo.solicitante.pessoa.matricula = '"+matricula+"'"
        +" AND a.exemplarMonografia.monografia.id = '"+codMonog+"'"
        +" AND a.pendente = 'true'";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
    
    //CD
    public EmprestimoCd ConsultEmprCdMatriculaCodCd(
            String matricula, 
            String codCd) {
        
        String jpql = "SELECT a FROM empr_cd a "
        + " WHERE a.emprestimo.solicitante.pessoa.matricula = '"+matricula+"'"
        +" AND a.exemplarCd.cd.id = '"+codCd+"'"
        +" AND a.pendente = 'true'";
        
        EmprestimoCd emprestimoCd = 
                this.manager.createQuery(jpql, EmprestimoCd.class).
                getSingleResult();
        return emprestimoCd;
    }
    
    //Monografia
    public EmprestimoMonografia ConsultEmprMonogMatriculaCodMonog(
            String matricula, 
            String codMonog) {
        
        String jpql = "SELECT a FROM emp_monog a "
        + " WHERE a.emprestimo.solicitante.pessoa.matricula = '"+matricula+"'"
        +" AND a.exemplarMonografia.monografia.id = '"+codMonog+"'"
        +" AND a.pendente = 'true'";
        
        EmprestimoMonografia emprestimoMonografia = 
                this.manager.createQuery(jpql, EmprestimoMonografia.class).
                getSingleResult();
        return emprestimoMonografia;
    }
   
}
