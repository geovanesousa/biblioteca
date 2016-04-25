package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EditoraDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.DicionarioDAO;
import br.com.fatene.biblioteca.model.Autor;
import br.com.fatene.biblioteca.model.AutorDicionario;
import br.com.fatene.biblioteca.model.ExemplarDicionario;
import br.com.fatene.biblioteca.model.Dicionario;
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
public class CadastroDicionarioBean implements Serializable {

    private Dicionario dicionario = new Dicionario();
    private List<Autor> autores = new ArrayList<Autor>();
    private Autor autor = new Autor();
    private AutorDicionario autorDicionario = new AutorDicionario();
    private ExemplarDicionario exemplarDicionario = new ExemplarDicionario();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirDicionario() {
        
        //insere primeiramente o Dicionario
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        EditoraDAO editoraDAO = new EditoraDAO(manager);
        this.dicionario.setEditora(editoraDAO.consultarIgual(
                this.dicionario.getEditora().getNome()));
        genericoDAO.inserir(this.dicionario);
        DicionarioDAO dicionarioDAO = new DicionarioDAO(manager);
        
        //insere o Autor ou Autores no Banco de Dados
        try{
            int i = 0;
            while(true){
                genericoDAO.inserir(this.autores.get(i));
                this.autorDicionario.setAutor(this.autores.get(i));
                this.autorDicionario.setDicionario(this.dicionario);
                genericoDAO.inserir(this.autorDicionario);
                this.autorDicionario = new AutorDicionario();
                i++;
            }
        }catch(IndexOutOfBoundsException e){
            
        }
        
        //insere as informações de estoque
        this.exemplarDicionario.setDicionario(this.dicionario);
        genericoDAO.inserir(this.exemplarDicionario);
        
        return null;
    }
    
    public String adicionaAutorNaLista(){
        this.autores.add(this.autor);
        this.autor = new Autor();
        return null;
    }

    public Dicionario getDicionario() {
        return dicionario;
    }

    public void setDicionario(Dicionario dicionario) {
        this.dicionario = dicionario;
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

    public AutorDicionario getAutorDicionario() {
        return autorDicionario;
    }

    public void setAutorDicionario(AutorDicionario autorDicionario) {
        this.autorDicionario = autorDicionario;
    }

    public ExemplarDicionario getExemplarDicionario() {
        return exemplarDicionario;
    }

    public void setExemplarDicionario(ExemplarDicionario exemplarDicionario) {
        this.exemplarDicionario = exemplarDicionario;
    }

    

}
