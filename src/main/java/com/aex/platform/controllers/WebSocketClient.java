package com.aex.platform.controllers;

import com.aex.platform.entities.TransactionTodo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="aex-ms-socket", url="ec2-52-23-71-203.compute-1.amazonaws.com:3000")
public interface WebSocketClient {
    @GetMapping("/chat/room")
    String sendMessage();

    @PostMapping(value = "/chat/room",consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean transactionPending(@RequestBody List<TransactionTodo> data );

    @PostMapping(value = "/chat/room/giros/in-progress",consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean transactionInProgress(@RequestBody List<TransactionTodo> data );
}
