package ra.QuanLyCuaHangDienThoai.dao;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDAO<T> {
    int addInvoice(int customer_id);
    T infoNewInvoice();
    void addFinalInvoice(int invoice_id);
    void deleteInvoice(int invoice_id);
    List<T> listInvoices();
    List<T> searchInvoiceByCustomerName(String CustomerName);
    List<T> searchInvoiceByDate(LocalDate date);
    double dailyRevenue(LocalDate date);
    double monthlyRevenue(int month, int year);
    double yearRevenue(int year);
}
