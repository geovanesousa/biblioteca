package br.com.fatene.biblioteca.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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

@Entity
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIME)
    @Column(nullable = false, updatable = false, name = "hora_emprestimo")
    private Date horaEmprestimo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(updatable = false, nullable = false, name = "solicitante_id")
    private Solicitante solicitante;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(updatable = false, nullable = false, name = "log_acesso_id")
    private LogAcesso logAcesso;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false, name = "data_emprestimo")
    private Calendar dataEmprestimo;
    @Column(scale = 2)
    private Double multa;

    public Emprestimo(){
        this.logAcesso = new LogAcesso();
        this.solicitante = new Solicitante();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHoraEmprestimo() {
        return horaEmprestimo;
    }

    public void setHoraEmprestimo(Date horaEmprestimo) {
        this.horaEmprestimo = horaEmprestimo;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public LogAcesso getLogAcesso() {
        return logAcesso;
    }

    public void setLogAcesso(LogAcesso logAcesso) {
        this.logAcesso = logAcesso;
    }

    public Calendar getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Calendar dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
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
        if (!(object instanceof Emprestimo)) {
            return false;
        }
        Emprestimo other = (Emprestimo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.Emprestimo[ id=" + id + " ]";
    }

}
