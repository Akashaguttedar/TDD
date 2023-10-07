package www.com.product.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import www.com.product.product.model.product;

public interface productrepository extends JpaRepository<product, Integer> {

	
	
	Optional<product> findByid(Integer id);
	
	
}
