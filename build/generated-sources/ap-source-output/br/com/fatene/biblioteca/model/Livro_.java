package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Livro.class)
public abstract class Livro_ {

	public static volatile SingularAttribute<Livro, Integer> ano;
	public static volatile SingularAttribute<Livro, String> isbn;
	public static volatile SingularAttribute<Livro, Boolean> bloqueado;
	public static volatile SingularAttribute<Livro, String> titulo;
	public static volatile SingularAttribute<Livro, String> classificacao;
	public static volatile SingularAttribute<Livro, Long> id;
	public static volatile SingularAttribute<Livro, Integer> edicao;
	public static volatile SingularAttribute<Livro, Editora> editora;
	public static volatile SingularAttribute<Livro, Integer> quantidaDePaginas;
	public static volatile SingularAttribute<Livro, Boolean> braille;
	public static volatile SingularAttribute<Livro, String> descricao;
	public static volatile SingularAttribute<Livro, String> localLivro;

}

