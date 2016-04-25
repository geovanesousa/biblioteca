package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.MonografiaDAO;
import br.com.fatene.biblioteca.model.Autor;
import br.com.fatene.biblioteca.model.AutorMonografia;
import br.com.fatene.biblioteca.model.ExemplarMonografia;
import br.com.fatene.biblioteca.model.Monografia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class CadastroMonografiaBean implements Serializable {

    private Monografia monografia = new Monografia();
    private List<Autor> autores = new ArrayList<Autor>();
    private Autor autor = new Autor();
    private AutorMonografia autorMonografia = new AutorMonografia();
    private ExemplarMonografia exemplarMonografia = new ExemplarMonografia();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirMonografia() {
        
        //insere primeiramente a Monografia
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        genericoDAO.inserir(this.monografia);
        MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
        
        //insere o Autor ou Autores no Banco de Dados
        try{
            int i = 0;
            while(true){
                genericoDAO.inserir(this.autores.get(i));
                this.autorMonografia.setAutor(this.autores.get(i));
                this.autorMonografia.setMonografia(this.monografia);
                genericoDAO.inserir(this.autorMonografia);
                this.autorMonografia = new AutorMonografia();
                i++;
            }
        }catch(IndexOutOfBoundsException e){
            
        }
        
        //insere as informações de estoque
        this.exemplarMonografia.setMonografia(this.monografia);
        genericoDAO.inserir(this.exemplarMonografia);
        
        return null;
    }
    
    public String adicionaAutorNaLista(){
        this.autores.add(this.autor);
        this.autor = new Autor();
        return null;
    }

    public Monografia getMonografia() {
        return monografia;
    }

    public void setMonografia(Monografia monografia) {
        this.monografia = monografia;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public AutorMonografia getAutorMonografia() {
        return autorMonografia;
    }

    public void setAutorMonografia(AutorMonografia autorMonografia) {
        this.autorMonografia = autorMonografia;
    }

    public ExemplarMonografia getExemplarMonografia() {
        return exemplarMonografia;
    }

    public void setExemplarMonografia(ExemplarMonografia exemplarMonografia) {
        this.exemplarMonografia = exemplarMonografia;
    }

    
}
