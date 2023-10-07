package www.com.product.product.service;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.stereotype.Service;

import www.com.product.product.model.product;
import www.com.product.product.repository.productrepository;

@Service
public class Productservice implements productserviceinterface {

	
	
	private final productrepository prepository;
	
	
	
	public Productservice(productrepository prepository) {
		
		this.prepository = prepository;
	}

	@Override
	public Optional<product> findbid(Integer id) {
		Optional<product> findByid = prepository.findByid(id);
		return findByid;
	}

	@Override
	public List<product> findAll() {
		List<product> findAll = prepository.findAll();
		return findAll;
	}

	@Override
	public boolean updated(product product) {
	    product save = prepository.save(product);
		return true;
	}

	@Override
	public product save(product product) {
		product pr =prepository.save(product);
		return pr;
	}

	@Override
	public boolean delete(Integer id) {
		prepository.deleteById(id);
		return true;
	}

	
	
	
	
	
	
	
}
