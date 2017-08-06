package com.helper.trading.service;

import com.helper.trading.model.TxType;
import com.helper.trading.repository.TxTypeRepository;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxTypeService {
    private TxTypeRepository txTypeRepository;

    @Autowired
    public void setTxTypeRepository(TxTypeRepository txTypeRepository) {
        this.txTypeRepository = txTypeRepository;
    }

    public TxType fromUserTrade(UserTrade userTrade) {
        if (userTrade == null)
            return null;

        return txTypeRepository.findFirstByName(userTrade.getType().equals(Order.OrderType.ASK) ? "Buy" : "Sell");
    }
}
