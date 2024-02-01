package com.aex.platform.controllers;

import com.aex.platform.entities.TransactionTodo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="aex-ms-socket", url="localhost:3000")
public interface WebSocketClient {
    @GetMapping("/chat/room")
    String sendMessage();

    @PostMapping(value = "/chat/room",consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> transactionPending(@RequestBody List<TransactionTodo> data );
}
