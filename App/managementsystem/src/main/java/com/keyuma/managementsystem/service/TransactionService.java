package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.ContributionException;
import com.keyuma.managementsystem.exception.FamilyMemberException;
import com.keyuma.managementsystem.exception.TransactionException;
import com.keyuma.managementsystem.models.*;
import com.keyuma.managementsystem.payload.request.TransactionRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.repository.ContributionRepository;
import com.keyuma.managementsystem.repository.FamilyMemberRepository;
import com.keyuma.managementsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public PagedApiResponse<Transaction, TransactionDTO> getAllTransaction(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);

        List<TransactionDTO> TransactionDTOs = transactionPage.getContent().stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getContribution().getId(),
                        transaction.getFamilyMember().getId(),
                        transaction.getAmount(),
                        transaction.getIsVerify().getValue(),
                        transaction.getTransactionDate()))
                .collect(Collectors.toList());

        return new PagedApiResponse<>(true, "All transaction retrieved successfully", transactionPage, TransactionDTOs);
    }

    public ApiResponse<TransactionDTO> getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionException("Transaction not found"));

        TransactionDTO transactionDTO = new TransactionDTO(
                transaction.getId(),
                transaction.getContribution().getId(),
                transaction.getFamilyMember().getId(),
                transaction.getAmount(),
                transaction.getIsVerify().getValue(),
                transaction.getTransactionDate()
        );

        return new ApiResponse<>(true, "Transaction retrieved successfully",transactionDTO);
    }

    public ApiResponse<MessageResponse> addTransaction(TransactionRequest transactionRequest) {
        Contribution contribution = contributionRepository.findById(transactionRequest.getContributionId())
                .orElseThrow(() -> new ContributionException("Contribution not found"));
        FamilyMember familyMember = familyMemberRepository.findById(transactionRequest.getFamilyMemberId())
                .orElseThrow(() -> new FamilyMemberException("Family Member not found"));

        Transaction transaction = new Transaction();
        transaction.setContribution(contribution);
        transaction.setFamilyMember(familyMember);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setIsVerify(EVerificationStatus.PENDING_VERIFY);
        transaction.setTransactionDate(transactionRequest.getTransactionDate());

        transactionRepository.save(transaction);

        return new ApiResponse<>(true, "Transaction added successfully", new MessageResponse("Transaction added successfully!"));
    }

    public ApiResponse<MessageResponse> updateTransaction(Long id,TransactionRequest transactionRequest){
        Contribution contribution = contributionRepository.findById(transactionRequest.getContributionId())
                .orElseThrow(() -> new ContributionException("Contribution not found"));

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionException("Transaction not found"));

        Integer isVerify = transactionRequest.getIsVerify();
        if (isVerify == null || (isVerify != 0 && isVerify != 1 && isVerify != 2)) {
            throw new IllegalArgumentException("Verification status must be 0 (rejected), 1 (pending), or 2 (verified)");
        }

        transaction.setContribution(transaction.getContribution());
        transaction.setFamilyMember(transaction.getFamilyMember());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setIsVerify(EVerificationStatus.fromValue(isVerify));
        transaction.setTransactionDate(transactionRequest.getTransactionDate());

        transactionRepository.save(transaction);

        return new ApiResponse<>(true, "Transaction updated successfully", new MessageResponse("Transaction updated successfully!"));
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

    public ApiResponse<MessageResponse> deleteTransaction(Long id){
        transactionRepository.findById(id)
                .orElseThrow( ()-> new TransactionException("Transaction not found"));

        transactionRepository.deleteById(id);
        return new ApiResponse<>(true, "Transaction deleted successfully", new MessageResponse("Transaction deleted successfully!"));
    }
}
