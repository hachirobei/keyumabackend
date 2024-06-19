package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.models.Contribution;
import com.keyuma.managementsystem.models.Transaction;
import com.keyuma.managementsystem.models.VerificationStatus;
import com.keyuma.managementsystem.payload.request.TransactionRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.repository.ContributionRepository;
import com.keyuma.managementsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    public ApiResponse<MessageResponse>  addTransaction(TransactionRequest transactionRequest) {
        Contribution contribution = contributionRepository.findById(transactionRequest.getContributionId())
                .orElseThrow(() -> new RuntimeException("Contribution not found"));

        Transaction transaction = new Transaction();
        transaction.setContribution(contribution);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setIsVerify(VerificationStatus.PENDING_VERIFY);
        transaction.setTransactionDate(transactionRequest.getTransactionDate());

        transactionRepository.save(transaction);

        return new ApiResponse<>(true, "Transaction added successfully", new MessageResponse("Transaction added successfully!"));
    }
}
