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

@Entity(name = "reserv_monog")
public class ReservaMonografia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "solic_id", nullable = false, updatable = false)
    private Solicitante solicitante;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monog_id", nullable = false, updatable = false)
    private Monografia monografia;
    @Column(nullable = false)
    private Boolean recebida;
    @Temporal(TemporalType.DATE)
    @Column(name = "dt_reserva",nullable = false)
    private Calendar dataDaReserva;
    @Temporal(TemporalType.DATE)
    @Column(name = "dt_limite")
    private Calendar dataLimite;
    
    public ReservaMonografia(){
        this.monografia = new Monografia();
        this.solicitante = new Solicitante();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Monografia getMonografia() {
        return monografia;
    }

    public void setMonografia(Monografia monografia) {
        this.monografia = monografia;
    }
    
    public Calendar getDataDaReserva() {
        return dataDaReserva;
    }

    public void setDataDaReserva(Calendar dataDaReserva) {
        this.dataDaReserva = dataDaReserva;
    }

    public Calendar getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Calendar dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Boolean getRecebida() {
        return recebida;
    }

    public void setRecebida(Boolean recebida) {
        this.recebida = recebida;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ReservaMonografia)) {
            return false;
        }
        ReservaMonografia other = (ReservaMonografia) object;
        if ((this.id == null && other.id != null) || (this.id != null && 
                !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.ReservaLivro[ id=" + id + " ]";
    }
    
}
