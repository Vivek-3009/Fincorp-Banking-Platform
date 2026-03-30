package com.vivek.fincorp.transaction_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vivek.fincorp.transaction_service.enums.TransactionStatus;
import com.vivek.fincorp.transaction_service.enums.TransactionType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "transactions",
    indexes = {
        @Index(name = "idx_tx_idempotency", columnList = "idempotencyKey", unique = true),
        @Index(name = "idx_tx_from_account", columnList = "fromAccountNumber"),
        @Index(name = "idx_tx_to_account", columnList = "toAccountNumber")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(length = 36)
    private String id;

    @Column(length = 20)
    private String fromAccountNumber;

    @Column(length = 20)
    private String toAccountNumber;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;

    @Column(nullable = false, unique = true, length = 64)
    private String idempotencyKey;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
