package br.com.dio.Board.persistence.entity;

import java.time.OffsetDateTime;

public class BlockEntity {

    private Long id;
    private OffsetDateTime blockedAt;
    private String blockReason;
    private OffsetDateTime unBlockedAt;
    private String unBlockReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(OffsetDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public OffsetDateTime getUnBlockedAt() {
        return unBlockedAt;
    }

    public void setUnBlockedAt(OffsetDateTime unBlockedAt) {
        this.unBlockedAt = unBlockedAt;
    }

    public String getUnBlockReason() {
        return unBlockReason;
    }

    public void setUnBlockReason(String unBlockReason) {
        this.unBlockReason = unBlockReason;
    }
}
