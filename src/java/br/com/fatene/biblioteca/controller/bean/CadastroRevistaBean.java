package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EditoraDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.RevistaDAO;
import br.com.fatene.biblioteca.model.Autor;
import br.com.fatene.biblioteca.model.ExemplarRevista;
import br.com.fatene.biblioteca.model.Revista;
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
public class CadastroRevistaBean implements Serializable {

    private Revista revista = new Revista();
    private ExemplarRevista exemplarRevista = new ExemplarRevista();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirRevista() {

        //insere primeiramente o Revista
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        EditoraDAO editoraDAO = new EditoraDAO(manager);
        this.revista.setEditora(editoraDAO.consultarIgual(
                this.revista.getEditora().getNome()));
        genericoDAO.inserir(this.revista);

        //insere as informações de estoque
        this.exemplarRevista.setRevista(this.revista);
        genericoDAO.inserir(this.exemplarRevista);

        return null;
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public ExemplarRevista getExemplarRevista() {
        return exemplarRevista;
    }

    public void setExemplarRevista(ExemplarRevista exemplarRevista) {
        this.exemplarRevista = exemplarRevista;
    }

}
