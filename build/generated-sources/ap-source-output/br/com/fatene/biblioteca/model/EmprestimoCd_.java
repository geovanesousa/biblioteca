package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmprestimoCd.class)
public abstract class EmprestimoCd_ {

	public static volatile SingularAttribute<EmprestimoCd, Integer> quantidadeRenovacoes;
	public static volatile SingularAttribute<EmprestimoCd, ExemplarCd> exemplarCd;
	public static volatile SingularAttribute<EmprestimoCd, Emprestimo> emprestimo;
	public static volatile SingularAttribute<EmprestimoCd, Calendar> previsaoDataDevolucao;
	public static volatile SingularAttribute<EmprestimoCd, Long> id;
	public static volatile SingularAttribute<EmprestimoCd, Boolean> pendente;
	public static volatile SingularAttribute<EmprestimoCd, Calendar> dataDevolucao;

}

