package com.problem.coinapi.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoinController {

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/coin")
	public List<Number> getCoinList(@RequestParam Double amt, @RequestParam List<Number> denoms) {
		if (amt <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}

		if (denoms == null || denoms.isEmpty()) {
			throw new IllegalArgumentException("Denominations list cannot be empty");
		}

		List<Number> coinList = new ArrayList<>();

		Collections.sort(denoms, Collections.reverseOrder());

		for (Number denom : denoms) {
			double denomDouble = denom.doubleValue();
			while (amt >= denomDouble) {
				amt -= denomDouble;
				amt = Math.round(amt * 100.0) / 100.0;
				coinList.add(denom);
			}
		}
		Collections.sort(coinList, (a, b) -> Double.compare(a.doubleValue(), b.doubleValue()));
		return coinList;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
