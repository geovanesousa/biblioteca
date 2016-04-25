package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.CdDAO;
import br.com.fatene.biblioteca.controller.dao.EmprestimoDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarCdDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarLivroDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarMonografiaDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.LivroDAO;
import br.com.fatene.biblioteca.controller.dao.MonografiaDAO;
import br.com.fatene.biblioteca.controller.dao.PrazoMultaDAO;
import br.com.fatene.biblioteca.controller.dao.ReservaDAO;
import br.com.fatene.biblioteca.controller.dao.SolicitanteDAO;
import br.com.fatene.biblioteca.controller.dao.UsuarioDAO;
import br.com.fatene.biblioteca.model.Cd;
import br.com.fatene.biblioteca.model.Emprestimo;
import br.com.fatene.biblioteca.model.EmprestimoCd;
import br.com.fatene.biblioteca.model.EmprestimoLivro;
import br.com.fatene.biblioteca.model.EmprestimoMonografia;
import br.com.fatene.biblioteca.model.ExemplarCd;
import br.com.fatene.biblioteca.model.ExemplarLivro;
import br.com.fatene.biblioteca.model.ExemplarMonografia;
import br.com.fatene.biblioteca.model.Livro;
import br.com.fatene.biblioteca.model.Monografia;
import br.com.fatene.biblioteca.model.PrazoMulta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class EstoqueBean implements Serializable {

    private Emprestimo emprestimo = new Emprestimo();

    private String nomeSolicitante = "", matricula = "";
    private String tituloCd = "", codigoCd = "";
    private String tituloMonog = "", codigoMonog = "";
    private String tituloLivro = "", codigoLivro = "";
    private String estoqueCd = "", estoqueMonog = "", estoqueLivro = "";
    
    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String estoqCd(){
        if(!(this.codigoCd.equals(""))){
        EntityManager manager = this.getEntityManager();
        ExemplarCd cd = new ExemplarCd();
                cd = new ExemplarCdDAO(manager).
                consultarCod(this.codigoCd);
        this.estoqueCd = String.valueOf(cd.getQuantidadeEstoque());
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
    FacesMessage fm = new FacesMessage("Campo CÓD. CD obrigatório!");
    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
    fc.addMessage(null, fm);
    return null;
        }
        return null;
    }
    public String estoqLivro(){
        if(!(this.codigoLivro.equals(""))){
        EntityManager manager = this.getEntityManager();
        ExemplarLivro livro = new ExemplarLivro();
        livro = new ExemplarLivroDAO(manager).
                consultarCod(this.codigoLivro);
        this.estoqueLivro = String.valueOf(livro.getQuantidadeEstoque());
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
    FacesMessage fm = new FacesMessage("Campo CÓD. LIVRO obrigatório!");
    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
    fc.addMessage(null, fm);
    return null;
        }
        return null;
    }
    public String estoqMonog(){
        if(!(this.codigoMonog.equals(""))){
        EntityManager manager = this.getEntityManager();
        ExemplarMonografia monog = new ExemplarMonografia();
        monog = new ExemplarMonografiaDAO(manager).
                consultarCod(this.codigoMonog);
        this.estoqueMonog = String.valueOf(monog.getQuantidadeEstoque());
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
    FacesMessage fm = new FacesMessage("Campo CÓD. MONOGRAFIA obrigatório!");
    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
    fc.addMessage(null, fm);
    return null;
        }
        return null;
    }
    
    public String defineSolicitantePorMatricula() {
        try{
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        this.emprestimo.setSolicitante(
                solicitanteDAO.consultarPorMatricula(this.matricula));
        this.nomeSolicitante = this.emprestimo.getSolicitante().
                getPessoa().getNome();
        }catch(NoResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem solicitantes com "
                    + "essa matricula!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String codigoLivroPorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            LivroDAO livroDAO = new LivroDAO(manager);
            this.codigoLivro = livroDAO.consultarCodigoPorNome(this.tituloLivro);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem livros com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch(NonUniqueResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um livro "
                    + "com esse título! Pesquise pela CÓDIGO.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String codigoMonogPorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
            this.codigoMonog = monografiaDAO.consultarCodigoPorNome(this.tituloMonog);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem monografias com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch(NonUniqueResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de uma monografia "
                    + "com esse título! Pesquise pelo CÓDIGO.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String codigoCdPorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            CdDAO cdDAO = new CdDAO(manager);
            this.codigoCd = cdDAO.consultarCodigoPorNome(this.tituloCd);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem CDs com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch(NonUniqueResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um CD "
                    + "com esse título! Pesquise pelo CÓDIGO.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeLivroPorCodigo() {
        try{
        EntityManager manager = this.getEntityManager();
        LivroDAO livroDAO = new LivroDAO(manager);
        this.tituloLivro = livroDAO.consultarNomePorCodigo(this.codigoLivro);
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem livros com "
                    + "esse CÓDIGO!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeCdPorCodigo(){
        try{
        EntityManager manager = this.getEntityManager();
        CdDAO cdDAO = new CdDAO(manager);
        this.tituloCd = cdDAO.consultarNomePorCodigo(this.codigoCd);
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem CDs com "
                    + "esse código!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeMonogPorCodigo() {
        try{
        EntityManager manager = this.getEntityManager();
        MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
        this.tituloMonog = monografiaDAO.consultarNomePorCodigo(this.codigoMonog);
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem monografias com "
                    + "esse código!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public List<String> listaLivros(String tituloLivro) {
        this.tituloLivro = tituloLivro;
        EntityManager manager = this.getEntityManager();
        LivroDAO livroDAO = new LivroDAO(manager);
        List<String> nomesLivros
                = livroDAO.nomesDosLivros(tituloLivro);
        return nomesLivros;
    }

    public List<String> listaCds(String tituloCd) {
        this.tituloCd = tituloCd;
        EntityManager manager = this.getEntityManager();
        CdDAO cdDAO = new CdDAO(manager);
        List<String> nomesCds
                = cdDAO.nomesDosCds(tituloCd);
        return nomesCds;
    }

    public List<String> listaMonog(String tituloMonog) {
        this.tituloMonog = tituloMonog;
        EntityManager manager = this.getEntityManager();
        MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
        List<String> nomesMonografias
                = monografiaDAO.nomesDasMonografias(tituloMonog);
        return nomesMonografias;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTituloCd() {
        return tituloCd;
    }

    public void setTituloCd(String tituloCd) {
        this.tituloCd = tituloCd;
    }

    public String getCodigoCd() {
        return codigoCd;
    }

    public void setCodigoCd(String codigoCd) {
        this.codigoCd = codigoCd;
    }

    public String getTituloMonog() {
        return tituloMonog;
    }

    public void setTituloMonog(String tituloMonog) {
        this.tituloMonog = tituloMonog;
    }

    public String getCodigoMonog() {
        return codigoMonog;
    }

    public void setCodigoMonog(String codigoMonog) {
        this.codigoMonog = codigoMonog;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(String codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public String getEstoqueCd() {
        return estoqueCd;
    }

    public void setEstoqueCd(String estoqueCd) {
        this.estoqueCd = estoqueCd;
    }

    public String getEstoqueMonog() {
        return estoqueMonog;
    }

    public void setEstoqueMonog(String estoqueMonog) {
        this.estoqueMonog = estoqueMonog;
    }

    public String getEstoqueLivro() {
        return estoqueLivro;
    }

    public void setEstoqueLivro(String estoqueLivro) {
        this.estoqueLivro = estoqueLivro;
    }

    
    
}
