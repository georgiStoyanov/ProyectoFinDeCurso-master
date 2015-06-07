package proyectoGeorge;
//////----------------------->ORDER IS NOW REQUEST IN THE SHOP<-------------------------
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class OrderManagerHibernate {
	private EntityManager _em;
	public OrderManagerHibernate(EntityManager em) {
		this._em=em;
	}
		
	public void insertOrder(Order o) {
		_em.persist(o);
	}
	public void updateOrder(Order o) {
		_em.persist(o);
	}

	public void deleteOrder(Order o) {
		_em.remove(o);
	}
	public List<Order> findAllOrders() {
		TypedQuery<Order> q = _em.createQuery("from Order", Order.class);
		List<Order> resultList = q.getResultList();
		return resultList;
	}
}
