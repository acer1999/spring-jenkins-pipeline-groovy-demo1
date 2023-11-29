package com.myapp.spring


import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.myapp.spring.domain.Product
import com.myapp.spring.service.ProductService

import spock.lang.Specification


@SpringBootTest
@AutoConfigureMockMvc

class ProductCreationTest extends Specification {

   @Autowired
   
	MockMvc mvc
   
   
   
   
	@SpringBean
   
   ProductService productService=Mock()
   
   
   
   
   @Autowired
   
   ObjectMapper objectMapper
   
   
   
   
   def "should pass product save details to domain component and return 'ok' status"() {
   
   given:
   
   Product request = new Product(
   
   productId : 4,
   
   productName : 'SamsungGalaxyNote',
   
   
   
				price: 78546.5,
   
				starRating: 4.2
   
   )
   
   
   
   
   and:
   
   productService.saveProduct(request)
   
	   
   
		when:
   
		def response = mvc.perform(
   
				post('/api/products').contentType(APPLICATION_JSON).content(toJson(request))
   
		).andReturn().response // notice the extra call to: andReturn()
   
   
   
   
		then:
   
		response.status == HttpStatus.OK.value()
   
   }

   
//   def "GetProduct"() {
//	   when: 'Calling getProduct for a known product entity'
//	   def mvcResult = mvc.perform(get("/persons/1/rank").contentType(APPLICATION_JSON))
//							   .andExpect(status().is2xxSuccessful()).andReturn()
//
//	   then: 'we define the mock for JUST the external service'
//	   productService.getProduct(_) >> {
//		   Product request = new Product(
//   
//   productId : 4,
//   
//   productName : 'SamsungGalaxyNote',
//   
//   
//   
//				price: 78546.5,
//   
//				starRating: 4.2
//   
//   )
//	   }
//	   noExceptionThrown()
//
//	   when: 'inspecting the contents'
//	   def resultingJson = mvcResult.response.contentAsString
//
//	   then: 'the result contains a mix of mocked service data and actual wired component data'
//	   resultingJson == ''
//   }
   
}

