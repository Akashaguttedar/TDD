package www.com.product.product.service;

import java.util.List;
import java.util.Optional;

import www.com.product.product.model.product;

public interface productserviceinterface {

	
	
    Optional<product> findbid(Integer id);
    
    
    List<product> findAll();
    
    
    boolean updated(product product);
    
    
    product save(product product);
    
    
    boolean delete(Integer id);
}
