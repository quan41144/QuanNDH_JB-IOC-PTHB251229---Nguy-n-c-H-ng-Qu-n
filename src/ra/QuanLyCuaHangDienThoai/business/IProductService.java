package ra.QuanLyCuaHangDienThoai.business;

public interface IProductService<T> {
    boolean isExistProductName(String name);
    boolean isExistProductId(int id);
    boolean isCheckPriceAndStock(double price, int stock);
    boolean isCheckProductStock(int id);
}
