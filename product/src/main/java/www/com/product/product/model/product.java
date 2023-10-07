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
	private Integer id;
	
	private String name;
	
	private Integer quantity;
	
	private Integer version;

	
	
	
	
	
	
	
	public product() {
		super();
	}

	public product(Integer id, String name, Integer quantity, Integer version) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "product [id=" + id + ", name=" + name + ", quantity=" + quantity + ", version=" + version + "]";
	}
	
	
	
	
}
