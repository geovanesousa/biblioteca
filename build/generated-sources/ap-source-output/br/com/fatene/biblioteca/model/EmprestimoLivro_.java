package br.com.fatene.biblioteca.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmprestimoLivro.class)
public abstract class EmprestimoLivro_ {

	public static volatile SingularAttribute<EmprestimoLivro, Integer> quantidadeRenovacoes;
	public static volatile SingularAttribute<EmprestimoLivro, Emprestimo> emprestimo;
	public static volatile SingularAttribute<EmprestimoLivro, ExemplarLivro> exemplarLivro;
	public static volatile SingularAttribute<EmprestimoLivro, Calendar> previsaoDataDevolucao;
	public static volatile SingularAttribute<EmprestimoLivro, Long> id;
	public static volatile SingularAttribute<EmprestimoLivro, Boolean> pendente;
	public static volatile SingularAttribute<EmprestimoLivro, Calendar> dataDevolucao;

}

