package com.sendtomoon.credit.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sendtomoon.credit.dto.CardDTO;

public interface CreditDAO extends MongoRepository<CardDTO, Integer>{

}
