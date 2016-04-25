package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReservaLivro.class)
public abstract class ReservaLivro_ {

	public static volatile SingularAttribute<ReservaLivro, Boolean> recebida;
	public static volatile SingularAttribute<ReservaLivro, Livro> livro;
	public static volatile SingularAttribute<ReservaLivro, Solicitante> solicitante;
	public static volatile SingularAttribute<ReservaLivro, Calendar> dataLimite;
	public static volatile SingularAttribute<ReservaLivro, Long> id;
	public static volatile SingularAttribute<ReservaLivro, Calendar> dataDaReserva;

}

