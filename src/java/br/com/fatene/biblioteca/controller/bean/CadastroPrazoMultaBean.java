package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.PrazoMultaDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class CadastroPrazoMultaBean implements Serializable {

    private EntityManager manager;
    

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirPrazoMultaBean() {
        this.manager = this.getEntityManager();
        PrazoMultaDAO pmd = new PrazoMultaDAO(this.manager);
        pmd.listarTodos();
        return null;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
