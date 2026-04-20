package ra.QuanLyCuaHangDienThoai.dao;

import ra.QuanLyCuaHangDienThoai.model.Customer;

import java.util.List;

public interface ICustomerDAO<T> {
    void addCustomer(T item);
    void infoCustomer(int id);
    void updateCustomer(T item);
    void deleteCustomer(int id);
    Customer getCustomerById(int id);
    List<T> listCustomer();
}
