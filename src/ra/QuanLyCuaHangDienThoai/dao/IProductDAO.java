package ra.QuanLyCuaHangDienThoai.dao;

import ra.QuanLyCuaHangDienThoai.model.Product;

import java.util.List;

public interface IProductDAO<T> {
    void addProduct(T item);
    void infoProduct(int id);
    void updateProduct(T item);
    void deleteProduct(int id);
    List<T> listProduct();
    List<T> searchProductByBrand(String brand);
    List<T> searchProductByPrice(double begin, double end);
    List<T> searchProductByName(String name, int stock);
    T getProductById(int id);
}
