package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExemplarLivro.class)
public abstract class ExemplarLivro_ {

	public static volatile SingularAttribute<ExemplarLivro, Livro> livro;
	public static volatile SingularAttribute<ExemplarLivro, Integer> quantidadeEstoque;
	public static volatile SingularAttribute<ExemplarLivro, Long> id;

}

