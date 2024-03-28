package com.aex.platform.service;

import com.aex.platform.entities.Currency;
import com.aex.platform.interfaces.CurrencyInterface;
import com.aex.platform.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CurrencyService implements CurrencyInterface {

    @Autowired
    CurrencyRepository currencyRepository;
    @Override
    public Currency save(Currency currency) {
        Currency save;
        save = currencyRepository.save(currency);
        return save;
    }

    @Override
    public List<Currency> saveAll(List<Currency> currency) {
        return null;
    }

    @Override
    public Optional<Currency> findByCurrencyId(Long currencyId) {
        return currencyRepository.findById(currencyId);
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public boolean deleteById(Long Id) {
        return false;
    }

    @Override
    public boolean deleteAll(List<Long> Ids) {
        return false;
    }

    @Override
    public Currency update(Currency currency) {
        return null;
    }
}
