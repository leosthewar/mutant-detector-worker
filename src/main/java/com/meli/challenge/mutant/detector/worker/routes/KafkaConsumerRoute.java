package com.meli.challenge.mutant.detector.worker.routes;

import java.net.ConnectException;
import java.net.UnknownHostException;

import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

import com.meli.challenge.mutant.detector.worker.configuration.ConfigurationRoute;
import com.meli.challenge.mutant.detector.worker.properties.KafkaConsumerProperties;

/**
 * 
 * RestConsumerRoute </br>
 * Route to create a API Rest to detect mutant DNA 
 * 
 * @author Leonardo Sthewar Rincon - leo.sthewar.rincon@gmail.com
 * @since 1/07/2022
 *
 */
@Component
public class KafkaConsumerRoute extends ConfigurationRoute {

	
	private KafkaConsumerProperties kafkaConsumerProperties;
	
	public KafkaConsumerRoute(KafkaConsumerProperties kafkaConsumerProperties) {
		this.kafkaConsumerProperties=kafkaConsumerProperties;
		
	}
	
	@Override
	public void configure() throws Exception {
		

		onException(UnknownHostException.class).handled(true)
			.maximumRedeliveries(3)
			.redeliveryDelay(2000)
			.to(ROUTE_EXCEPTION);
	
		onException(ConnectException.class).handled(true)
			.maximumRedeliveries(3)
			.redeliveryDelay(2000)
			.to(ROUTE_EXCEPTION);
	
		onException(Exception.class).handled(true)
			.to(ROUTE_EXCEPTION);
			
		
		from(kafkaConsumerProperties.getCamelEndpoint()).routeId("consumer-kafka-route")
			.log(LoggingLevel.DEBUG," Topic msg ${body}")
			.to("direct:producer-db-route")
			.end();
        

    }
}
