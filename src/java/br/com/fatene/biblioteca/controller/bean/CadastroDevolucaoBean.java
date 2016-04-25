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
import br.com.fatene.biblioteca.model.Solicitante;
import br.com.fatene.biblioteca.model.Usuario;
import static com.sun.faces.facelets.util.Path.context;
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
import javax.swing.JOptionPane;

@ManagedBean
@SessionScoped
public class CadastroDevolucaoBean implements Serializable {

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
    private PrazoMulta pmLivro = new PrazoMulta();
    private PrazoMulta pmMonog = new PrazoMulta();
    private PrazoMulta pmCd = new PrazoMulta();
    private Calendar datah = Calendar.getInstance();
    private Double multa = 0.0;
    private Usuario usuario = new Usuario();
    private String ip;

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute(
                "EntityManager");
        return manager;
    }

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
        return "/restrito/devolucao";
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
        Solicitante s = solicitanteDAO.consultarPorMatricula(this.matricula);
        this.matricula = String.valueOf(s.getPessoa().getMatricula());
        this.nomeSolicitante = s.getPessoa().getNome();
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
        return null;
    }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem livros com "
                    + "esse CÓDIGO!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
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

    public String nomeCdPorCodigo() {
        try{
        EntityManager manager = this.getEntityManager();
        CdDAO cdDAO = new CdDAO(manager);
        this.tituloCd = cdDAO.consultarNomePorCodigo(this.codigoCd);
        return null;
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem CDs com "
                    + "esse CÓDIGO!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        } 
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

    public String nomeMonogPorCodigo() {
        try{
            EntityManager manager = this.getEntityManager();
        MonografiaDAO monografiaDAO = new MonografiaDAO(manager);
        this.tituloMonog = monografiaDAO.consultarNomePorCodigo(this.codigoMonog);
        }catch (NoResultException e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Não existem monografias com "
                    + "esse CÓDIGO!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return null;
        }
        return null;
    }

    public String inserirDevolucao() {
        try{
        EntityManager manager = this.getEntityManager();
        GenericoDAO genericoDAO = new GenericoDAO(manager);
        this.ip = this.getEnderecoIP();
        this.usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuarioLogado");
        //atualiza informações exclusivas do emprestimo de livros
        try {
            int i = 0;
            while (true) {
                Emprestimo emp = this.emprLivros.get(i).getEmprestimo();
                EmprestimoLivro empLivro = this.emprLivros.get(i);
                ExemplarLivro expLivro = this.emprLivros.get(i).
                        getExemplarLivro();

                emp.setMulta(this.multa);
                empLivro.setPendente(false);
                empLivro.setDataDevolucao(Calendar.getInstance());
                expLivro.setQuantidadeEstoque(
                        expLivro.getQuantidadeEstoque() + 1);
                
                if(new ReservaDAO(manager).qtdReservaLivro(this.codigoLivro)>
                new ExemplarLivroDAO(manager).
                        consultarCod(this.codigoLivro).getQuantidadeEstoque()){
                try {
                    ReservaDAO reservaDAO = new ReservaDAO(manager);
                    ReservaLivro reservaLivro = new ReservaLivro();

                    reservaLivro = reservaDAO.reservaLivroMaisAntiga(
                            String.valueOf(expLivro.getLivro().getId()));
                    Calendar dataLimite = Calendar.getInstance();
                    if (dataLimite.get(Calendar.DAY_OF_WEEK) == 6) {
                        dataLimite.add(Calendar.DATE,
                                3);
                    } else if (dataLimite.get(Calendar.DAY_OF_WEEK) == 7) {
                        dataLimite.add(Calendar.DATE,
                                2);
                    } else {
                        dataLimite.add(Calendar.DATE,
                                1);
                    }
                    reservaLivro.setDataLimite(dataLimite);
                    
                    genericoDAO.atualizar(reservaLivro);
                } catch (NoResultException e) {

                }
                }

                genericoDAO.atualizar(emp);
                genericoDAO.atualizar(empLivro);
                genericoDAO.atualizar(expLivro);
                
                emp = new Emprestimo();
                empLivro = new EmprestimoLivro();
                expLivro = new ExemplarLivro();
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
                ExemplarCd expCd = this.emprCds.get(i).
                        getExemplarCd();

                emp.setMulta(this.multa);
                empCd.setPendente(false);
                empCd.setDataDevolucao(Calendar.getInstance());
                expCd.setQuantidadeEstoque(
                        expCd.getQuantidadeEstoque() + 1);

                
                genericoDAO.atualizar(emp);
                genericoDAO.atualizar(empCd);
                genericoDAO.atualizar(expCd);
                
                if(new ReservaDAO(manager).qtdReservaCd(this.codigoCd)>
                new ExemplarCdDAO(manager).
                        consultarCod(this.codigoCd).getQuantidadeEstoque()){
                try {
                    ReservaDAO reservaDAO = new ReservaDAO(manager);
                    ReservaCd reservaCd = new ReservaCd();

                    reservaCd = reservaDAO.reservaCdMaisAntiga(
                            String.valueOf(expCd.getCd().getId()));
                    Calendar dataLimite = Calendar.getInstance();
                    if (dataLimite.get(Calendar.DAY_OF_WEEK) == 6) {
                        dataLimite.add(Calendar.DATE,
                                3);
                    } else if (dataLimite.get(Calendar.DAY_OF_WEEK) == 7) {
                        dataLimite.add(Calendar.DATE,
                                2);
                    } else {
                        dataLimite.add(Calendar.DATE,
                                1);
                    }
                    reservaCd.setDataLimite(dataLimite);
                    
                    genericoDAO.atualizar(reservaCd);
                } catch (NoResultException e) {

                }
                }

                emp = new Emprestimo();
                empCd = new EmprestimoCd();
                expCd = new ExemplarCd();
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
                ExemplarMonografia expMonog = this.emprMonografias.get(i).
                        getExemplarMonografia();

                emp.setMulta(this.multa);
                empMonog.setPendente(false);
                empMonog.setDataDevolucao(Calendar.getInstance());
                expMonog.setQuantidadeEstoque(
                        expMonog.getQuantidadeEstoque() + 1);

                genericoDAO.atualizar(emp);
                genericoDAO.atualizar(empMonog);
                genericoDAO.atualizar(expMonog);
                
                if(new ReservaDAO(manager).qtdReservaMonog(this.codigoMonog)>
                new ExemplarMonografiaDAO(manager).
                        consultarCod(this.codigoMonog).getQuantidadeEstoque()){
                try {
                    ReservaDAO reservaDAO = new ReservaDAO(manager);
                    ReservaMonografia reservaMonog = new ReservaMonografia();

                    reservaMonog = reservaDAO.reservaMonogMaisAntiga(
                            String.valueOf(expMonog.getMonografia().getId()));
                    Calendar dataLimite = Calendar.getInstance();
                    if (dataLimite.get(Calendar.DAY_OF_WEEK) == 6) {
                        dataLimite.add(Calendar.DATE,
                                3);
                    } else if (dataLimite.get(Calendar.DAY_OF_WEEK) == 7) {
                        dataLimite.add(Calendar.DATE,
                                2);
                    } else {
                        dataLimite.add(Calendar.DATE,
                                1);
                    }
                    reservaMonog.setDataLimite(dataLimite);
                    
                    genericoDAO.atualizar(reservaMonog);
                } catch (NoResultException e) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("Erro interno: Não foi "
                            + "possível localizar a "
                            + "reserva mais antiga!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
                }

                emp = new Emprestimo();
                empMonog = new EmprestimoMonografia();
                expMonog = new ExemplarMonografia();
                i++;
            }
        } catch (IndexOutOfBoundsException e) {

        } 
        return "/restrito/comp_devolucao";
    }catch(NullPointerException e){
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage("Nenhum solicitante e/ou "
                    + "exemplar selecionado!");
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage(null, fm);
        return null;
    }
    }

    public String adicionaLivroNaLista() {
        if (!(this.matricula.equals("")) && !(this.codigoLivro.equals(""))) {
        EntityManager manager = this.getEntityManager();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(manager);
        try {
            this.emprLivro = emprestimoDAO.ConsultEmprLivroMatriculaCodLivro(
                    this.matricula, this.codigoLivro);
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
            Integer dias = new CalculoDatas().diasEntreDatas(
                    this.emprLivro.getPrevisaoDataDevolucao());
            if ((dias > 0)) {
                this.multa = this.multa + (dias * this.pmLivro.getMultaDiaTitulo());
                this.emprLivro.setMulta(dias * this.pmLivro.getMultaDiaTitulo());
            }
            this.emprLivros.add(this.emprLivro);
            this.emprLivro = new EmprestimoLivro();
        } catch (NoResultException e) {
            
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM empréstimos "
                    + "pendentes para esse LIVRO e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
                }
        
        }else {
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
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(manager);
        try {
            this.emprCd = emprestimoDAO.ConsultEmprCdMatriculaCodCd(
                    this.matricula, this.codigoCd);
            PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
            String tipo = "cd";
            this.pmCd = pmd.consultar(
                    this.emprCd.getEmprestimo().getSolicitante()
                    .getCategoria(), tipo);
            CalculoDatas cdata = new CalculoDatas();
            Integer dias = cdata.diasEntreDatas(
                    this.emprCd.getPrevisaoDataDevolucao());
            if ((dias > 0)) {
                this.multa = this.multa + (dias * this.pmCd.getMultaDiaTitulo());
                this.emprCd.setMulta(dias * this.pmCd.getMultaDiaTitulo());
            }
            this.emprCds.add(this.emprCd);
            this.emprCd = new EmprestimoCd();
        } catch (NoResultException e) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM empréstimos "
                    + "pendentes para esse CD e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
        }
        }else {
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
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(manager);
        try {
            this.emprMonografia = emprestimoDAO.
                    ConsultEmprMonogMatriculaCodMonog(this.matricula, this.codigoMonog);
            PrazoMultaDAO pmd = new PrazoMultaDAO(manager);
            String tipo = "monografia";
            this.pmMonog = pmd.consultar(
                    this.emprMonografia.getEmprestimo().getSolicitante()
                    .getCategoria(), tipo);
            Integer dias = new CalculoDatas().diasEntreDatas(
                    this.emprMonografia.getPrevisaoDataDevolucao());
            if ((dias > 0)) {
                this.multa = this.multa + (dias * this.pmMonog.getMultaDiaTitulo());
                this.emprMonografia.setMulta(dias * this.pmMonog.getMultaDiaTitulo());
            }
            this.emprMonografias.add(this.emprMonografia);
            this.emprMonografia = new EmprestimoMonografia();
        } catch (NoResultException e) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage("NÃO EXISTEM empréstimos "
                    + "pendentes para essa MONOGRAFIA e SOLICITANTE!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                    return null;
        }
        }else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Campo MATRÍCULA e "
                    + "CÓD. MONOGRAFIA são obrigatórios!");
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

    public Calendar getDatah() {
        return datah;
    }

    public void setDatah(Calendar datah) {
        this.datah = datah;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

}
