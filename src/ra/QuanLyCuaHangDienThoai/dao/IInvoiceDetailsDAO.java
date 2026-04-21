package ra.QuanLyCuaHangDienThoai.dao;

import java.util.List;

public interface IInvoiceDetailsDAO<T> {
    void addInvoiceDetails(T item);
    List<T> getInvoiceDetails(int invoiceId);
}
