package www.com.product.product;

import java.util.Optional;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import www.com.product.product.model.product;
import www.com.product.product.service.productserviceinterface;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductApplicationTests {

	@MockBean
	private productserviceinterface productservic;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@DisplayName("GET /product/ - Found")
	void testGetProductByIdFound() throws Exception {
		product mockproduct=new product(1, "akash", 20, 1996);
		System.out.println("1");
		Mockito.doReturn(Optional.of(mockproduct)).when(productservic).findbid(1);
		System.out.println("2");
		productservic.save(mockproduct);
		mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1))
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"1996\""))
		.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/product/1"))
		       .andExpect(MockMvcResultMatchers.status().isOk())
		       .andDo(MockMvcResultHandlers.print())
		       .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("akash"));
		       
		
	}
	
	
	
	 @Test()
	 @DisplayName("GET /Product/1- NOT FOUND")
     void testgetproductnotfound() throws Exception {
		 
		 Mockito.doReturn(Optional.empty()).when(productservic).findbid(1);
		 mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1))
		 .andExpect(MockMvcResultMatchers.status().isNotFound());
		 
     }
	
	

}
