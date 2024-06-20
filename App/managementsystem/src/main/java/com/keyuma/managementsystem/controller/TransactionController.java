package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.models.ContributionPlan;
import com.keyuma.managementsystem.models.Transaction;
import com.keyuma.managementsystem.payload.request.TransactionRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<PagedApiResponse<Transaction, TransactionDTO>>>  allAccess(@RequestParam(defaultValue = "0") int page,
                                                                                                      @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all transaction with pagination: page = {}, size = {}", page, size);
        try {
            PagedApiResponse<Transaction, TransactionDTO> response = transactionService.getAllTransaction(page, size);
            logger.debug("Successfully fetched transaction ");
            return ResponseEntity.ok(new ApiResponse<>(true, "All transaction retrieved successfully", response));
        } catch (Exception e) {
            logger.error("Error fetching transaction: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<ApiResponse<TransactionDTO>> getTransactionById(@PathVariable Long id){
        logger.debug("Retrieve transaction with ID : {}",id);
        try {
            TransactionDTO transactionDTO = transactionService.getTransactionById(id).getData();
            logger.debug("Successfully fetched transaction with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "T ransaction retrieved successfully", transactionDTO));
        } catch (Exception e) {
            logger.error("Error Retrieve transaction with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<ApiResponse<MessageResponse>> addTransaction(@RequestBody TransactionRequest transactionRequest){
        logger.debug("Adding transaction with contribution plan id:{}",transactionRequest.getContributionId());
        try {
            ApiResponse<MessageResponse> response = transactionService.addTransaction(transactionRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error adding transaction: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<ApiResponse<MessageResponse>> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest){
        logger.debug("Updating transaction with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = transactionService.updateTransaction(id,transactionRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating transaction with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteTransaction(@PathVariable Long id){
        logger.debug("Deleting transaction with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = transactionService.deleteTransaction(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleting transaction with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }
}
