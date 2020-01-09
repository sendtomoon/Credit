package com.sendtomoon.credit.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.sendtomoon.credit.dto.CardDTO;
import com.sendtomoon.credit.dto.CardListDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditTest1 {

	@Autowired
	CardDTO dto;	
	@Autowired
	CardListDTO list;

	@Test
	public void test1() {
		System.err.println(JSON.toJSONString(list));
	}

}
