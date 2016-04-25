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
import br.com.fatene.biblioteca.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class CadastroEmprestimoBean implements Serializable {

    private Emprestimo emprestimo = new Emprestimo();

    private String nomeSolicitante = "", matricula = "";
    private List<Livro> livros = new ArrayList<Livro>();
    private Livro livro = new Livro();
    private List<Cd> cds = new ArrayList<Cd>();
    private Cd cd = new Cd();
    private List<Monografia> monografias = new ArrayList<Monografia>();
    private List<EmprestimoLivro> emprestimosLivros
            = new ArrayList<EmprestimoLivro>();
    private List<EmprestimoCd> emprestimosCds
            = new ArrayList<EmprestimoCd>();
    private List<EmprestimoMonografia> emprestimosMonog
            = new ArrayList<EmprestimoMonografia>();
    private Monografia monografia = new Monografia();
    private String tituloCd = "", codigoCd = "";
    private String tituloMonog = "", codigoMonog = "";
    private String tituloLivro = "", codigoLivro = "";
    private PrazoMulta pmLivro = new PrazoMulta();
    private PrazoMulta pmMonog = new PrazoMulta();
    private PrazoMulta pmCd = new PrazoMulta();
    private Calendar datah = Calendar.getInstance();

    public String limpar() {
        this.emprestimo = new Emprestimo();
        this.nomeSolicitante = "";
        this.matricula = "";
        this.livros = new ArrayList<Livro>();
        this.livro = new Livro();
        this.cds = new ArrayList<Cd>();
        this.cd = new Cd();
        this.monografias = new ArrayList<Monografia>();
        this.emprestimosLivros = new ArrayList<EmprestimoLivro>();
        this.emprestimosCds = new ArrayList<EmprestimoCd>();
        this.emprestimosMonog = new ArrayList<EmprestimoMonografia>();
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
        return "/restrito/emprestimo";
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

    public String inserirEmprestimo() {
        try{
        this.defineSolicitante();
        //insere informações gerais do emprestimo
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        if (emprestimo.getSolicitante().getSituacao().equals("habilitado")) {
            GenericoDAO genericoDAO = new GenericoDAO(manager);
            this.emprestimo.getLogAcesso().setNomeDispositivo(this.getEnderecoIP());
            Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuarioLogado");
            this.emprestimo.getLogAcesso().setUsuario(new UsuarioDAO(manager).
                    consultarPorMatricula(String.valueOf(
                                    usuario.getPessoa().getMatricula())));
            genericoDAO.inserir(this.emprestimo.getLogAcesso());
            this.emprestimo.setMulta(0.0);
            this.emprestimo.setHoraEmprestimo(Calendar.getInstance().getTime());
            this.emprestimo.setDataEmprestimo(Calendar.getInstance());
            genericoDAO.inserir(this.emprestimo);
            //insere informações exclusivas do emprestimo de livros
            try {
                int i = 0;
                while (true) {
                    EmprestimoLivro empLivro = new EmprestimoLivro();
                    ExemplarLivroDAO exemplarLivroDAO = new ExemplarLivroDAO(manager);
                    ExemplarLivro exemplarLivro = exemplarLivroDAO.consultarCod(
                            String.valueOf(this.livros.get(i).getId()));
                    PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                    String tipo = "livro";
                    //se livro em braille
                    if (this.livros.get(i).getBraille() == true) {
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
                    genericoDAO.inserir(empLivro);
                    this.emprestimosLivros.add(empLivro);
                    exemplarLivro.setQuantidadeEstoque(
                            exemplarLivro.getQuantidadeEstoque() - 1);
                    genericoDAO.atualizar(exemplarLivro);
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {

            }

            //insere informações exclusivas do emprestimo de CDs
            try {
                int i = 0;
                while (true) {
                    EmprestimoCd empCd = new EmprestimoCd();
                    ExemplarCdDAO exemplarCdDAO = new ExemplarCdDAO(manager);
                    ExemplarCd exemplarCd = exemplarCdDAO.consultarCod(
                            String.valueOf(this.cds.get(i).getId()));
                    empCd.setExemplarCd(exemplarCd);
                    empCd.setPendente(true);
                    empCd.setEmprestimo(this.emprestimo);
                    empCd.setQuantidadeRenovacoes(0);
                    Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                    dataPrevisaoDevolucao.add(Calendar.DATE,
                            pmCd.getDiasDevolucao());
                    empCd.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                    genericoDAO.inserir(empCd);
                    this.emprestimosCds.add(empCd);
                    exemplarCd.setQuantidadeEstoque(
                            exemplarCd.getQuantidadeEstoque() - 1);
                    genericoDAO.atualizar(exemplarCd);
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {

            }

            //insere informações exclusivas do emprestimo de monografias
            try {
                int i = 0;
                while (true) {
                    EmprestimoMonografia empMonog = new EmprestimoMonografia();
                    ExemplarMonografiaDAO exMonogDAO = new ExemplarMonografiaDAO(manager);
                    ExemplarMonografia exMonog = exMonogDAO.consultarCod(
                            String.valueOf(this.monografias.get(i).getId()));
                    empMonog.setExemplarMonografia(exMonog);
                    empMonog.setPendente(true);
                    empMonog.setEmprestimo(this.emprestimo);
                    empMonog.setQuantidadeRenovacoes(0);
                    Calendar dataPrevisaoDevolucao = Calendar.getInstance();
                    dataPrevisaoDevolucao.add(Calendar.DATE,
                            pmMonog.getDiasDevolucao());
                    empMonog.setPrevisaoDataDevolucao(dataPrevisaoDevolucao);
                    genericoDAO.inserir(empMonog);
                    this.emprestimosMonog.add(empMonog);
                    exMonog.setQuantidadeEstoque(
                            exMonog.getQuantidadeEstoque() - 1);
                    genericoDAO.atualizar(exMonog);
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {

            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Solicitante desativado!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        }catch(NullPointerException e){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Nenhum solicitante e/ou "
                    + "exemplar selecionado!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return "/restrito/comp_emprestimo";
    }

    public List<String> listaSolicitantes(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
        EntityManager manager = this.getEntityManager();
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
        List<String> nomesSolicitantes
                = solicitanteDAO.nomesSolicitantes(nomeSolicitante);
        return nomesSolicitantes;
    }

    public String defineSolicitantePorNome() {
        try {
            EntityManager manager = this.getEntityManager();
            SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
            this.emprestimo.setSolicitante(
                    solicitanteDAO.consultarPorNome(this.nomeSolicitante));
            this.matricula = String.valueOf(this.emprestimo.getSolicitante().
                    getPessoa().getMatricula());
        } catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem solicitantes com "
                    + "esse nome!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } catch(NonUniqueResultException e){
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

    public String adicionaLivroNaLista() {
        if (!(this.matricula.equals("")) && !(this.codigoLivro.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new ReservaDAO(manager).qtdReservaLivro(this.codigoLivro)
                    < new ExemplarLivroDAO(manager).
                    consultarCod(this.codigoLivro).getQuantidadeEstoque()) {
                if (new EmprestimoDAO(manager).qtdEmprLivroPorSolic(
                        this.codigoLivro, this.matricula) == 0) {
                    LivroDAO livroDAO = new LivroDAO(manager);
                    this.livro = livroDAO.consultarLivroPorCodigo(this.codigoLivro);
                    ExemplarLivroDAO exemplarLivroDAO = new ExemplarLivroDAO(manager);
                    ExemplarLivro exemplarLivro = exemplarLivroDAO.consultarCod(
                            String.valueOf(this.livro.getId()));
                    //livro desbloqueado e estoque maior que zero
                    if (this.livro.getBloqueado() == false) {
                        if (exemplarLivro.getQuantidadeEstoque() > 0) {
                            PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                            this.defineSolicitante();
                            String tipo = "livro";
                            //se livro em braille
                            if (this.livro.getBraille() == true) {
                                tipo = "braille";
                            }
                            this.pmLivro = pmd.consultar(
                                    this.emprestimo.getSolicitante().getCategoria(), tipo);
                            //se solicitante nao atigiu o limite de emprestimos
                            if (new EmprestimoDAO(manager).qtdEmprLivroPorSolic(
                                    this.codigoLivro, this.matricula)
                                    < pmLivro.getLimiteTitulos()) {
                                this.livros.add(this.livro);
                            } else {
                                FacesContext fc = FacesContext.getCurrentInstance();
                                FacesMessage fm = new FacesMessage("O solicitante atingiu"
                                        + " o limite de empréstimos para esse tipo de material!");
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                fc.addMessage(null, fm);
                                return null;
                            }
                        } else {
                            FacesContext fc = FacesContext.getCurrentInstance();
                            FacesMessage fm = new FacesMessage("Quantidade de estoque "
                                    + "deste Livro é igual a zero!");
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                            return null;
                        }
                    } else {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        FacesMessage fm = new FacesMessage(
                                "Livro bloqueado para empréstimos!");
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                        return null;
                    }
                    this.livro = new Livro();
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
                FacesMessage fm = new FacesMessage("Estoque insuficiente! "
                        + "Realize um reserva.");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                return null;
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo MATRÍCULA e "
                    + "CÓD. LIVRO são obrigatórios!");
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
                    < new ExemplarCdDAO(manager).
                    consultarCod(this.codigoCd).getQuantidadeEstoque()) {
                if (new EmprestimoDAO(manager).qtdEmprCdPorSolic(
                        this.codigoCd, this.matricula) == 0) {
                    CdDAO cdDAO = new CdDAO(manager);
                    this.cd = cdDAO.consultarCdPorCodigo(this.codigoCd);
                    ExemplarCdDAO exemplarCdDAO = new ExemplarCdDAO(manager);
                    ExemplarCd exemplarCd = exemplarCdDAO.consultarCod(
                            String.valueOf(this.cd.getId()));
                    //livro desbloqueado e estoque maior que zero
                    if (this.cd.getBloqueado() == false) {
                        if (exemplarCd.getQuantidadeEstoque() > 0) {
                            PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                            this.defineSolicitante();
                            String tipo = "cd";
                            this.pmCd = pmd.consultar(
                                    this.emprestimo.getSolicitante().getCategoria(), tipo);
                            //se solicitante nao atigiu o limite de emprestimos
                            if (new EmprestimoDAO(manager).qtdEmprCdPorSolic(
                                    this.codigoCd, this.matricula)
                                    < pmCd.getLimiteTitulos()) {
                                this.cds.add(this.cd);
                            } else {
                                FacesContext fc = FacesContext.getCurrentInstance();
                                FacesMessage fm = new FacesMessage(
                                        "O solicitante atingiu o limite de "
                                        + "empréstimos para esse tipo "
                                        + "de material!");
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                fc.addMessage(null, fm);
                                return null;
                            }
                        } else {
                            FacesContext fc = FacesContext.getCurrentInstance();
                            FacesMessage fm = new FacesMessage("Quantidade de estoque "
                                    + "deste CD é igual a zero!");
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                            return null;
                        }
                    } else {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        FacesMessage fm = new FacesMessage(
                                "CD bloqueado para empréstimos!");
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                        return null;
                    }
                    this.cd = new Cd();
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
                FacesMessage fm = new FacesMessage("Estoque insuficiente! "
                        + "Realize um reserva.");
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

    public String adicionaMonografiaNaLista() {
        if (!(this.matricula.equals("")) && !(this.codigoMonog.equals(""))) {
            EntityManager manager = this.getEntityManager();
            if (new ReservaDAO(manager).qtdReservaMonog(this.codigoMonog)
                    < new ExemplarMonografiaDAO(manager).
                    consultarCod(this.codigoMonog).getQuantidadeEstoque()) {
                if (new EmprestimoDAO(manager).qtdEmprMonogPorSolic(
                        this.codigoMonog, this.matricula) == 0) {
                    MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
                    this.monografia = monografiaDAO.consultarMonografiaPorTitulo(
                            this.codigoMonog);
                    ExemplarMonografiaDAO exemplarMonografiaDAO
                            = new ExemplarMonografiaDAO(manager);
                    ExemplarMonografia exemplarMonografia = exemplarMonografiaDAO.consultarCod(
                            String.valueOf(this.monografia.getId()));
                    //monografia desbloqueada e estoque maior que zero
                    if (this.monografia.getBloqueado() == false) {
                        if (exemplarMonografia.getQuantidadeEstoque() > 0) {
                            PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
                            this.defineSolicitante();
                            String tipo = "monografia";
                            this.pmMonog = pmd.consultar(
                                    this.emprestimo.getSolicitante().getCategoria(), tipo);
                            //se solicitante nao atigiu o limite de emprestimos
                            if (new EmprestimoDAO(manager).qtdEmprMonogPorSolic(
                                    this.codigoMonog, this.matricula)
                                    < pmMonog.getLimiteTitulos()) {
                                this.monografias.add(this.monografia);
                            } else {
                                FacesContext fc = FacesContext.getCurrentInstance();
                                FacesMessage fm = new FacesMessage(
                                        "O solicitante atingiu o limite de "
                                        + "empréstimos para esse tipo "
                                        + "de material!");
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                fc.addMessage(null, fm);
                                return null;
                            }
                        } else {
                            FacesContext fc = FacesContext.getCurrentInstance();
                            FacesMessage fm = new FacesMessage("Quantidade de estoque "
                                    + "desta MONOGRAFIA é igual a zero!");
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                            return null;
                        }
                    } else {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        FacesMessage fm = new FacesMessage(
                                "MONOGRAFIA bloqueada para empréstimos!");
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                        return null;
                    }
                    this.monografia = new Monografia();
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
                FacesMessage fm = new FacesMessage("Estoque insuficiente! "
                        + "Realize um reserva.");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                return null;
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo MATRÍCULA e CÓD. "
                    + "MONOGRAFIA são obrigatórios!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public void defineSolicitante() {
        if (!(this.nomeSolicitante.equals(""))) {
            EntityManager manager = this.getEntityManager();
            SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
            this.emprestimo.setSolicitante(solicitanteDAO.consultarPorNome(
                    this.nomeSolicitante));
        } else if (!(this.matricula.equals(""))) {
            EntityManager manager = this.getEntityManager();
            SolicitanteDAO solicitanteDAO = new SolicitanteDAO(manager);
            this.emprestimo.setSolicitante(
                    solicitanteDAO.consultarPorMatricula(this.matricula));
        }
    }

    public List<EmprestimoLivro> getEmprestimosLivros() {
        return emprestimosLivros;
    }

    public void setEmprestimosLivros(List<EmprestimoLivro> emprestimosLivros) {
        this.emprestimosLivros = emprestimosLivros;
    }

    public List<EmprestimoCd> getEmprestimosCds() {
        return emprestimosCds;
    }

    public void setEmprestimosCds(List<EmprestimoCd> emprestimosCds) {
        this.emprestimosCds = emprestimosCds;
    }

    public List<EmprestimoMonografia> getEmprestimosMonog() {
        return emprestimosMonog;
    }

    public void setEmprestimosMonog(List<EmprestimoMonografia> emprestimosMonog) {
        this.emprestimosMonog = emprestimosMonog;
    }

    public PrazoMulta getPmLivro() {
        return pmLivro;
    }

    public void setPmLivro(PrazoMulta pmLivro) {
        this.pmLivro = pmLivro;
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

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
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

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Cd> getCds() {
        return cds;
    }

    public void setCds(List<Cd> cds) {
        this.cds = cds;
    }

    public Cd getCd() {
        return cd;
    }

    public void setCd(Cd cd) {
        this.cd = cd;
    }

    public List<Monografia> getMonografias() {
        return monografias;
    }

    public void setMonografias(List<Monografia> monografias) {
        this.monografias = monografias;
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

    public Calendar getDatah() {
        return datah;
    }

    public void setDatah(Calendar datah) {
        this.datah = datah;
    }

}
