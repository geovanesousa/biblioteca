package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReservaMonografia.class)
public abstract class ReservaMonografia_ {

	public static volatile SingularAttribute<ReservaMonografia, Boolean> recebida;
	public static volatile SingularAttribute<ReservaMonografia, Monografia> monografia;
	public static volatile SingularAttribute<ReservaMonografia, Solicitante> solicitante;
	public static volatile SingularAttribute<ReservaMonografia, Calendar> dataLimite;
	public static volatile SingularAttribute<ReservaMonografia, Long> id;
	public static volatile SingularAttribute<ReservaMonografia, Calendar> dataDaReserva;

}

