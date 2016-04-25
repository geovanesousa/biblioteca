package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExemplarDicionario.class)
public abstract class ExemplarDicionario_ {

	public static volatile SingularAttribute<ExemplarDicionario, Integer> quantidadeEstoque;
	public static volatile SingularAttribute<ExemplarDicionario, Long> id;
	public static volatile SingularAttribute<ExemplarDicionario, Dicionario> dicionario;

}

