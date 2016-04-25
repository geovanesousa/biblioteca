package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.model.PrazoMulta;
import java.util.List;
import javax.persistence.EntityManager;

public class PrazoMultaDAO {

    private EntityManager manager;
    
    public PrazoMultaDAO(EntityManager manager) {
        this.manager = manager;
    }
    
    public void listarTodos() {
        
        String jpql = "SELECT a FROM prazo_multa a";
        
        List<PrazoMulta> lista = this.manager.createQuery(jpql, PrazoMulta.class).
                getResultList();
        try{
        PrazoMulta prazoMulta = lista.get(0);
        }catch(IndexOutOfBoundsException e){
            GenericoDAO gd = new GenericoDAO(this.manager);
            PrazoMulta pm = new PrazoMulta();
            
            //Livro
            pm.setCategoria("aluno");
            pm.setTipoMaterial("livro");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(4);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(10);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("funcionario");
            pm.setTipoMaterial("livro");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(4);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(10);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("professor");
            pm.setTipoMaterial("livro");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(5);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(20);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            //CD
            pm.setCategoria("aluno");
            pm.setTipoMaterial("cd");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(2);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(5);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("funcionario");
            pm.setTipoMaterial("cd");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(2);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(5);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("professor");
            pm.setTipoMaterial("cd");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(2);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(5);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            //Monografia
            pm.setCategoria("aluno");
            pm.setTipoMaterial("monografia");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(2);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(5);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("funcionario");
            pm.setTipoMaterial("monografia");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(2);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(5);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("professor");
            pm.setTipoMaterial("monografia");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(2);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(5);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            //Braille
            pm.setCategoria("aluno");
            pm.setTipoMaterial("braille");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(4);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(10);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("funcionario");
            pm.setTipoMaterial("braille");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(4);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(10);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
            pm.setCategoria("professor");
            pm.setTipoMaterial("braille");
            pm.setLimiteRenovacao(3);
            pm.setLimiteTitulos(5);
            pm.setMultaDiaTitulo(1.00);
            pm.setDiasDevolucao(20);
            gd.inserir(pm);
            pm = new PrazoMulta();
            
        }
    }
    
    public PrazoMulta consultar(String categoria,String tipoMaterial) {
        
        String jpql = "SELECT a FROM prazo_multa"
                + " a WHERE a.categoria = '"+categoria+"'"+
                " and a.tipoMaterial = '"+tipoMaterial+"'";
        
        PrazoMulta prazoMulta = this.manager.createQuery(jpql, PrazoMulta.class).
                getSingleResult();
        return prazoMulta;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
    
    

}
