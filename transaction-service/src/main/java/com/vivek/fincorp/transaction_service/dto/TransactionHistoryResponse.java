package com.vivek.fincorp.transaction_service.dto;

import java.util.List;

public record TransactionHistoryResponse(
        List<TransactionResponse> transactions
) {}