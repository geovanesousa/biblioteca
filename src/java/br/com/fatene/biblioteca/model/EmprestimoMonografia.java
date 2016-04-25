package br.com.fatene.biblioteca.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "emp_monog")
public class EmprestimoMonografia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emprest_id", nullable = false, updatable = false)
    private Emprestimo emprestimo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "ex_monog_id", updatable = false)
    private ExemplarMonografia exemplarMonografia;
    @Column(nullable = false)
    private Boolean pendente;
    @Column(nullable = false, name = "qtd_renov")
    private Integer quantidadeRenovacoes;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_devolucao")
    private Calendar dataDevolucao;
    @Temporal(TemporalType.DATE)
    @Column(name = "prev_data_dev", nullable = false)
    private Calendar previsaoDataDevolucao;
    @Transient
    private Double multa = 0.0;
    
    
    public EmprestimoMonografia(){
        this.emprestimo = new Emprestimo();
        this.exemplarMonografia = new ExemplarMonografia();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public ExemplarMonografia getExemplarMonografia() {
        return exemplarMonografia;
    }

    public void setExemplarMonografia(ExemplarMonografia exemplarMonografia) {
        this.exemplarMonografia = exemplarMonografia;
    }

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public Integer getQuantidadeRenovacoes() {
        return quantidadeRenovacoes;
    }

    public void setQuantidadeRenovacoes(Integer quantidadeRenovacoes) {
        this.quantidadeRenovacoes = quantidadeRenovacoes;
    }

    public Calendar getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Calendar dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Calendar getPrevisaoDataDevolucao() {
        return previsaoDataDevolucao;
    }

    public void setPrevisaoDataDevolucao(Calendar previsaoDataDevolucao) {
        this.previsaoDataDevolucao = previsaoDataDevolucao;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EmprestimoMonografia)) {
            return false;
        }
        EmprestimoMonografia other = (EmprestimoMonografia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.EmprestimoCd[ id=" + id + " ]";
    }

}
