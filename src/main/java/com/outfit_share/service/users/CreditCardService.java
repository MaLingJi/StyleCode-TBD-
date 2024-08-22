package com.outfit_share.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.outfit_share.entity.users.CreditCards;
import com.outfit_share.repository.users.CreditCardsRepository;

public class CreditCardService {

    @Autowired
    private CreditCardsRepository creditCardsRepo;

    public CreditCards saveCard(CreditCards cards) {
        return creditCardsRepo.save(cards);
    }

    public List<CreditCards> findCards(Integer userId) {
        return creditCardsRepo.findByUserDetailId(userId);
    }
}
