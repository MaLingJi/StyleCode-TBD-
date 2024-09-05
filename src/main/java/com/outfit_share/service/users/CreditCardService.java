package com.outfit_share.service.users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.users.CreditCards;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.repository.users.CreditCardsRepository;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardsRepository creditCardsRepo;

    @Autowired
    private UserDetailRepository userDetailRepo;

    public CreditCards saveCard(Integer userId, String cNumber, String expDateStr, String securityCode,
            String holderName,
            String billingAddress) throws ParseException {
        UserDetail userDetail = userDetailRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        CreditCards cards = new CreditCards();
        cards.setUserDetail(userDetail);
        cards.setCardNumber(cNumber);
        Date expDate = convertStringToDate(expDateStr);
        System.out.println(expDate);
        cards.setExpirationDate(expDate);
        cards.setSecurityCode(securityCode);
        cards.setHolderName(holderName);
        cards.setBillingAddress(billingAddress);
        cards.setCreatedTime(new Date());
        cards.setUpdatedTime(new Date());

        return creditCardsRepo.save(cards);
    }

    public List<CreditCards> findCards(Integer userId) {
        return creditCardsRepo.findByUserDetailId(userId);
    }

    public boolean deleteById(Integer id, Integer userId) {
        Optional<CreditCards> optional = creditCardsRepo.findById(id);
        if (optional.isPresent()) {
            CreditCards card = optional.get();
            if (card.getUserDetail().getId().equals(userId))
                creditCardsRepo.delete(card);
            return true;
        }
        return false;
    }

    // 將 MM/yy 格式的字串轉換為 Date
    private Date convertStringToDate(String dateStr) throws ParseException {
        // 定義輸入格是為 MM/yy
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/yy");
        inputFormat.setLenient(false); // 嚴格模式

        // 將字串轉換為 Date
        Date date = inputFormat.parse(dateStr);
        // 使用 Calendar 設置日期為該月的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 設置為該月的第一天

        return calendar.getTime();
    }
}
