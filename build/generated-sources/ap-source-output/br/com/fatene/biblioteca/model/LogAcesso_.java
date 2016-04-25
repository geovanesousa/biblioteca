package br.com.fatene.biblioteca.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LogAcesso.class)
public abstract class LogAcesso_ {

	public static volatile SingularAttribute<LogAcesso, String> nomeDispositivo;
	public static volatile SingularAttribute<LogAcesso, Usuario> usuario;
	public static volatile SingularAttribute<LogAcesso, Long> id;

}

