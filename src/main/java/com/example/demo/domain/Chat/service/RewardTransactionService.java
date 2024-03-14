package com.example.demo.domain.Chat.service;

import com.example.demo.domain.Chat.Repository.RewardTransactionRepository;
import com.example.demo.domain.Chat.entity.RewardTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardTransactionService {

    private final RewardTransactionRepository rewardTransactionRepository;

    @Autowired
    public RewardTransactionService(RewardTransactionRepository rewardTransactionRepository) {
        this.rewardTransactionRepository = rewardTransactionRepository;
    }

    public RewardTransaction saveRewardTransaction(RewardTransaction rewardTransaction) {
        return rewardTransactionRepository.save(rewardTransaction);
    }
}
