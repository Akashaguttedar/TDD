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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		Mockito.doReturn(Optional.of(mockproduct)).when(productservic).findbid(1);
		productservic.save(mockproduct);
		mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1))
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"1996\""))
		.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/product/1"))
		       .andExpect(MockMvcResultMatchers.status().isOk())
		       .andDo(MockMvcResultHandlers.print())
		       .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("akash"))
		       .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(20))
		       .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(1996));
		       
		
	}
	
	
	
	 @Test()
	 @DisplayName("GET /Product/1- NOT FOUND")
     void testgetproductnotfound() throws Exception {
		 
		 Mockito.doReturn(Optional.empty()).when(productservic).findbid(1);
		 mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}",1))
		 .andExpect(MockMvcResultMatchers.status().isNotFound());
		 
     }
	
	 
	 
	 static String asjsonString(final Object ob) {
		 try {
			 return new ObjectMapper().writeValueAsString(ob);
		 }catch (Exception e) {
			throw new RuntimeException();
		}
	 }
	
	 
	 @Test
	 @DisplayName("POST /product -sucess")
	 void testcreatproduct() throws Exception {
		 product postroduct=new product(1, "akash", 20, 1996);
		 product mockproduct=new product(2, "Khadar", 20, 1995);
		 Mockito.doReturn(mockproduct).when(productservic).save(Mockito.any());
		 mockMvc.perform(MockMvcRequestBuilders.post("/product")
				                               .contentType(MediaType.APPLICATION_JSON)
				                               .content(asjsonString(mockproduct)))
		                  //validate the product response code
		                  .andExpect(MockMvcResultMatchers.status().isCreated())
		                  .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
					      .andDo(MockMvcResultHandlers.print())
					      
					      //validate header
					      .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"1995\""))
					      
					      // validate the return field value
					      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Khadar"))
					      .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(20))
					      .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(1995));
		 
	 }
	 
	 
	 @Test
	 @DisplayName("PUT /product/1 -sucess")
	 void testupdatedproduct() throws Exception {
		 product postroduct=new product(1, "akash", 20, 1996);
		 product mockproduct=new product(2, "Khadar", 20, 1995);
		 Mockito.doReturn(Optional.of(mockproduct)).when(productservic).findbid(2);
		 Mockito.doReturn(true).when(productservic).updated(Mockito.any());
		 mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}",2)
				                               .contentType(MediaType.APPLICATION_JSON)
				                               .header(HttpHeaders.IF_MATCH, 1995)
				                               .content(asjsonString(mockproduct)))
		                  //validate the product response code
		                  .andExpect(MockMvcResultMatchers.status().isCreated())
		                  .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
					      .andDo(MockMvcResultHandlers.print())
					      
					      //validate header
					      .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"1996\""))
					      
					      // validate the return field value
					      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Khadar"))
					      .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(20))
					      .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(1996));
		 
	 }
	 
	 
	 @Test
	 @DisplayName("PUT /product/1 - version mismatch")
	 void testupdatedproductversion() throws Exception {
		 product postroduct=new product(1, "akash", 20, 1996);
		 product mockproduct=new product(2, "Khadar", 20, 1995);
		 Mockito.doReturn(Optional.of(mockproduct)).when(productservic).findbid(2);
		 Mockito.doReturn(true).when(productservic).updated(Mockito.any());
		 mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}",2)
				                               .contentType(MediaType.APPLICATION_JSON)
				                               .header(HttpHeaders.IF_MATCH, 1998)
				                               .content(asjsonString(mockproduct)))
		                  //validate the product response code
		                 .andExpect(MockMvcResultMatchers.status().isConflict());
		                  
					   
					      
					      
		 
	 }
	 
	 
	 
	 @Test
	 @DisplayName("DELETE /product/1- sucess")
	 void deleteproduct() {
		 
		 product mockproduct=new product(2, "Khadar", 20, 1995);
		 Mockito.doReturn(Optional.of(mockproduct)).when(productservic).findbid(2);
		 Mockito.doReturn(true).when(productservic).delete(2);
		 
		 
		 
		 
		 
	 }
	 
	 
	 

}
