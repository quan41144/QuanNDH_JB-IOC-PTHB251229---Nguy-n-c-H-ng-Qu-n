package ra.QuanLyCuaHangDienThoai.business;

public interface ICustomerService<T> {
    boolean isExistPhone(String phone);
    boolean isExistEmail(String email);
    boolean isExistCustomerId(int id);
}
