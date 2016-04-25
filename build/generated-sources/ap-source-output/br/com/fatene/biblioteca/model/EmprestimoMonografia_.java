package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmprestimoMonografia.class)
public abstract class EmprestimoMonografia_ {

	public static volatile SingularAttribute<EmprestimoMonografia, Integer> quantidadeRenovacoes;
	public static volatile SingularAttribute<EmprestimoMonografia, Emprestimo> emprestimo;
	public static volatile SingularAttribute<EmprestimoMonografia, Calendar> previsaoDataDevolucao;
	public static volatile SingularAttribute<EmprestimoMonografia, Long> id;
	public static volatile SingularAttribute<EmprestimoMonografia, Boolean> pendente;
	public static volatile SingularAttribute<EmprestimoMonografia, Calendar> dataDevolucao;
	public static volatile SingularAttribute<EmprestimoMonografia, ExemplarMonografia> exemplarMonografia;

}

