package br.com.fatene.biblioteca.controller.dao;

import br.com.fatene.biblioteca.controller.utilitarios.CalculoDatas;
import br.com.fatene.biblioteca.model.ReservaCd;
import br.com.fatene.biblioteca.model.ReservaLivro;
import br.com.fatene.biblioteca.model.ReservaMonografia;
import java.util.List;
import javax.persistence.EntityManager;

public class ReservaDAO {

    private EntityManager manager;

    public ReservaDAO(EntityManager manager) {
        this.manager = manager;
    }
    
    public ReservaLivro reservaLivro(String codReserva) {
        
        String jpql = "SELECT a FROM reserv_livro a"
                + " WHERE a.id = '"+codReserva+"'";
        
        ReservaLivro rl = this.manager.createQuery(
                jpql, ReservaLivro.class).getSingleResult();
        return rl;
    }
    
    public ReservaCd reservaCd(String codReserva) {
        
        String jpql = "SELECT a FROM reserv_cd a"
                + " WHERE a.id = '"+codReserva+"'";
        
        ReservaCd rc = this.manager.createQuery(
                jpql, ReservaCd.class).getSingleResult();
        return rc;
    }
    
    public ReservaMonografia reservaMonog(String codReserva) {
        
        String jpql = "SELECT a FROM reserv_monog a"
                + " WHERE a.id = '"+codReserva+"'";
        
        ReservaMonografia rm = this.manager.createQuery(
                jpql, ReservaMonografia.class).getSingleResult();
        return rm;
    }
    
    public List<ReservaMonografia> listaReservasMonog(String codMonog) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT a FROM reserv_monog a"
                + " WHERE a.monografia.id = '"+codMonog+"'"
                + " AND a.recebida = 'false'"
                + " AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL) ORDER BY a.id";
        
        List<ReservaMonografia> lista = this.manager.createQuery(
                jpql, ReservaMonografia.class).getResultList();
        return lista;
    }
    
    public List<ReservaLivro> listaReservasLivro(String codLivro) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT a FROM reserv_livro a"
                + " WHERE a.livro.id = '"+codLivro+"'"
                + " AND a.recebida = 'false'"
                + " AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL) ORDER BY a.id";
        
        List<ReservaLivro> lista = this.manager.createQuery(
                jpql, ReservaLivro.class).getResultList();
        return lista;
    }
    
    public List<ReservaCd> listaReservasCd(String codCd) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT a FROM reserv_cd a"
                + " WHERE a.cd.id = '"+codCd+"'"
                + " AND a.recebida = 'false'"
                + " AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL) ORDER BY a.id";
        
        List<ReservaCd> lista = this.manager.createQuery(
                jpql, ReservaCd.class).getResultList();
        
        return lista;
    }
    
    public ReservaLivro reservaLivroMaisAntiga(String codLivro) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT a FROM reserv_livro a"
                + " WHERE a.livro.id = '"+codLivro+"'"
                + " AND a.recebida = 'false'"
                + " AND a.dataLimite IS NULL"
                + " AND a.dataDaReserva = (SELECT MIN(r.dataDaReserva)"
                + " FROM reserv_livro r) ORDER BY a.id";
        
        List<ReservaLivro> lista = this.manager.createQuery(
                jpql, ReservaLivro.class).getResultList();
        ReservaLivro rl = lista.get(0);
        return rl;
    }
    
    public ReservaCd reservaCdMaisAntiga(String codCd) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT a FROM reserv_cd a"
                + " WHERE a.cd.id = '"+codCd+"'"
                + " AND a.recebida = 'false'"
                + " AND a.dataLimite IS NULL"
                + " AND a.dataDaReserva = (SELECT MIN(r.dataDaReserva)"
                + " FROM reserv_cd r) ORDER BY a.id";
        
        List<ReservaCd> lista = this.manager.createQuery(
                jpql, ReservaCd.class).getResultList();
        ReservaCd rc = lista.get(0);
        return rc;
    }
    
    public ReservaMonografia reservaMonogMaisAntiga(String codMonog) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT a FROM reserv_monog a"
                + " WHERE a.monografia.id = '"+codMonog+"'"
                + " AND a.recebida = 'false'"
                + " AND a.dataLimite IS NULL"
                + " AND a.dataDaReserva = (SELECT MIN(r.dataDaReserva)"
                + " FROM reserv_monog r) ORDER BY a.id";
        
        List<ReservaMonografia> lista = this.manager.createQuery(
                jpql, ReservaMonografia.class).getResultList();
        ReservaMonografia rl = lista.get(0);
        return rl;
    }
    
    public Integer qtdReservaLivroPorSolic(String codLivro, String codSolic) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT COUNT(a) FROM reserv_livro a "
                + "WHERE a.livro.id = '"+codLivro+"'"
                + "AND a.solicitante.pessoa.matricula = '"+codSolic+"'"
                + "AND a.recebida = 'false'"
                + "AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL)";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
   
   public Integer qtdReservaLivro(String codLivro) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT COUNT(a) FROM reserv_livro a "
                + "WHERE a.livro.id = '"+codLivro+"'"
                + "AND a.recebida = 'false'"
                + "AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL)";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
   
   
   public Integer qtdReservaCdPorSolic(String codCd, String codSolic) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT COUNT(a) FROM reserv_cd a "
                + "WHERE a.cd.id = '"+codCd+"'"
                + "AND a.solicitante.pessoa.matricula = '"+codSolic+"'"
                + "AND a.recebida = 'false'"
                + "AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL)";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
   
   public Integer qtdReservaCd(String codCd) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT COUNT(a) FROM reserv_cd a "
                + "WHERE a.cd.id = '"+codCd+"'"
                + "AND a.recebida = 'false'"
                + "AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL)";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }

   public Integer qtdReservaMonogPorSolic(String codMonog, String codSolic) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT COUNT(a) FROM reserv_monog a "
                + "WHERE a.monografia.id = '"+codMonog+"'"
                + "AND a.solicitante.pessoa.matricula = '"+codSolic+"'"
                + "AND a.recebida = 'false'"
                + "AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL)";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }
   
   public Integer qtdReservaMonog(String codMonog) {
        
        CalculoDatas cDatas = new CalculoDatas();
        String dataHoje = cDatas.dataAtual();
        
        String jpql = "SELECT COUNT(a) FROM reserv_monog a "
                + "WHERE a.monografia.id = '"+codMonog+"'"
                + "AND a.recebida = 'false'"
                + "AND (a.dataLimite >= '"+dataHoje+"'"
                + " OR a.dataLimite IS NULL)";
        
        Long qtdL = this.manager.createQuery(
                jpql, Long.class).getSingleResult();
        Integer qtd = Integer.parseInt(String.valueOf(qtdL));
        return qtd;
    }

}
