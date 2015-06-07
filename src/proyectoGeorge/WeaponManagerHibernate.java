package proyectoGeorge;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class WeaponManagerHibernate {
private EntityManager _em;
	
	public WeaponManagerHibernate( EntityManager em ){
		_em = em;
	}
	public void updateWeapon(Weapon w) {
		_em.persist(w);
	}

	public void insertWeapon(Weapon w) {
		_em.persist(w);
	}

	public void deleteWeapon(Weapon w) {
		_em.remove(w);
	}
	public Weapon findByPrimaryKey(Weapon w)  {
		TypedQuery<Weapon> q = _em.createQuery("from Weapon where idWeapon=:id", Weapon.class);
		q.setParameter("id", w.getIdWeapon() );
		List<Weapon> resultList = q.getResultList();
		return resultList.get(0);
	}
	
	public Weapon findByCategory(Weapon w)  {
		TypedQuery<Weapon> q = _em.createQuery("from Weapon where category=:category", Weapon.class);
		q.setParameter("category", w.getCategory() );
		List<Weapon> resultList = q.getResultList();
		return resultList.get(0);
	}
	public Weapon findByBrand(Weapon w)  {
		TypedQuery<Weapon> q = _em.createQuery("from Weapon where brand=:brand", Weapon.class);
		q.setParameter("brand", w.getBrand());
		List<Weapon> resultList = q.getResultList();
		return resultList.get(0);
	}
	public Weapon findByModel(Weapon w)  {
		TypedQuery<Weapon> q = _em.createQuery("from Weapon where model=:model", Weapon.class);
		q.setParameter("model", w.getModel() );
		List<Weapon> resultList = q.getResultList();
		return resultList.get(0);
	}
	
	public List<Weapon> findAll() {
		TypedQuery<Weapon> q = _em.createQuery("from Weapon", Weapon.class);
		List<Weapon> resultList = q.getResultList();
		return resultList;
	}
}
