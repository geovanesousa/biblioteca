package br.com.fatene.biblioteca.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "prazo_multa")
public class PrazoMulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false,updatable = false,length = 15)
    private String categoria;
    @Column(nullable = false, length = 25, name = "tipo_material")
    private String tipoMaterial;
    @Column(nullable = false, name = "lmt_renovacao")
    private Integer limiteRenovacao;
    @Column(nullable = false, name = "lmt_titulos")
    private Integer limiteTitulos;
    @Column(nullable = false, name = "mlt_dia_titulo",scale = 2)
    private Double multaDiaTitulo;
    @Column(nullable = false, name = "dias_devolucao")
    private Integer diasDevolucao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Integer getLimiteRenovacao() {
        return limiteRenovacao;
    }

    public void setLimiteRenovacao(Integer limiteRenovacao) {
        this.limiteRenovacao = limiteRenovacao;
    }

    public Integer getLimiteTitulos() {
        return limiteTitulos;
    }

    public void setLimiteTitulos(Integer limiteTitulos) {
        this.limiteTitulos = limiteTitulos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getMultaDiaTitulo() {
        return multaDiaTitulo;
    }

    public void setMultaDiaTitulo(Double multaDiaTitulo) {
        this.multaDiaTitulo = multaDiaTitulo;
    }

    public Integer getDiasDevolucao() {
        return diasDevolucao;
    }

    public void setDiasDevolucao(Integer diasDevolucao) {
        this.diasDevolucao = diasDevolucao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PrazoMulta)) {
            return false;
        }
        PrazoMulta other = (PrazoMulta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.PrazoMulta[ id=" + id + " ]";
    }

}
