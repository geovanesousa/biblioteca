package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.utilitarios.RetiraMascara;
import br.com.fatene.biblioteca.model.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

    private Usuario usuario = new Usuario();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirUsuario(){
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        this.usuario.getPessoa().setCpf(RetiraMascara.campoCPF(
                this.usuario.getPessoa().getCpf()));
        genericoDAO.inserir(this.usuario.getPessoa());
        genericoDAO.inserir(this.usuario);
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage("Inserido com sucesso! Login gerado: "
                +String.valueOf(this.usuario.getPessoa().getMatricula()));
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, fm);
        this.usuario = new Usuario();
        return null;
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", 
                        "As senhas não correspondem."));
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
