package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EditoraDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.LivroDAO;
import br.com.fatene.biblioteca.model.Autor;
import br.com.fatene.biblioteca.model.AutorLivro;
import br.com.fatene.biblioteca.model.ExemplarLivro;
import br.com.fatene.biblioteca.model.Livro;
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
public class CadastroLivroBean implements Serializable {

    private Livro livro = new Livro();
    private List<Autor> autores = new ArrayList<Autor>();
    private Autor autor = new Autor();
    private AutorLivro autorLivro = new AutorLivro();
    private ExemplarLivro exemplarLivro = new ExemplarLivro();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirLivro() {
        
        //insere primeiramente o Livro
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        EditoraDAO editoraDAO = new EditoraDAO(manager);
        this.livro.setEditora(editoraDAO.consultarIgual(
                this.livro.getEditora().getNome()));
        genericoDAO.inserir(this.livro);
        LivroDAO livroDAO = new LivroDAO(manager);
        
        //insere o Autor ou Autores no Banco de Dados
        try{
            int i = 0;
            while(true){
                genericoDAO.inserir(this.autores.get(i));
                this.autorLivro.setAutor(this.autores.get(i));
                this.autorLivro.setLivro(this.livro);
                genericoDAO.inserir(this.autorLivro);
                this.autorLivro = new AutorLivro();
                i++;
            }
        }catch(IndexOutOfBoundsException e){
            
        }
        
        //insere as informações de estoque
        this.exemplarLivro.setLivro(this.livro);
        genericoDAO.inserir(this.exemplarLivro);
        
        return null;
    }
    
    public String adicionaAutorNaLista(){
        this.autores.add(this.autor);
        this.autor = new Autor();
        return null;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public AutorLivro getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(AutorLivro autorLivro) {
        this.autorLivro = autorLivro;
    }

    public ExemplarLivro getExemplarLivro() {
        return exemplarLivro;
    }

    public void setExemplarLivro(ExemplarLivro exemplarLivro) {
        this.exemplarLivro = exemplarLivro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    

}
