package www.com.product.product.controller;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import www.com.product.product.model.product;
import www.com.product.product.service.productserviceinterface;




@RestController
public class productcontroller {

	private static final Logger logger=LogManager.getLogger(productcontroller.class);
	
	private final productserviceinterface productservice;

	public productcontroller(productserviceinterface productservice) {
		this.productservice = productservice;
	}
	
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getproduct(@PathVariable Integer id){
		   Optional<product> findbid = productservice.findbid(id);
	return	findbid.map(p->{
			try {
				return ResponseEntity.ok()
						.eTag(Integer.toString(p.getVersion()))
						.location(new URI("/product/" + p.getId()))
						.body(p);
			}catch(URISyntaxException ee){
			   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}).orElse(ResponseEntity.notFound().build());
		
		
	
	}
	
	@GetMapping("/product")
	public ResponseEntity<?> getall(){
		List<product> findAll = productservice.findAll();
		return ResponseEntity.ok(findAll);
	}
	
	
	@PostMapping("/product")
	public ResponseEntity<product> createproduct(@RequestBody product product){
		logger.info("Creating new product with name:{},quantity:{}",product.getName(),product.getQuantity());
		product save = productservice.save(product);
			try {
				return ResponseEntity
						.created(new URI("/product/"+product.getId()))
						.eTag(Integer.toString(product.getVersion()))
						.body(product);
			}catch(URISyntaxException ee){
			   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		
		
		
		
	}
	
	
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> updatedproduct(@RequestBody product product,
			                                @PathVariable("id") Integer id,
			                                @RequestHeader("If-Match") Integer ifmatch){
		
	System.out.println(ifmatch);
	 logger.info("Creating new product with name:{},quantity:{}",product.getName(),product.getQuantity());
		System.out.println(ifmatch);
	  Optional<product> findbid = productservice.findbid(id);
	  
	 return findbid.map(p -> {
		 //compare the tag
		  logger.info("Product With ID: "+ id + "has version of " + p.getVersion()
		           + "Updated is for If-Match: " + ifmatch);
		  if(!p.getVersion().equals(ifmatch)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		  }
		 
		  //updated new product
		  p.setName(product.getName());
		  p.setQuantity(product.getQuantity());
		  p.setVersion(product.getVersion()+1);
		  
		 logger.info("Updated product with id" + p.getId() 
		             + "upaded name: "+ p.getName()
                     + "upaded quantity: " + p.getQuantity()
                     + "upaded version: "   + p.getVersion()
				      );
		 try {
			 if(productservice.updated(p)) {
				 return ResponseEntity
							.created(new URI("/product/"+p.getId()))
							.eTag(Integer.toString(p.getVersion()))
							.body(p);
			  }else {
				  return ResponseEntity.notFound().build();
			  }
		    }catch(Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	    }).orElse(ResponseEntity.notFound().build());
		
	}
	
	
	
	
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> detelteproduct( @PathVariable ("id") Integer id){
		Optional<product> findbid = productservice.findbid(id);
	 return	findbid.map(p-> {
			if(productservice.delete(p.getId())) {
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}).orElse(ResponseEntity.notFound().build());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
