package ra.QuanLyCuaHangDienThoai.dao;

import ra.QuanLyCuaHangDienThoai.model.Product;

import java.util.List;

public interface IProductDAO<T> {
    void addProduct(T item);
    void infoProduct(int id);
    void updateProduct(T item);
    void deleteProduct(int id);
    List<T> listProduct();
    void searchProductByBrand(String brand);
    void searchProductByPrice(double begin, double end);
    void searchProductByName(String name, int stock);
    T getProductById(int id);
}
