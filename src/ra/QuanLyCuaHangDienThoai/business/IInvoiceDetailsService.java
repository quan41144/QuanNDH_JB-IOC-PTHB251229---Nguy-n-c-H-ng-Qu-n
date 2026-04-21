package ra.QuanLyCuaHangDienThoai.business;

public interface IInvoiceDetailsService<T> {
    boolean checkProductStock(int productId, int quantity);
}
