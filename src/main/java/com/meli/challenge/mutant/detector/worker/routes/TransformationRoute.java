package com.meli.challenge.mutant.detector.worker.routes;

import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

import com.meli.challenge.mutant.detector.worker.configuration.ConfigurationRoute;

/**
 * 
 * TransformationRoute </br>
 * Route to  make transformations, in this to case  to validate request and validate DNA Mutant
 * @author Leonardo Sthewar Rincon - leo.sthewar.rincon@gmail.com
 * @since 1/07/2022
 *
 */
@Component
public class TransformationRoute extends ConfigurationRoute {

	@Override
	public void configure() throws Exception {
		
		/**
		 * Route to handle exceptions and generate response error 
		 */
		from(ROUTE_EXCEPTION).routeId("ruta_exception")
			.setProperty("exceptionMessage",simple("${exception.message}"))
			.log(LoggingLevel.DEBUG, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
			.log(LoggingLevel.DEBUG, "StackTrace: ${exception.stacktrace}")
			.end();

	}

}
