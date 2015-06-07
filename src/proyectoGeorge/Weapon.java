package proyectoGeorge;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WEAPON_TABLE")
public class Weapon {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long idWeapon;
	
	@Column private String model;
	@Column private String brand;
	@Column private String category;
	@Column private double price;
	@Column private boolean active;
//	@Column private int ordered;
//	quantity must be replaced by another table named STOCK_WEAPON with connection OneToOne
	@Column private int quantity;
	@OneToMany(mappedBy="ordered")
	private List<Order> orderedWeapons;
	
	public List<Order> getOrders() {
		if (orderedWeapons == null) {
			orderedWeapons =new ArrayList<Order>();
			
		}

		return orderedWeapons;
	}
	
	public void addOrder( Order o){
		if( o.getWeapon()!= this ){
			o.setWeapon(this);
		}
		if( !getOrders().contains(o) ){
			getOrders().add(o);
		}
	}
	
	public void removeOrder( Order o){
		getOrders().remove(o);
		o.setUser(null);
	}
	public String getCategory(){
		return this.category;
	}
	protected void setCategory(String newCategory){
		this.category=newCategory;
	}
	public String getModel(){
		return this.model;
	}
	protected void setModel(String newModel){
		this.model=newModel;
	}
	public double getPrice(){
		return this.price;
	}
	protected void setPrice(double newPrice){
		this.price=newPrice;
	}
	public String getBrand(){
		return this.brand;
	}
	protected void setBrand(String newBrand){
		this.brand=newBrand;
	}
	public boolean isActive(){
		return this.active;
	}
	public void setActive(boolean _active){
		this.active=_active;
	}
	protected void setType(boolean newActive){
		this.active=newActive;
	}
	protected long getIdWeapon(){
		return this.idWeapon;
	}
	protected void setIdWeapn(long _id){
		this.idWeapon=_id;
	}
	protected int getQuantity() {
		return this.quantity;
	}
	protected void setQuantity(int newCuantity){
		this.quantity=newCuantity;
	}
//	protected int getOrderedCount() {
//		return this.ordered;
//	}
//	protected void setOrderedCount(int newFavorites){
//		this.ordered=newFavorites;
//	}
	@Override
	public String toString(){
		return (brand+" "+model+" "+price+" $"+ " Available:"+quantity);
	}
}
