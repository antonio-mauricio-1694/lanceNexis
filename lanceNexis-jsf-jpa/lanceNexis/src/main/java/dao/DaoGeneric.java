package dao;

import br.com.jpaUtil.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoGeneric <E> {
	
	public void salvar (E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.persist(entidade);
		entityTransaction.commit();
		entityManager.close();
	}

	
	public E merge (E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		E retorno = entityManager.merge(entidade);
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
	}

}
