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
import br.com.fatene.biblioteca.controller.utilitarios.CalculoDatas;
import br.com.fatene.biblioteca.model.Emprestimo;
import br.com.fatene.biblioteca.model.EmprestimoCd;
import br.com.fatene.biblioteca.model.EmprestimoLivro;
import br.com.fatene.biblioteca.model.EmprestimoMonografia;
import br.com.fatene.biblioteca.model.PrazoMulta;
import br.com.fatene.biblioteca.model.Solicitante;
import br.com.fatene.biblioteca.model.Usuario;
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
@SessionScoped
public class CadastroRenovacaoBean implements Serializable {

    private String nomeSolicitante = "", matricula = "";
    private List<EmprestimoLivro> emprLivros = new ArrayList<EmprestimoLivro>();
    private EmprestimoLivro emprLivro = new EmprestimoLivro();
    private List<EmprestimoCd> emprCds = new ArrayList<EmprestimoCd>();
    private EmprestimoCd emprCd = new EmprestimoCd();
    private List<EmprestimoMonografia> emprMonografias
            = new ArrayList<EmprestimoMonografia>();
    private EmprestimoMonografia emprMonografia
            = new EmprestimoMonografia();
    private String tituloCd = "", codigoCd = "";
    private String tituloMonog = "", codigoMonog = "";
    private String tituloLivro = "", codigoLivro = "";
    private PrazoMulta pmLivro;
    private PrazoMulta pmMonog;
    private PrazoMulta pmCd;
    private Double multa = 0.0;
    private String ip = "";
    private Usuario usuario = new Usuario();
    private Calendar datah = Calendar.getInstance();
    
    
    public String limpar(){
    this.nomeSolicitante = "";
    this.matricula = "";
    this.emprLivros = new ArrayList<EmprestimoLivro>();
    this.emprCds = new ArrayList<EmprestimoCd>();
    this.emprMonografias = new ArrayList<EmprestimoMonografia>();
    this.tituloCd = "";
    this.codigoCd = "";
    this.tituloMonog = "";
    this.codigoMonog = "";
    this.tituloLivro = "";
    this.codigoLivro = "";
    this.ip = "";
    this.pmLivro = new PrazoMulta();
    this.pmMonog = new PrazoMulta();
    this.pmCd = new PrazoMulta();
    this.usuario = new Usuario();
        return "/restrito/renovacao";
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

    public String defineSolicitantePorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
            Solicitante s = solicitanteDAO.consultarPorNome(this.nomeSolicitante);
            this.matricula = String.valueOf(s.getPessoa().getMatricula());
            this.nomeSolicitante = s.getPessoa().getNome();
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
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        Solicitante s = solicitanteDAO.consultarPorMatricula(this.matricula);
        this.matricula = String.valueOf(s.getPessoa().getMatricula());
        this.nomeSolicitante = s.getPessoa().getNome();
        return null;
    }

    public String codigoLivroPorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            LivroDAO livroDAO = new LivroDAO(manager);
            this.codigoLivro = livroDAO.consultarCodigoPorNome(this.tituloLivro);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem solicitantes com "
                    + "essa matricula!");
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
            this.tituloLivro = livroDAO.consultarNomePorCodigo(this.codigoLivro);
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
        } catch (NonUniqueResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de um CD "
                    + "com esse título! Pesquise pelo CÓDIGO.");
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
            this.tituloCd = cdDAO.consultarNomePorCodigo(this.codigoCd);
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
        } catch (NonUniqueResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Mais de uma monografia "
                    + "com esse título! Pesquise pelo CÓDIGO.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String nomeMonogPorCodigo() {
        try {
            EntityManager manager = this.getEntityManager();
            MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
            this.tituloMonog = monografiaDAO.consultarNomePorCodigo(this.codigoMonog);
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem monografias com "
                    + "esse código!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String inserirRenovacao() {

        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        this.ip = this.getEnderecoIP();
        this.usuario = (Usuario) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("usuarioLogado");
        //atualiza informações exclusivas do emprestimo de livros
        try {
            int i = 0;
            while (true) {
                Emprestimo emp = this.emprLivros.get(i).getEmprestimo();
                EmprestimoLivro empLivro = this.emprLivros.get(i);

                emp.setMulta(this.multa);
                Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                dataPrevisaoDevolucao.add(Calendar.DATE,
                        pmLivro.getDiasDevolucao());
                empLivro.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                empLivro.setQuantidadeRenovacoes(
                        empLivro.getQuantidadeRenovacoes() + 1);

                genericoDAO.atualizar(emp);
                genericoDAO.atualizar(empLivro);

                emp = new Emprestimo();
                empLivro = new EmprestimoLivro();
                i++;
            }
        } catch (IndexOutOfBoundsException e) {

        }

        //atualiza informações exclusivas do emprestimo de CDs
        try {
            int i = 0;
            while (true) {
                Emprestimo emp = this.emprCds.get(i).getEmprestimo();
                EmprestimoCd empCd = this.emprCds.get(i);

                emp.setMulta(this.multa);
                Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                dataPrevisaoDevolucao.add(Calendar.DATE,
                        pmLivro.getDiasDevolucao());
                empCd.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                empCd.setQuantidadeRenovacoes(
                        empCd.getQuantidadeRenovacoes() + 1);

                genericoDAO.atualizar(emp);
                genericoDAO.atualizar(empCd);

                emp = new Emprestimo();
                empCd = new EmprestimoCd();
                i++;
            }
        } catch (IndexOutOfBoundsException e) {

        }

        //atuliza informações exclusivas do emprestimo de monografias
        try {
            int i = 0;
            while (true) {
                Emprestimo emp = this.emprMonografias.get(i).getEmprestimo();
                EmprestimoMonografia empMonog = this.emprMonografias.get(i);

                emp.setMulta(this.multa);
                Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                dataPrevisaoDevolucao.add(Calendar.DATE,
                        pmLivro.getDiasDevolucao());
                empMonog.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                empMonog.setQuantidadeRenovacoes(
                        empMonog.getQuantidadeRenovacoes() + 1);

                genericoDAO.atualizar(emp);
                genericoDAO.atualizar(empMonog);

                emp = new Emprestimo();
                empMonog = new EmprestimoMonografia();
                i++;
            }
        } catch (IndexOutOfBoundsException e) {

        }
        return "/restrito/comp_renovacao";
    }

    public String adicionaLivroNaLista() {
        if (!(this.matricula.equals("")) && !(this.codigoLivro.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new ReservaDAO(manager).qtdReservaLivro(this.codigoLivro)
                    <= new ExemplarLivroDAO(manager).
                    consultarCod(this.codigoLivro).getQuantidadeEstoque()) {
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO(manager);
                try {
                    this.emprLivro = emprestimoDAO.ConsultEmprLivroMatriculaCodLivro(
                            this.matricula, this.codigoLivro);
                } catch (NoResultException e) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM empréstimos "
                            + "pendentes para esse LIVRO e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
                PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                String tipo = "livro";
                //se livro em braille
                if (this.emprLivro.getExemplarLivro().getLivro().
                        getBraille() == true) {
                    tipo = "braille";
                }
                this.pmLivro = pmd.consultar(
                        this.emprLivro.getEmprestimo().getSolicitante()
                        .getCategoria(), tipo);
                if (this.emprLivro.getQuantidadeRenovacoes()
                        < this.pmLivro.getLimiteRenovacao()) {
                    Integer dias = new CalculoDatas().diasEntreDatas(
                            this.emprLivro.getPrevisaoDataDevolucao());
                    if ((dias > 0)) {
                        this.multa = this.multa + (dias * this.pmLivro.getMultaDiaTitulo());
                        this.emprLivro.setMulta(dias * this.pmLivro.getMultaDiaTitulo());
                    }
                    this.emprLivros.add(this.emprLivro);
                    this.emprLivro = new EmprestimoLivro();
                } else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage(
                            "O solicitante atingiu o limite de "
                            + "renovações para esse tipo "
                            + "de material!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }

            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage("Material reservado! "
                        + "Favor efetuar a devolução.");
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

    public String adicionaCdNaLista() {
        if (!(this.matricula.equals("")) && !(this.codigoCd.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new ReservaDAO(manager).qtdReservaCd(this.codigoCd)
                    <= new ExemplarCdDAO(manager).
                    consultarCod(this.codigoCd).getQuantidadeEstoque()) {
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO(manager);
                try {
                    this.emprCd = emprestimoDAO.ConsultEmprCdMatriculaCodCd(
                            this.matricula, this.codigoCd);
                } catch (NoResultException e) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM empréstimos "
                            + "pendentes para esse CD e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
                PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                String tipo = "cd";
                this.pmCd = pmd.consultar(
                        this.emprCd.getEmprestimo().getSolicitante()
                        .getCategoria(), tipo);
                if (this.emprCd.getQuantidadeRenovacoes()
                        < this.pmCd.getLimiteRenovacao()) {
                    Integer dias = new CalculoDatas().diasEntreDatas(
                            this.emprCd.getPrevisaoDataDevolucao());
                    if ((dias > 0)) {
                        this.multa = this.multa + (dias * this.pmCd.getMultaDiaTitulo());
                        this.emprCd.setMulta(dias * this.pmCd.getMultaDiaTitulo());
                    }
                    this.emprCds.add(this.emprCd);
                    this.emprCd = new EmprestimoCd();
                } else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage(
                            "O solicitante atingiu o limite de "
                            + "renovações para esse tipo "
                            + "de material!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }

            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage("Material reservado! "
                        + "Favor efetuar a devolução.");
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

    public String adicionaMonogNaLista() {
        if (!(this.matricula.equals("")) && !(this.codigoMonog.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new ReservaDAO(manager).qtdReservaMonog(this.codigoMonog)
                    <= new ExemplarMonografiaDAO(manager).
                    consultarCod(this.codigoMonog).getQuantidadeEstoque()) {
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO(manager);
                try {
                    this.emprMonografia = emprestimoDAO.
                            ConsultEmprMonogMatriculaCodMonog(this.matricula, this.codigoMonog);
                } catch (NoResultException e) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM empréstimos "
                            + "pendentes para essa MONOGRAFIA e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
                PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                String tipo = "monografia";
                this.pmMonog = pmd.consultar(
                        this.emprMonografia.getEmprestimo().getSolicitante()
                        .getCategoria(), tipo);
                if (this.emprMonografia.getQuantidadeRenovacoes()
                        < this.pmMonog.getLimiteRenovacao()) {
                    Integer dias = new CalculoDatas().diasEntreDatas(
                            this.emprMonografia.getPrevisaoDataDevolucao());
                    if ((dias > 0)) {
                        this.multa = this.multa + (dias * this.pmMonog.getMultaDiaTitulo());
                        this.emprMonografia.setMulta(dias * this.pmMonog.getMultaDiaTitulo());
                    }
                    this.emprMonografias.add(this.emprMonografia);
                    this.emprMonografia = new EmprestimoMonografia();
                } else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage(
                            "O solicitante atingiu o limite de "
                            + "renovações para esse tipo "
                            + "de material!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }

            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage("Material reservado! "
                        + "Favor efetuar a devolução.");
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

    public List<EmprestimoLivro> getEmprLivros() {
        return emprLivros;
    }

    public void setEmprLivros(List<EmprestimoLivro> emprLivros) {
        this.emprLivros = emprLivros;
    }

    public EmprestimoLivro getEmprLivro() {
        return emprLivro;
    }

    public void setEmprLivro(EmprestimoLivro emprLivro) {
        this.emprLivro = emprLivro;
    }

    public List<EmprestimoCd> getEmprCds() {
        return emprCds;
    }

    public void setEmprCds(List<EmprestimoCd> emprCds) {
        this.emprCds = emprCds;
    }

    public EmprestimoCd getEmprCd() {
        return emprCd;
    }

    public void setEmprCd(EmprestimoCd emprCd) {
        this.emprCd = emprCd;
    }

    public List<EmprestimoMonografia> getEmprMonografias() {
        return emprMonografias;
    }

    public void setEmprMonografias(List<EmprestimoMonografia> emprMonografias) {
        this.emprMonografias = emprMonografias;
    }

    public EmprestimoMonografia getEmprMonografia() {
        return emprMonografia;
    }

    public void setEmprMonografia(EmprestimoMonografia emprMonografia) {
        this.emprMonografia = emprMonografia;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calendar getDatah() {
        return datah;
    }

    public void setDatah(Calendar datah) {
        this.datah = datah;
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

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

}
