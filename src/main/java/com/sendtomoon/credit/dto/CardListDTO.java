package com.sendtomoon.credit.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties (prefix = "card") 
public class CardListDTO {
private List<CardDTO> list;

public List<CardDTO> getList() {
	return list;
}

public void setList(List<CardDTO> list) {
	this.list = list;
}
}
