package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.utilitarios.RetiraMascara;
import br.com.fatene.biblioteca.model.Solicitante;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class CadastroSolicitanteBean implements Serializable {

    private Solicitante solicitante = new Solicitante();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirSolicitante(){
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        this.solicitante.getPessoa().setCpf(RetiraMascara.campoCPF(
                this.solicitante.getPessoa().getCpf()));
        genericoDAO.inserir(this.solicitante.getPessoa());
        genericoDAO.inserir(this.solicitante);
        return null;
    }
    
    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    
}
