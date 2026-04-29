package dao;

import java.util.List;

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
	public void deletePorId(E entidade, Object id) {
	    EntityManager entityManager = JpaUtil.getEntityManager();
	    EntityTransaction entityTransaction = entityManager.getTransaction();
	    try {
	        entityTransaction.begin();
	        entityManager.createQuery(
	            "DELETE FROM " + entidade.getClass().getSimpleName() + " e WHERE e.id = :id"
	        )
	        .setParameter("id", id)
	        .executeUpdate();
	        entityTransaction.commit();
	    } catch (Exception e) {
	        if (entityTransaction.isActive()) {
	            entityTransaction.rollback();
	        }
	        throw e;
	    } finally {
	        entityManager.close();
	    }
	}
	
	public List<E> getListEntity(Class<E> entidade) {

	    EntityManager entityManager = JpaUtil.getEntityManager();
	    List<E> retorno;

	    try {
	        retorno = entityManager
	                .createQuery("FROM " + entidade.getSimpleName(), entidade)
	                .getResultList();
	    } finally {
	        entityManager.close(); // fecha DEPOIS
	    }

	    return retorno;
	}
	

}
