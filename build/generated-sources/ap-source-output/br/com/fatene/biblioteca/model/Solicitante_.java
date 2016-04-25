package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Solicitante.class)
public abstract class Solicitante_ {

	public static volatile SingularAttribute<Solicitante, String> situacao;
	public static volatile SingularAttribute<Solicitante, Pessoa> pessoa;
	public static volatile SingularAttribute<Solicitante, String> categoria;
	public static volatile SingularAttribute<Solicitante, Long> id;

}

