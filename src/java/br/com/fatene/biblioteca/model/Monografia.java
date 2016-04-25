package br.com.fatene.biblioteca.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Monografia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "qtd_paginas")
    private Integer quantidaDePaginas;
    @Column(length = 4, nullable = false)
    private Integer ano;
    @Column(nullable = false, length = 50)
    private String classificacao;
    @Column(nullable = false, length = 50)
    private String titulo;
    @Column(nullable = false, length = 50,name = "local_monog")
    private String localMonografia;
    @Column(nullable = false)
    private Boolean bloqueado;
    @Column(nullable = false, length = 100)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidaDePaginas() {
        return quantidaDePaginas;
    }

    public void setQuantidaDePaginas(Integer quantidaDePaginas) {
        this.quantidaDePaginas = quantidaDePaginas;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocalMonografia() {
        return localMonografia;
    }

    public void setLocalMonografia(String localMonografia) {
        this.localMonografia = localMonografia;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Monografia)) {
            return false;
        }
        Monografia other = (Monografia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.Monografia[ id=" + id + " ]";
    }

}
