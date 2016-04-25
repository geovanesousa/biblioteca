package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Monografia.class)
public abstract class Monografia_ {

	public static volatile SingularAttribute<Monografia, Integer> ano;
	public static volatile SingularAttribute<Monografia, String> localMonografia;
	public static volatile SingularAttribute<Monografia, Boolean> bloqueado;
	public static volatile SingularAttribute<Monografia, String> titulo;
	public static volatile SingularAttribute<Monografia, String> classificacao;
	public static volatile SingularAttribute<Monografia, Long> id;
	public static volatile SingularAttribute<Monografia, Integer> quantidaDePaginas;
	public static volatile SingularAttribute<Monografia, String> descricao;

}

