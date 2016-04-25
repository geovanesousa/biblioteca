package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EditoraDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.CdDAO;
import br.com.fatene.biblioteca.model.Autor;
import br.com.fatene.biblioteca.model.AutorCd;
import br.com.fatene.biblioteca.model.ExemplarCd;
import br.com.fatene.biblioteca.model.Cd;
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
public class CadastroCdBean implements Serializable {

    private Cd cd = new Cd();
    private List<Autor> autores = new ArrayList<Autor>();
    private Autor autor = new Autor();
    private AutorCd autorCd = new AutorCd();
    private ExemplarCd exemplarCd = new ExemplarCd();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirCd() {
        //insere primeiramente o Cd
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        EditoraDAO editoraDAO = new EditoraDAO(manager);
        this.cd.setEditora(editoraDAO.consultarIgual(
                this.cd.getEditora().getNome()));
        genericoDAO.inserir(this.cd);
        CdDAO cdDAO = new CdDAO(manager);

        //insere o Autor ou Autores no Banco de Dados
        try {
            int i = 0;
            while (true) {
                genericoDAO.inserir(this.autores.get(i));
                this.autorCd.setAutor(this.autores.get(i));
                this.autorCd.setCd(this.cd);
                genericoDAO.inserir(this.autorCd);
                this.autorCd = new AutorCd();
                i++;
            }
        } catch (IndexOutOfBoundsException e) {

        }

        //insere as informações de estoque
        this.exemplarCd.setCd(this.cd);
        genericoDAO.inserir(this.exemplarCd);

        return null;
    }

    public String adicionaAutorNaLista() {
        this.autores.add(this.autor);
        this.autor = new Autor();
        return null;
    }

    public Cd getCd() {
        return cd;
    }

    public void setCd(Cd cd) {
        this.cd = cd;
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

    public AutorCd getAutorCd() {
        return autorCd;
    }

    public void setAutorCd(AutorCd autorCd) {
        this.autorCd = autorCd;
    }

    public ExemplarCd getExemplarCd() {
        return exemplarCd;
    }

    public void setExemplarCd(ExemplarCd exemplarCd) {
        this.exemplarCd = exemplarCd;
    }

}
