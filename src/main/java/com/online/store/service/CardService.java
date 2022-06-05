package com.online.store.service;


import com.online.store.dto.request.CardRequest;
import com.online.store.dto.response.CardResponse;
import com.online.store.entity.Card;

import java.util.List;

public interface CardService {


    CardResponse createCard(CardRequest cardRequest);

    CardResponse modifyCard(Long id, Integer quantity);

    void deleteCard(Long id);

    List<Card> findAllCards(List<Long> cardListId);


}
