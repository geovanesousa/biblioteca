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
public class Dicionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Integer edicao;
    @Column(nullable = false, length = 50,name = "local_dicio")
    private String localDicionario;
    @Column(nullable = false, length = 50)
    private String titulo;
    @Column(nullable = false, length = 100)
    private String descricao;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "editora_id", updatable = true)
    private Editora editora;
    @Column(length = 4, nullable = false)
    private Integer ano;
    @Column(nullable = false, length = 50)
    private String classificacao;
    @Column(nullable = false, name = "qtd_paginas")
    private Integer quantidaDePaginas;

    public Dicionario(){
        this.editora = new Editora();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEdicao() {
        return edicao;
    }

    public void setEdicao(Integer edicao) {
        this.edicao = edicao;
    }

    public String getLocalDicionario() {
        return localDicionario;
    }

    public void setLocalDicionario(String localDicionario) {
        this.localDicionario = localDicionario;
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

    public Integer getQuantidaDePaginas() {
        return quantidaDePaginas;
    }

    public void setQuantidaDePaginas(Integer quantidaDePaginas) {
        this.quantidaDePaginas = quantidaDePaginas;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dicionario)) {
            return false;
        }
        Dicionario other = (Dicionario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.Dicionario[ id=" + id + " ]";
    }

}
