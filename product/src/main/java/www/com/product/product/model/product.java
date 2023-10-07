package www.com.product.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "product")
public class product {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private int quantity;
	
	private int version;

	
	
	
	
	
	
	
	public product() {
		super();
	}

	public product(int id, String name, int quantity, int version) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "product [id=" + id + ", name=" + name + ", quantity=" + quantity + ", version=" + version + "]";
	}
	
	
	
	
}
