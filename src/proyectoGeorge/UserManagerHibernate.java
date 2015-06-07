package proyectoGeorge;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserManagerHibernate{
	private EntityManager _em;
	
	public UserManagerHibernate( EntityManager em ){
		_em = em;
	}
	public void updateUser(User u) {
		_em.persist(u);
	}

	public void insertUser(User u) {
		_em.persist(u);
	}

	public void deleteUser(User u) {
		_em.remove(u);
	}

	public User findByUserName(User u) {
		TypedQuery<User> q = _em.createQuery("from User where USERNAME=:id", User.class);
		q.setParameter("id", u.getName());
		List<User> resultList = q.getResultList();
		if(resultList.isEmpty()){
			return null;
		}
		return resultList.get(0);
	}
	
	public List<User> findAllUsers() {
		TypedQuery<User> q = _em.createQuery("from User", User.class);
		List<User> resultList = q.getResultList();
		return resultList;
	}
}
