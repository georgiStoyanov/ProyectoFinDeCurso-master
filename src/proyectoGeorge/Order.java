package proyectoGeorge;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
//////----------------------->ORDER IS NOW REQUEST IN THE SHOP<-------------------------
@Entity
@Table(name="ORDER_TABLE")
public class Order {
	@Id@GeneratedValue(strategy = GenerationType.AUTO)
	private int idOrder;

	@ManyToOne @JoinColumn(name="orders")
	private User buyer;
	@ManyToOne @JoinColumn(name="orderedWeapons")
	private Weapon ordered;

	private int orderSize;
	public Weapon getWeapon(){
		return this.ordered;
	}
	public int getId(){
		return this.idOrder;
	}
	public void setWeapon(Weapon w){
		this.ordered=w;
	}
	public User getUser(){
		return this.buyer;
	}

	public void setUser(User u){
		this.buyer=u;
	}
	protected int getOrderSize() {
		return this.orderSize;
	}
	protected void setOrderSize(int newSize) {
		this.orderSize=newSize;
	}
	
	@Override
	public String toString() {
		return orderSize+":"+ordered.getBrand()+" "+ordered.getModel();
	}

}
