package com.aex.platform.interfaces;

import com.aex.platform.entities.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyInterface {
    Currency save(Currency currency);

    List<Currency> saveAll(List<Currency> currency);

    Optional<Currency> findByCurrencyId(Long currencyId);

    List<Currency> findAll();

    boolean deleteById(Long Id);

    boolean deleteAll(List<Long> Ids);


    Currency update(Currency currency);
}
