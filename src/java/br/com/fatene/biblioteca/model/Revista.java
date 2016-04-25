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
public class Revista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = true, length = 50,name = "local_revista")
    private String localRevista;
    @Column(nullable = false)
    private Integer edicao;
    @Column(nullable = false, name = "qtd_paginas")
    private Integer quantidaDePaginas;
    @Column(nullable = false, length = 50)
    private String titulo;
    @Column(nullable = false, length = 100)
    private String descricao;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "editora_id", updatable = true)
    private Editora editora;
    @Column(nullable = false, length = 50)
    private String classificacao;
    @Column(length = 4, nullable = false)
    private Integer ano;

    public Revista(){
        this.editora = new Editora();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalRevista() {
        return localRevista;
    }

    public void setLocalRevista(String localRevista) {
        this.localRevista = localRevista;
    }

    public Integer getEdicao() {
        return edicao;
    }

    public void setEdicao(Integer edicao) {
        this.edicao = edicao;
    }

    public Integer getQuantidaDePaginas() {
        return quantidaDePaginas;
    }

    public void setQuantidaDePaginas(Integer quantidaDePaginas) {
        this.quantidaDePaginas = quantidaDePaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Revista)) {
            return false;
        }
        Revista other = (Revista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.Revista[ id=" + id + " ]";
    }

}
