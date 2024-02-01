package com.aex.platform.service;

import com.aex.platform.controllers.WebSocketClient;
import com.aex.platform.entities.models.ChatMessage;
import com.aex.platform.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class WebSocketService {
    @Autowired
    private WebSocketClient client;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private    TransactionService transactionService;

    public void sendMessage() {
        client.sendMessage();
    }

    public ResponseEntity<Boolean> sendTrans(Long[] statusIds) {
       return transactionService.updateWebsocketTransactionsTodo(Arrays.stream(statusIds).toList());
    }

}
