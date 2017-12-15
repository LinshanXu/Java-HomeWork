package com.huilv.dao;

import com.huilv.service.MoneyService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoneyService ms = new MoneyService();
		System.out.println(ms.change(6.5, "US"));
	}

}
