package com.huilv.service;

import com.huilv.dao.MoneyDao;

public class MoneyService {
	public String change(double money,String currency){
		//�������ݲ�
		MoneyDao md = new MoneyDao();
		//���double����
		double temp = money*md.select(currency);
		
		//���ƽ��ĸ�ʽ ��λС��
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");  
		return df.format(temp);
	}
}
