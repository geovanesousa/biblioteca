package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Emprestimo.class)
public abstract class Emprestimo_ {

	public static volatile SingularAttribute<Emprestimo, LogAcesso> logAcesso;
	public static volatile SingularAttribute<Emprestimo, Calendar> dataEmprestimo;
	public static volatile SingularAttribute<Emprestimo, Solicitante> solicitante;
	public static volatile SingularAttribute<Emprestimo, Double> multa;
	public static volatile SingularAttribute<Emprestimo, Long> id;
	public static volatile SingularAttribute<Emprestimo, Date> horaEmprestimo;

}

