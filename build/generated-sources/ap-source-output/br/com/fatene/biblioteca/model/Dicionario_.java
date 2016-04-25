package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dicionario.class)
public abstract class Dicionario_ {

	public static volatile SingularAttribute<Dicionario, String> localDicionario;
	public static volatile SingularAttribute<Dicionario, Integer> ano;
	public static volatile SingularAttribute<Dicionario, String> titulo;
	public static volatile SingularAttribute<Dicionario, String> classificacao;
	public static volatile SingularAttribute<Dicionario, Long> id;
	public static volatile SingularAttribute<Dicionario, Integer> edicao;
	public static volatile SingularAttribute<Dicionario, Editora> editora;
	public static volatile SingularAttribute<Dicionario, Integer> quantidaDePaginas;
	public static volatile SingularAttribute<Dicionario, String> descricao;

}

