package hu.markgyori.game_4096.classes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import hu.markgyori.game_4096.model.UserData;

public class UserFactory {
	private EntityManagerFactory factory;

	private UserFactory() {
		factory = Persistence.createEntityManagerFactory("UserData");
	}
	
	public List<UserData> getUsers() {
		EntityManager em = factory.createEntityManager();
        try {
            Query q = em.createQuery("SELECT c FROM UserData c ORDER BY c.score DESC", UserData.class).setMaxResults(50);
            @SuppressWarnings("unchecked")
			List<UserData> data = q.getResultList();
            int index = 1;
            for(UserData d : data)
            	d.setIndex(index++);
            return data;
        } finally {
            em.close();
        }
    }
	
	public void addUserData(UserData data) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(data);
		em.getTransaction().commit();
		em.close();
	}
	
	public void close() {
		factory.close();
	}
	
	public static UserFactory getFactory() {
		return new UserFactory();
	}
}
