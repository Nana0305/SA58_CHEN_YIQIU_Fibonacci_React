package com.problem.coinapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.problem.coinapi.api.controller.CoinController;

@WebMvcTest(CoinController.class)
public class CoinControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetCoinListCase1() throws Exception {
		Double amt = 7.3;
		List<Number> denoms = Arrays.asList(0.1, 0.5, 1, 5, 10);

		mockMvc.perform(get("/coin").param("amt", String.valueOf(amt)).param("denoms", "0.1", "0.5", "1", "5", "10")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[0.1,0.1,0.1,1,1,5]"));
	}

	@Test
	public void testGetCoinListCase2() throws Exception {
		Double amt = 103d;
		List<Number> denoms = Arrays.asList(1, 2, 50);

		mockMvc.perform(get("/coin").param("amt", String.valueOf(amt)).param("denoms", "1", "2", "50")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("[1,2,50,50]"));
	}

	@Test
	public void testGetCoinListNegativeAmt() throws Exception {
		Double amt = -100d;

		mockMvc.perform(get("/coin").param("amt", String.valueOf(amt)).param("denoms", "1", "2", "5", "10")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
}
