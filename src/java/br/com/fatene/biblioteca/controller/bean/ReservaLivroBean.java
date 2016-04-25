package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EmprestimoDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarLivroDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.LivroDAO;
import br.com.fatene.biblioteca.controller.dao.ReservaDAO;
import br.com.fatene.biblioteca.controller.dao.SolicitanteDAO;
import br.com.fatene.biblioteca.model.ExemplarLivro;
import br.com.fatene.biblioteca.model.ReservaLivro;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class ReservaLivroBean {

    private String tituloLivro = "", codLivro = "",
            matricula = "", nomeSolic = "";
    private ReservaLivro reservaLivro = new ReservaLivro();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirReserva() {
        if (!(this.matricula.equals("")) && !(this.codLivro.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new EmprestimoDAO(manager).qtdEmprLivroPorSolic(
                    this.codLivro, this.matricula) == 0) {
                ExemplarLivroDAO exemplarLivroDAO = new ExemplarLivroDAO(manager);
                ExemplarLivro exemplarLivro = exemplarLivroDAO.consultarCod(this.codLivro);
                ReservaDAO reservaDAO = new ReservaDAO(manager);
                if (reservaDAO.qtdReservaLivroPorSolic(this.codLivro, this.matricula) == 0) {
                    if (exemplarLivro.getQuantidadeEstoque() <= reservaDAO.
                            qtdReservaLivro(this.codLivro)) {
                        GenericoDAO genericoDAO = new GenericoDAO(manager);
                        LivroDAO livroDAO = new LivroDAO(manager);
                        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
                        this.reservaLivro.setSolicitante(
                                solicitanteDAO.consultarPorMatricula(this.matricula));
                        this.reservaLivro.setDataDaReserva(Calendar.getInstance());
                        this.reservaLivro.setLivro(livroDAO.
                                consultarLivroPorCodigo(this.codLivro));
                        if (this.reservaLivro.getLivro().getBloqueado() == false) {
                            this.reservaLivro.setRecebida(false);
                            genericoDAO.inserir(this.reservaLivro);
                        } else {
                            FacesContext fc = FacesContext.getCurrentInstance();
                            FacesMessage fm = new FacesMessage("Livro bloqueado!");
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                            return null;
                        }

                    } else {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        FacesMessage fm = new FacesMessage("Estoque suficiente! "
                                + "Prefira realizar um empréstimo.");
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                        return null;
                    }
                } else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("Existem reservas pendentes"
                            + " para esse LIVRO e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage("Existem empréstimos pendentes"
                        + " para esse LIVRO e SOLICITANTE!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                return null;
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo MATRÍCULA e CÓD. LIVRO "
                    + "são obrigatórios!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String defineSolicitantePorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
            this.matricula = String.valueOf(solicitanteDAO.consultarPorNome(
                    this.nomeSolic).getPessoa().getMatricula());
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem solicitantes com "
                    + "esse nome!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch (NonUniqueResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um solicitante "
                    + "com esse nome! Pesquise pela MATRÍCULA.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String defineSolicitantePorMatricula() {
        try {
            EntityManager manager = this.getEntityManager();
            SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
            this.nomeSolic = solicitanteDAO.consultarPorMatricula(this.matricula).
                    getPessoa().getNome();
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem solicitantes com "
                    + "essa matrícula!");
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
            this.codLivro = livroDAO.consultarCodigoPorNome(this.tituloLivro);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem Livros com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch (NonUniqueResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um Livro "
                    + "com esse título! Pesquise pela CÓD. LIVRO.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeLivroPorCodigo() {
        try {
            EntityManager manager = this.getEntityManager();
            LivroDAO livroDAO = new LivroDAO(manager);
            this.tituloLivro = livroDAO.consultarNomePorCodigo(this.codLivro);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem livros com "
                    + "esse CÓDIGO!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(String codLivro) {
        this.codLivro = codLivro;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomeSolic() {
        return nomeSolic;
    }

    public void setNomeSolic(String nomeSolic) {
        this.nomeSolic = nomeSolic;
    }

    public ReservaLivro getReservaLivro() {
        return reservaLivro;
    }

    public void setReservaLivro(ReservaLivro reservaLivro) {
        this.reservaLivro = reservaLivro;
    }

}
