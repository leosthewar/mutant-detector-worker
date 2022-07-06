package com.meli.challenge.mutant.detector.worker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.mongodb.client.MongoClient;

/**
 * 
 * ApplicationTests </br>
 * Class with integration tests 
 *
 * @author Leonardo Sthewar Rincon - leo.sthewar.rincon@gmail.com
 * @since 5/07/2022
 *
 */
@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("direct:producer-db-route")
class ApplicationTests{

	
	private final static String REQUEST_MESSAGE = "src/test/resources/requestMessages/message.json";
	
	@Autowired
	private CamelContext context;
	
	@Autowired
	private ProducerTemplate template;
	
	@EndpointInject(value="mock:direct:producer-db-route")
	MockEndpoint mockProducerEndpoint;

	

	@TestConfiguration
	static class Config {
		@Bean("mongoBean")
		MongoClient mongoClient() {

			return null;
		}
	}
	
	
	@Test
	void successRequestTransformationTest() throws Exception 
	{
		
		context.start();
		assertTrue(context.getStatus().isStarted());
	
		File expectedSuccessFile = new File(REQUEST_MESSAGE);
		template.requestBody("direct:mock-kafka", expectedSuccessFile);
		mockProducerEndpoint.expectedMessageCount(1);
		mockProducerEndpoint.assertIsSatisfied();
		
	}



	

}
