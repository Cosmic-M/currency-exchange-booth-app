package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Deal;

public interface DealService {
    Deal create(Deal deal);

    void deleteDealBy(String phone);
}
