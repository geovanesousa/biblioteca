package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReservaCd.class)
public abstract class ReservaCd_ {

	public static volatile SingularAttribute<ReservaCd, Cd> cd;
	public static volatile SingularAttribute<ReservaCd, Boolean> recebida;
	public static volatile SingularAttribute<ReservaCd, Solicitante> solicitante;
	public static volatile SingularAttribute<ReservaCd, Calendar> dataLimite;
	public static volatile SingularAttribute<ReservaCd, Long> id;
	public static volatile SingularAttribute<ReservaCd, Calendar> dataDaReserva;

}

