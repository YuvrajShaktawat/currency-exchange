package com.example.microservices.currency_exchange_service.Currency_exchange;

import java.math.BigDecimal;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyController {
	
	@Autowired
	public CurrencyExchangeRepository repository;

	@Autowired
	public Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
//		CurrencyExchange currencyExchange = new CurrencyExchange(1L, "USD", "INR", BigDecimal.valueOf(82.0));
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " + from + " to " + to);
		}
		String port = environment.getProperty("server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}
