package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.ContributionException;
import com.keyuma.managementsystem.exception.TransactionException;
import com.keyuma.managementsystem.models.Contribution;
import com.keyuma.managementsystem.models.EVerificationStatus;
import com.keyuma.managementsystem.models.Transaction;
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

    public ApiResponse<MessageResponse> addTransaction(TransactionRequest transactionRequest) {
        Contribution contribution = contributionRepository.findById(transactionRequest.getContributionId())
                .orElseThrow(() -> new ContributionException("Contribution not found"));

        Transaction transaction = new Transaction();
        transaction.setContribution(contribution);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setIsVerify(EVerificationStatus.PENDING_VERIFY);
        transaction.setTransactionDate(transactionRequest.getTransactionDate());

        transactionRepository.save(transaction);

        return new ApiResponse<>(true, "Transaction added successfully", new MessageResponse("Transaction added successfully!"));
    }

    public ApiResponse<MessageResponse> verifyOrReject(Long id, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionException("Transaction not found"));

        Integer isVerify = transactionRequest.getIsVerify();
        if (isVerify == null || (isVerify != 0 && isVerify != 1 && isVerify != 2)) {
            throw new IllegalArgumentException("Verification status must be 0 (rejected), 1 (pending), or 2 (verified)");
        }

        transaction.setIsVerify(EVerificationStatus.fromValue(isVerify));
        transactionRepository.save(transaction);

        String message;
        switch (isVerify) {
            case 0:
                message = "Transaction rejected successfully!";
                break;
            case 1:
                message = "Transaction marked as pending!";
                break;
            case 2:
                message = "Transaction verified successfully!";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + isVerify);
        }

        return new ApiResponse<>(true, message, new MessageResponse(message));
    }
}
