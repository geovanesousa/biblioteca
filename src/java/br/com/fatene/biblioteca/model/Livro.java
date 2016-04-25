package br.com.fatene.biblioteca.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private Integer edicao;
    
    @OneToOne
    @JoinColumn(name = "editora_id", updatable = true)
    private Editora editora;
    
    @Column(nullable = false)
    private Boolean bloqueado;
    
    @Column(length = 15, nullable = false, unique = true)
    private String isbn;
    
    @Column(nullable = false, name = "qtd_paginas")
    private Integer quantidaDePaginas;
    
    @Column(length = 4, nullable = false)
    private Integer ano;
    
    @Column(nullable = false)
    private Boolean braille;
    
    @Column(nullable = false, length = 100)
    private String descricao;
    
    @Column(nullable = false, length = 50,name = "local_livro")
    private String localLivro;
    
    @Column(nullable = false, length = 50)
    private String titulo;
    
    @Column(nullable = false, length = 50)
    private String classificacao;

    public Livro(){
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

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public Boolean getBraille() {
        return braille;
    }

    public void setBraille(Boolean braille) {
        this.braille = braille;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalLivro() {
        return localLivro;
    }

    public void setLocalLivro(String localLivro) {
        this.localLivro = localLivro;
    }

    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fatene.biblioteca.model.Livro[ id=" + id + " ]";
    }

}
