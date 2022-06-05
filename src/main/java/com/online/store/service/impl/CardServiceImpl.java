package com.online.store.service.impl;

import com.online.store.dto.request.CardRequest;
import com.online.store.dto.response.CardResponse;
import com.online.store.entity.Card;
import com.online.store.exception.UnauthorizedAccessException;
import com.online.store.repository.CardRepository;
import com.online.store.service.CardService;
import com.online.store.service.ProductService;
import com.online.store.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.online.store.exception.NotFoundException.notFoundException;
import static com.online.store.util.MessagesErrors.CHANGE_PERMISSION;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ProductService productService;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, ProductService productService) {
        this.cardRepository = cardRepository;
        this.productService = productService;
    }


    @Transactional
    @Override
    public CardResponse createCard(CardRequest cardRequest) {
        Card card = getCard(cardRequest);
        cardRepository.save(card);
        return getCardResponse(card);
    }

    @Override
    public CardResponse modifyCard(Long id, Integer quantity) {
        Card card = cardRepository.findById(id).orElseThrow(() -> notFoundException(Constant.CARD));
        if (!card.getIsActive()) {
            throw new UnauthorizedAccessException(CHANGE_PERMISSION);
        }
        card.setQuantity(quantity);
        card.setTotal(getTotal(card, quantity));
        cardRepository.save(card);
        return getCardResponse(card);
    }

    @Override
    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> notFoundException(Constant.CARD));
        card.getProduct().removeCard(card);
        cardRepository.delete(card);
    }

    @Override
    public List<Card> findAllCards(List<Long> cardListId) {
        List<Card> cardList = cardListId.stream().map(cardRepository::getById).collect(Collectors.toList());
        cardList.forEach(card -> card.setIsActive(false));
        return cardList;
    }

    private int getTotal(Card card, Integer quantity) {
        return quantity * card.getProduct().getPrice();
    }

    private Card getCard(CardRequest cardRequest) {
        Card card = new Card();
        card.setProduct(productService.findProductByIdFromDB(cardRequest.getProductId()));
        card.getProduct().addCard(card);
        card.setQuantity(cardRequest.getQuantity());
        card.setTotal(getTotal(card, card.getQuantity()));
        return card;
    }

    private CardResponse getCardResponse(Card card) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setIsActive(card.getIsActive());
        cardResponse.setProductId(card.getProduct().getId());
        cardResponse.setQuantity(card.getQuantity());
        cardResponse.setTotal(card.getTotal());
        return cardResponse;
    }

}
