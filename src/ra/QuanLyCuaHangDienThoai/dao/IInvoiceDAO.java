package ra.QuanLyCuaHangDienThoai.dao;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDAO<T> {
    void addInvoice(int customer_id);
    T infoNewInvoice();
    List<T> listInvoices();
    List<T> searchInvoiceByCustomerName(String CustomerName);
    List<T> searchInvoiceByDate(LocalDate date);
}
