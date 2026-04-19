package ra.QuanLyCuaHangDienThoai.business;

public interface IProductService<T> {
    boolean isExistProductName(String name);
    boolean isContainProductName(String name);
    boolean isCheckProductStock(String name, int stock);
    boolean isExistProductId(int id);
    boolean isExistProductBrand(String brand);
    boolean isExistProductPrice(double begin, double end);
    boolean isCheckPriceAndStock(double price, int stock);
}
