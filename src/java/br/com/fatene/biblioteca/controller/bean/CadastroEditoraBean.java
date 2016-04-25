package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EditoraDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.utilitarios.RetiraMascara;
import br.com.fatene.biblioteca.model.Editora;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class CadastroEditoraBean implements Serializable {
 
    private Editora editora = new Editora();
    private String nomeEditora;

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    } 

    public String inserirEditora() {
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        this.editora.getEndereco().setCep(
                RetiraMascara.campoCep(this.editora.getEndereco().getCep()));
        genericoDAO.inserir(this.editora.getEndereco());
        this.editora.setCnpj(RetiraMascara.campoCNPJ(this.editora.getCnpj()));
        this.editora.setTelefone(RetiraMascara.campoTelefone(
                this.editora.getTelefone()));
        genericoDAO.inserir(this.editora);
        return null;
    }

    public List<String> listaEditoras(String nomeEditora){
        this.nomeEditora = nomeEditora;
        EntityManager manager = this.getEntityManager();
        EditoraDAO editoraDAO = new EditoraDAO(manager);
        List<String> nomesEditoras = editoraDAO.nomesDasEditoras(nomeEditora);
        return nomesEditoras;
    }
    
    public String consultarEditora(){
        EntityManager manager = this.getEntityManager();
        EditoraDAO editoraDAO = new EditoraDAO(manager);
        Editora edit = editoraDAO.consultarIgual(this.nomeEditora);
        this.editora = edit;
        return null;
    }
    
    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String consultaEditora) {
        this.nomeEditora = consultaEditora;
    }

}
