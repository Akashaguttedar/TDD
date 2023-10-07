package www.com.product.product;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import www.com.product.product.model.product;
import www.com.product.product.repository.productrepository;
import www.com.product.product.service.productserviceinterface;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private productserviceinterface productservice;
	
	@MockBean
	private productrepository repository;
	
	
	@Test
	@DisplayName("Test Find BY Id Sucess")
	void testfindbyid() {
		
		 product mockproduct=new product(1, "Akasha", 20, 1995);
		 Mockito.doReturn(Optional.of(mockproduct)).when(repository).findById(1);
		 
		 Optional<product> findbid = productservice.findbid(2);
		 
		 Assertions.assertTrue(findbid.isPresent(),"Product was not Found");
		 Assertions.assertSame(findbid.get(),mockproduct,"Product was not Found");
		 
		 
		 
		
		
		
	}
}
