package com.example.demo.domain.Chat.controller;

import com.example.demo.domain.Chat.entity.RewardTransaction;
import com.example.demo.domain.Chat.service.RewardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardTransactionController {

    private final RewardTransactionService rewardTransactionService;

    @Autowired
    public RewardTransactionController(RewardTransactionService rewardTransactionService) {
        this.rewardTransactionService = rewardTransactionService;
    }

    @PostMapping("/rewardTransactions")
    public ResponseEntity<RewardTransaction> createRewardTransaction(@RequestBody RewardTransaction rewardTransaction) {
        RewardTransaction savedTransaction = rewardTransactionService.saveRewardTransaction(rewardTransaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }
}
