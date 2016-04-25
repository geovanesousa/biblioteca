package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.EmprestimoDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarMonografiaDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
import br.com.fatene.biblioteca.controller.dao.MonografiaDAO;
import br.com.fatene.biblioteca.controller.dao.ReservaDAO;
import br.com.fatene.biblioteca.controller.dao.SolicitanteDAO;
import br.com.fatene.biblioteca.model.ExemplarMonografia;
import br.com.fatene.biblioteca.model.ReservaMonografia;
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
public class ReservaMonogBean {

    private String tituloMonog = "", codMonog = "",
            matricula = "", nomeSolic = "";
    private ReservaMonografia reservaMonog = new ReservaMonografia();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String inserirReserva() {
        if (!(this.matricula.equals("")) && !(this.codMonog.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new EmprestimoDAO(manager).qtdEmprMonogPorSolic(
                    this.codMonog, this.matricula) == 0) {
                ExemplarMonografiaDAO exemplarMonografiaDAO
                        = new ExemplarMonografiaDAO(manager);
                ExemplarMonografia exemplarMonografia
                        = exemplarMonografiaDAO.consultarCod(this.codMonog);
                ReservaDAO reservaDAO = new ReservaDAO(manager);
                if (reservaDAO.qtdReservaMonogPorSolic(this.codMonog, this.matricula) == 0) {
                    if (exemplarMonografia.getQuantidadeEstoque() <= reservaDAO.
                            qtdReservaMonog(this.codMonog)) {
                        GenericoDAO genericoDAO = new GenericoDAO(manager);
                        MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
                        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
                        this.reservaMonog.setSolicitante(
                                solicitanteDAO.consultarPorMatricula(this.matricula));
                        this.reservaMonog.setDataDaReserva(Calendar.getInstance());
                        this.reservaMonog.setMonografia(monografiaDAO.
                                consultarMonografiaPorCodigo(this.codMonog));
                        if (this.reservaMonog.getMonografia().
                                getBloqueado() == false) {
                            this.reservaMonog.setRecebida(false);
                            genericoDAO.inserir(this.reservaMonog);
                        } else {
                            FacesContext fc = FacesContext.getCurrentInstance();
                            FacesMessage fm = new FacesMessage("MONOGRAFIA bloqueada!");
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
                            + " para essa MONOGRAFIA e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage("Existem empréstimos pendentes"
                        + " para essa MONOGRAFIA e SOLICITANTE!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                return null;
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo MATRÍCULA e CÓD. MONOGRAFIA "
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
        try{
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        this.nomeSolic = solicitanteDAO.consultarPorMatricula(this.matricula).
                getPessoa().getNome();
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem solicitantes com "
                    + "essa matrícula!");
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
            this.codMonog = monografiaDAO.consultarCodigoPorNome(this.tituloMonog);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem Monografias com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch (NonUniqueResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de uma Monografia "
                    + "com esse título! Pesquise pelo CÓD. MONOGRAFIA");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeMonogPorCodigo() {
        try{
        EntityManager manager = this.getEntityManager();
        MonografiaDAO monogDAO = new MonografiaDAO(manager);
        this.tituloMonog = monogDAO.consultarNomePorCodigo(this.codMonog);
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem Monografias com "
                    + "esse código!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String getTituloMonog() {
        return tituloMonog;
    }

    public void setTituloMonog(String tituloMonog) {
        this.tituloMonog = tituloMonog;
    }

    public String getCodMonog() {
        return codMonog;
    }

    public void setCodMonog(String codMonog) {
        this.codMonog = codMonog;
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

    public ReservaMonografia getReservaMonog() {
        return reservaMonog;
    }

    public void setReservaMonog(ReservaMonografia reservaMonog) {
        this.reservaMonog = reservaMonog;
    }

}
