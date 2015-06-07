package proyectoGeorge;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="USER_TABLE",
uniqueConstraints = {@UniqueConstraint(columnNames={"USERNAME"})}
)
public class User {
	@Id @Column public String userName;
	@Column private String passwordString;
	@Column private boolean admin;
	@Lob private byte[] image;
	@Transient private char[] password;
	@OneToMany(mappedBy="buyer")
	private List<Order> orders;
	
	public List<Order> getOrders() {
		if (orders == null) {
			orders=new ArrayList<Order>();
			
		}

		return orders;
	}
	
	public void addOrder( Order o){
		if( o.getUser() != this ){
			o.setUser(this);
		}
		if( !getOrders().contains(o) ){
			getOrders().add(o);
		}
	}
	
	public void removeOrder( Order o){
		getOrders().remove(o);
		o.setUser(null);
	}
	
	public void setOrders(List<Order> orders) {
		this.orders=orders;
	}
	
	public String getName(){
		return this.userName;
	}
	protected void setName(String newName){
		this.userName=newName;
	}
	
	public String getPasswordString(){
		return passwordString;
	}
	
	protected char[] getPasswordChars(){
		return this.password;
	}
	public void setPasswordCharFromString(String pass){
		char[] passChars=pass.toCharArray();
		this.password=passChars;
	}
	protected void setPassword(String pass){
		this.passwordString=pass;
	}
	protected boolean isAdmin() {
		return admin;
	}
	protected void setAdmin(boolean admin) {
		this.admin = admin;
	}
	protected void setImage(byte[] newImage){
		this.image=newImage;
	}
	public byte[] getImage(){
		return this.image;
	}
	@Override
	public String toString(){
		String no="No";
		String yes="Yes";
		if (admin) {
			return "Username: "+userName+" Admin:"+yes;
		}
		else{
		return "Username:"+userName+" Admin:"+no;
		}
	}
}