package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.CdDAO;
import br.com.fatene.biblioteca.controller.dao.LivroDAO;
import br.com.fatene.biblioteca.controller.dao.MonografiaDAO;
import br.com.fatene.biblioteca.controller.dao.ReservaDAO;
import br.com.fatene.biblioteca.model.ReservaCd;
import br.com.fatene.biblioteca.model.ReservaLivro;
import br.com.fatene.biblioteca.model.ReservaMonografia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class FilaReservaBean implements Serializable{

    private String tituloLivro = "", codLivro = "",
            tituloCd = "", codCd = "",
            tituloMonog = "", codMonog = "";
    private List<ReservaCd> reservasCd = new ArrayList<ReservaCd>();
    private List<ReservaLivro> reservasLivro= new ArrayList<ReservaLivro>();
    private List<ReservaMonografia> reservasMonog = 
            new ArrayList<ReservaMonografia>();

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    public String listaReservaLivro() {
        if (!(this.codLivro.equals(""))) {
                EntityManager manager = this.getEntityManager();
                ReservaDAO reservaDAO = new ReservaDAO(manager);
                try{
                this.reservasLivro = 
                        reservaDAO.listaReservasLivro(this.codLivro);
                this.reservasLivro.get(0);
                }catch(IndexOutOfBoundsException e){
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM reservas "
                            + "para esse Livro!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo CÓD. LIVRO "
                    + "é obrigatório!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
            
        return null;
    }
    
    public String listaReservaCd() {
        if (!(this.codCd.equals(""))) {
                EntityManager manager = this.getEntityManager();
                ReservaDAO reservaDAO = new ReservaDAO(manager);
                try{
                this.reservasCd = 
                        reservaDAO.listaReservasCd(this.codCd);
                this.reservasCd.get(0);
                }catch(IndexOutOfBoundsException e){
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM reservas "
                            + "para esse CD!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo CÓD. CD "
                    + "é obrigatório!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }
    
    public String listaReservaMonog() {
        if (!(this.codMonog.equals(""))) {
                EntityManager manager = this.getEntityManager();
                ReservaDAO reservaDAO = new ReservaDAO(manager);
                try{
                 this.reservasMonog = 
                        reservaDAO.listaReservasMonog(this.codMonog);
                        this.reservasMonog.get(0);
                }catch(IndexOutOfBoundsException e){
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM reservas "
                            + "para essa Monografia!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo CÓD. MONOGRAFIA "
                    + "é obrigatório!");
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
            FacesMessage fm = new FacesMessage("Não existem CDs com "
                    + "esse título!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch(NonUniqueResultException e){
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
        try{
        EntityManager manager = this.getEntityManager();
        CdDAO cdDAO = new CdDAO(manager);
        this.tituloCd = cdDAO.consultarNomePorCodigo(this.codCd);
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
        } catch(NonUniqueResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de uma Monografia "
                    + "com esse título! Pesquise pelo CÓD. MONOGRAFIA.");
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
        } catch(NonUniqueResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um Livro "
                    + "com esse título! Pesquise pelo CÓD. LIVRO.");
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
        this.tituloLivro = livroDAO.consultarNomePorCodigo(this.codLivro);
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem Livros com "
                    + "esse código!");
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

    public List<ReservaCd> getReservasCd() {
        return reservasCd;
    }

    public void setReservasCd(List<ReservaCd> reservasCd) {
        this.reservasCd = reservasCd;
    }

    public List<ReservaLivro> getReservasLivro() {
        return reservasLivro;
    }

    public void setReservasLivro(List<ReservaLivro> reservasLivro) {
        this.reservasLivro = reservasLivro;
    }

    public List<ReservaMonografia> getReservasMonog() {
        return reservasMonog;
    }

    public void setReservasMonog(List<ReservaMonografia> reservasMonog) {
        this.reservasMonog = reservasMonog;
    }

    
    
    
}
