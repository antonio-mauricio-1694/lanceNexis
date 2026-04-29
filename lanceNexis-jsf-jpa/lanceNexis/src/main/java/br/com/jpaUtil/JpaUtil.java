package br.com.jpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
	
	
	public static EntityManagerFactory factory = null;
	
	static {
		
		if(factory ==  null) {
			factory = Persistence.createEntityManagerFactory("lanceNexis");
		}
		
		
	}
	
	 public static EntityManager getEntityManager() {
		  return factory.createEntityManager();
	 }
	 
	 public static Object getPrimaryKey(Object entity) {
		 
		 return factory.getPersistenceUnitUtil().getIdentifier(entity);
		 
	 }

}
