package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Revista.class)
public abstract class Revista_ {

	public static volatile SingularAttribute<Revista, Integer> ano;
	public static volatile SingularAttribute<Revista, String> localRevista;
	public static volatile SingularAttribute<Revista, String> titulo;
	public static volatile SingularAttribute<Revista, String> classificacao;
	public static volatile SingularAttribute<Revista, Long> id;
	public static volatile SingularAttribute<Revista, Integer> edicao;
	public static volatile SingularAttribute<Revista, Editora> editora;
	public static volatile SingularAttribute<Revista, Integer> quantidaDePaginas;
	public static volatile SingularAttribute<Revista, String> descricao;

}

