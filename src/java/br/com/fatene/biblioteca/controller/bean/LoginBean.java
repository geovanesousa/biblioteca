package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.PrazoMultaDAO;
import br.com.fatene.biblioteca.controller.dao.UsuarioDAO;
import br.com.fatene.biblioteca.model.PrazoMulta;
import br.com.fatene.biblioteca.model.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    private Usuario usuario;
    private String login = "", senha = "";

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String autentica() {
        EntityManager manager = this.getEntityManager();
        try{
            PrazoMulta pm = new PrazoMultaDAO(manager).consultar("aluno", "cd");
        }catch(NoResultException e){
            new PrazoMultaDAO(manager).listarTodos();
        }
        try{
        
        this.usuario = new UsuarioDAO(manager).consultarPorMatricula(this.login);
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        session.setAttribute("usuarioLogado", this.usuario);
        if (this.usuario.getSenha().equals(this.senha)) {
            if(this.usuario.getCategoria().startsWith("at")){
            return "/restrito/bv_ate";
            }else if(this.usuario.getCategoria().startsWith("co")){
            return "/restrito/bv_coo";
            } else if(this.usuario.getCategoria().startsWith("se")){
            return "/restrito/bv_sec";
            }else if(this.usuario.getCategoria().startsWith("ad")){
            return "/restrito/bv_adm";
            }
        } else {
            FacesMessage fm = new FacesMessage("usuário e/ou senha inválidos");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }catch(NoResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("usuário e/ou senha inválidos");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        }
        return null;
    }
    
    public String registraSaida(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        session.removeAttribute("usuarioLogado");
        return "/index";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
