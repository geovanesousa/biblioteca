package br.com.fatene.biblioteca.controller.bean;

import br.com.fatene.biblioteca.controller.dao.ExemplarCdDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarLivroDAO;
import br.com.fatene.biblioteca.controller.dao.ExemplarMonografiaDAO;
import br.com.fatene.biblioteca.controller.dao.GenericoDAO;
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
import br.com.fatene.biblioteca.model.ReservaCd;
import br.com.fatene.biblioteca.model.ReservaLivro;
import br.com.fatene.biblioteca.model.ReservaMonografia;
import br.com.fatene.biblioteca.model.Usuario;
import java.io.Serializable;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class CadastroRetiradaBean implements Serializable {

    private String codRetCd = "", codRetLivro="", codRetMonog="";
    private Livro livro = new Livro();
    private Cd cd = new Cd();
    private Monografia monografia = new Monografia();
    private String tituloCd = "", codigoCd = "";
    private String tituloMonog = "", codigoMonog = "";
    private String tituloLivro = "", codigoLivro = "";
    private PrazoMulta pmLivro = new PrazoMulta();
    private PrazoMulta pmMonog = new PrazoMulta();
    private PrazoMulta pmCd = new PrazoMulta();
    private Emprestimo emprestimo = new Emprestimo();
    private EmprestimoLivro empLivro;
    private EmprestimoCd empCd;
    private EmprestimoMonografia empMonog;

    public String limpar(){
    this.codRetCd = "";
    this.codRetLivro="";
    this.codRetMonog="";
    this.livro = new Livro();
    this.cd = new Cd();
    this.monografia = new Monografia();
    this.tituloCd = "";
    this.codigoCd = "";
    this.tituloMonog = "";
    this.codigoMonog = "";
    this.tituloLivro = "";
    this.codigoLivro = "";
    this.pmLivro = new PrazoMulta();
    this.pmMonog = new PrazoMulta();
    this.pmCd = new PrazoMulta();
    this.emprestimo = new Emprestimo();
    this.empLivro = new EmprestimoLivro();
    this.empCd = new EmprestimoCd();
    this.empMonog = new EmprestimoMonografia();
        return "/restrito/fila_reservas";
    }
    
    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

    private String getEnderecoIP() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        String enderecoIP = request.getHeader("X-FORWARDED-FOR");
        if (enderecoIP == null) {
            enderecoIP = request.getRemoteAddr();
        }
        return enderecoIP;
    }

    public String retiradaLivro() {
        if(!(this.codRetLivro.equals(""))){
        try{
        //insere informações gerais do emprestimo
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        ReservaLivro reservaLivro = new ReservaDAO(manager).
                reservaLivro(this.codRetLivro);
        ReservaLivro rl = new ReservaDAO(manager).listaReservasLivro(
                String.valueOf(reservaLivro.getLivro().getId())).get(0);
        if (reservaLivro.getId() == rl.getId()) {
            if (reservaLivro.getSolicitante().getSituacao().equals("habilitado")) {
                GenericoDAO genericoDAO = new GenericoDAO(manager);
                this.emprestimo.getLogAcesso().setNomeDispositivo(this.getEnderecoIP());
                Usuario usuario = (Usuario) FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().get("usuarioLogado");
                this.emprestimo.getLogAcesso().setUsuario(new UsuarioDAO(manager).
                    consultarPorMatricula(
                            String.valueOf(usuario.getId())));
                genericoDAO.inserir(this.emprestimo.getLogAcesso());
                this.emprestimo.setSolicitante(reservaLivro.getSolicitante());
                this.emprestimo.setMulta(0.0);
                this.emprestimo.setHoraEmprestimo(Calendar.getInstance().getTime());
                this.emprestimo.setDataEmprestimo(Calendar.getInstance());
                genericoDAO.inserir(this.emprestimo);

                EmprestimoLivro empLivro = new EmprestimoLivro();
                ExemplarLivroDAO exemplarLivroDAO = new ExemplarLivroDAO(manager);
                ExemplarLivro exemplarLivro = exemplarLivroDAO.consultarCod(
                        String.valueOf(reservaLivro.getLivro().getId()));
                PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                String tipo = "livro";
                //se livro em braille
                if (reservaLivro.getLivro().getBraille() == true) {
                    tipo = "braille";
                }
                this.pmLivro = pmd.consultar(
                        this.emprestimo.getSolicitante().getCategoria(), tipo);
                empLivro.setExemplarLivro(exemplarLivro);
                empLivro.setPendente(true);
                empLivro.setEmprestimo(this.emprestimo);
                empLivro.setQuantidadeRenovacoes(0);
                Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                dataPrevisaoDevolucao.add(Calendar.DATE,
                        pmLivro.getDiasDevolucao());
                empLivro.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                reservaLivro.setRecebida(true);

                genericoDAO.inserir(empLivro);
                this.empLivro = empLivro;
                exemplarLivro.setQuantidadeEstoque(
                        exemplarLivro.getQuantidadeEstoque() - 1);
                genericoDAO.atualizar(exemplarLivro);
                genericoDAO.atualizar(reservaLivro);

            }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Solicitante desativado!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Desculpe você ainda não é "
                    + "o próximo da fila.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }catch(NoResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("CÓD. RESERVA do Livro "
                    + "é inválido!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }       
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo CÓD. RESERVA do Livro "
                    + "é obrigatório!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;

    }

    public String retiradaCd() {
        if(!(this.codRetCd.equals(""))){
        try{
        //insere informações gerais do emprestimo
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        ReservaCd reservaCd = new ReservaDAO(manager).
                reservaCd(this.codRetCd);
        ReservaCd rc = new ReservaDAO(manager).listaReservasCd(
                String.valueOf(reservaCd.getCd().getId())).get(0);
        if (reservaCd.getId() == rc.getId()) {
            if (reservaCd.getSolicitante().getSituacao().equals("habilitado")) {
                GenericoDAO genericoDAO = new GenericoDAO(manager);
                this.emprestimo.getLogAcesso().setNomeDispositivo(this.getEnderecoIP());
                Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuarioLogado");
                this.emprestimo.getLogAcesso().setUsuario(new UsuarioDAO(manager).
                    consultarPorMatricula(
                            String.valueOf(usuario.getId())));
                genericoDAO.inserir(this.emprestimo.getLogAcesso());
                this.emprestimo.setSolicitante(reservaCd.getSolicitante());
                this.emprestimo.setMulta(0.0);
                this.emprestimo.setHoraEmprestimo(Calendar.getInstance().getTime());
                this.emprestimo.setDataEmprestimo(Calendar.getInstance());
                genericoDAO.inserir(this.emprestimo);

                EmprestimoCd empCd = new EmprestimoCd();
                ExemplarCdDAO exemplarCdDAO = new ExemplarCdDAO(manager);
                ExemplarCd exemplarCd = exemplarCdDAO.consultarCod(
                        String.valueOf(reservaCd.getCd().getId()));
                empCd.setExemplarCd(exemplarCd);
                empCd.setPendente(true);
                empCd.setEmprestimo(this.emprestimo);
                empCd.setQuantidadeRenovacoes(0);
                Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                dataPrevisaoDevolucao.add(Calendar.DATE,
                        pmCd.getDiasDevolucao());
                empCd.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                reservaCd.setRecebida(true);

                genericoDAO.inserir(empCd);
                this.empCd = empCd;
                exemplarCd.setQuantidadeEstoque(
                        exemplarCd.getQuantidadeEstoque() - 1);
                genericoDAO.atualizar(exemplarCd);
                genericoDAO.atualizar(reservaCd);
            }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Solicitante desativado!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Desculpe você ainda não é "
                    + "o próximo da fila.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }catch(NoResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("CÓD. RESERVA do CD "
                    + "é inválido!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo CÓD. RESERVA do CD "
                    + "é obrigatório!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String retiradaMonog() {
        if(!(this.codRetMonog.equals(""))){
        try{
        //insere informações gerais do emprestimo
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        ReservaMonografia reservaMonog = new ReservaDAO(manager).
                reservaMonog(this.codRetMonog);
        ReservaMonografia rm = new ReservaDAO(manager).listaReservasMonog(
                String.valueOf(reservaMonog.getMonografia().getId())).get(0);
        if (reservaMonog.getId() == rm.getId()) {
            if (reservaMonog.getSolicitante().getSituacao().equals("habilitado")) {
                GenericoDAO genericoDAO = new GenericoDAO(manager);
                this.emprestimo.getLogAcesso().setNomeDispositivo(this.getEnderecoIP());
                UsuarioDAO usuarioDAO = new UsuarioDAO(manager);
                Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuarioLogado");
                this.emprestimo.getLogAcesso().setUsuario(new UsuarioDAO(manager).
                    consultarPorMatricula(
                            String.valueOf(usuario.getId())));
                genericoDAO.inserir(this.emprestimo.getLogAcesso());
                this.emprestimo.setSolicitante(reservaMonog.getSolicitante());
                this.emprestimo.setMulta(0.0);
                this.emprestimo.setHoraEmprestimo(Calendar.getInstance().getTime());
                this.emprestimo.setDataEmprestimo(Calendar.getInstance());
                genericoDAO.inserir(this.emprestimo);

                EmprestimoMonografia empMonog = new EmprestimoMonografia();
                ExemplarMonografiaDAO exemplarMonogDAO = new ExemplarMonografiaDAO(manager);
                ExemplarMonografia exemplarMonog = exemplarMonogDAO.consultarCod(
                        String.valueOf(reservaMonog.getMonografia().getId()));
                empMonog.setExemplarMonografia(exemplarMonog);
                empMonog.setPendente(true);
                empMonog.setEmprestimo(this.emprestimo);
                empMonog.setQuantidadeRenovacoes(0);
                Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                dataPrevisaoDevolucao.add(Calendar.DATE,
                        pmMonog.getDiasDevolucao());
                empMonog.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                reservaMonog.setRecebida(true);

                genericoDAO.inserir(empMonog);
                this.empMonog = empMonog;
                exemplarMonog.setQuantidadeEstoque(
                        exemplarMonog.getQuantidadeEstoque() - 1);
                genericoDAO.atualizar(exemplarMonog);
                genericoDAO.atualizar(reservaMonog);
            }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Solicitante desativado!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Desculpe você ainda não é "
                    + "o próximo da fila.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }catch(NoResultException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("CÓD. RESERVA do Livro "
                    + "é inválido!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo CÓD. RESERVA da Monografia "
                    + "é obrigatório!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public EmprestimoLivro getEmpLivro() {
        return empLivro;
    }

    public void setEmpLivro(EmprestimoLivro empLivro) {
        this.empLivro = empLivro;
    }

    public EmprestimoCd getEmpCd() {
        return empCd;
    }

    public void setEmpCd(EmprestimoCd empCd) {
        this.empCd = empCd;
    }

    public EmprestimoMonografia getEmpMonog() {
        return empMonog;
    }

    public void setEmpMonog(EmprestimoMonografia empMonog) {
        this.empMonog = empMonog;
    }

    
    
    
    public String getCodRetCd() {
        return codRetCd;
    }

    public void setCodRetCd(String codRetCd) {
        this.codRetCd = codRetCd;
    }

    public String getCodRetLivro() {
        return codRetLivro;
    }

    public void setCodRetLivro(String codRetLivro) {
        this.codRetLivro = codRetLivro;
    }

    public String getCodRetMonog() {
        return codRetMonog;
    }

    public void setCodRetMonog(String codRetMonog) {
        this.codRetMonog = codRetMonog;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Cd getCd() {
        return cd;
    }

    public void setCd(Cd cd) {
        this.cd = cd;
    }

    public Monografia getMonografia() {
        return monografia;
    }

    public void setMonografia(Monografia monografia) {
        this.monografia = monografia;
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

    public PrazoMulta getPmLivro() {
        return pmLivro;
    }

    public void setPmLivro(PrazoMulta pmLivro) {
        this.pmLivro = pmLivro;
    }

    public PrazoMulta getPmMonog() {
        return pmMonog;
    }

    public void setPmMonog(PrazoMulta pmMonog) {
        this.pmMonog = pmMonog;
    }

    public PrazoMulta getPmCd() {
        return pmCd;
    }

    public void setPmCd(PrazoMulta pmCd) {
        this.pmCd = pmCd;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

}
