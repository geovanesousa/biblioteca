package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cd.class)
public abstract class Cd_ {

	public static volatile SingularAttribute<Cd, Integer> duracaoMinutos;
	public static volatile SingularAttribute<Cd, Boolean> bloqueado;
	public static volatile SingularAttribute<Cd, String> titulo;
	public static volatile SingularAttribute<Cd, String> classificacao;
	public static volatile SingularAttribute<Cd, Long> id;
	public static volatile SingularAttribute<Cd, Editora> editora;
	public static volatile SingularAttribute<Cd, String> codigoBarras;
	public static volatile SingularAttribute<Cd, String> descricao;

}

