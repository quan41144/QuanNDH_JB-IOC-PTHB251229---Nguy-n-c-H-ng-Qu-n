package ra.QuanLyCuaHangDienThoai.model;

import java.time.LocalDateTime;

public class Invoice {
    private int id;
    private int customerId;
    private LocalDateTime createdAt;
    private double totalAmount;
    public Invoice(int customerId, int id, LocalDateTime createdAt, double totalAmount) {
        this.customerId = customerId;
        this.id = id;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
