package com.huilv.service;

import com.huilv.dao.MoneyDao;

public class MoneyService {
	public String change(double money,String currency){
		//调用数据层
		MoneyDao md = new MoneyDao();
		//获得double汇率
		double temp = money*md.select(currency);
		
		//控制金额的格式 两位小数
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");  
		return df.format(temp);
	}
}
