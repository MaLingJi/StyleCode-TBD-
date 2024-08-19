package com.outfit_share.repository.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit_share.entity.users.CreditCards;

public interface CreditCardsRepository extends JpaRepository<CreditCards, Integer> {
    List<CreditCards> findByUserDetailId(Integer userId);
}
