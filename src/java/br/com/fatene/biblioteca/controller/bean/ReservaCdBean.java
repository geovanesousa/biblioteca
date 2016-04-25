package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.ExemplarCdDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.CdDAO;
import br.com.fatene.biblioteca.controller.dao.EmprestimoDAO;
import br.com.fatene.biblioteca.controller.dao.ReservaDAO;
import br.com.fatene.biblioteca.controller.dao.SolicitanteDAO;
import br.com.fatene.biblioteca.model.ExemplarCd;
import br.com.fatene.biblioteca.model.ReservaCd;
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
public class ReservaCdBean {

    private String tituloCd = "", codCd = "",
            matricula = "", nomeSolic = "";
    private ReservaCd reservaCd = new ReservaCd();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirReserva() {
        if (!(this.matricula.equals("")) && !(this.codCd.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new EmprestimoDAO(manager).qtdEmprCdPorSolic(
                    this.codCd, this.matricula) == 0) {
                ExemplarCdDAO exemplarCdDAO = new ExemplarCdDAO(manager);
                ExemplarCd exemplarCd = exemplarCdDAO.consultarCod(this.codCd);
                ReservaDAO reservaDAO = new ReservaDAO(manager);
                if (reservaDAO.qtdReservaCdPorSolic(this.codCd, this.matricula) == 0) {
                    if (exemplarCd.getQuantidadeEstoque() <= reservaDAO.
                            qtdReservaCd(this.codCd)) {
                        GenericoDAO genericoDAO = new GenericoDAO(manager);
                        CdDAO cdDAO = new CdDAO(manager);
                        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
                        this.reservaCd.setSolicitante(
                                solicitanteDAO.consultarPorMatricula(this.matricula));
                        this.reservaCd.setDataDaReserva(Calendar.getInstance());
                        this.reservaCd.setCd(cdDAO.
                                consultarCdPorCodigo(this.codCd));
                        if (this.reservaCd.getCd().getBloqueado() == false) {
                            this.reservaCd.setRecebida(false);
                            genericoDAO.inserir(this.reservaCd);
                        }else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("CD bloqueado!");
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
                            + " para esse CD e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage("Existem empréstimos pendentes"
                        + " para esse CD e SOLICITANTE!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                return null;
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo MATRÍCULA e CÓD. CD "
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

    public String codigoCdPorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            CdDAO cdDAO = new CdDAO(manager);
            this.codCd = cdDAO.consultarCodigoPorNome(this.tituloCd);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem CDS com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch (NonUniqueResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um CD "
                    + "com esse título! Pesquise pelo CÓD. CD.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeCdPorCodigo() {
        try {
            EntityManager manager = this.getEntityManager();
            CdDAO cdDAO = new CdDAO(manager);
            this.tituloCd = cdDAO.consultarNomePorCodigo(this.codCd);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem CDs com "
                    + "esse código!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String getTituloCd() {
        return tituloCd;
    }

    public void setTituloCd(String tituloCd) {
        this.tituloCd = tituloCd;
    }

    public String getCodCd() {
        return codCd;
    }

    public void setCodCd(String codCd) {
        this.codCd = codCd;
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

    public ReservaCd getReservaCd() {
        return reservaCd;
    }

    public void setReservaCd(ReservaCd reservaCd) {
        this.reservaCd = reservaCd;
    }

}
