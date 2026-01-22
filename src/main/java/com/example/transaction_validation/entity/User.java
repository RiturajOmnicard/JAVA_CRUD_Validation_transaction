package com.example.transaction_validation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "is_blacklisted", nullable = false)
    private boolean blacklisted;

    public User() {}

    public String getUserId() { return userId; }
    public String getStatus() { return status; }
    public boolean isBlacklisted() { return blacklisted; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setStatus(String status) { this.status = status; }
    public void setBlacklisted(boolean blacklisted) { this.blacklisted = blacklisted; }
}
