package ra.QuanLyCuaHangDienThoai.presentation;

import ra.QuanLyCuaHangDienThoai.business.IAdminService;
import ra.QuanLyCuaHangDienThoai.business.impl.AdminServiceImpl;

import java.util.Scanner;

public class LoginView {
    private IAdminService adminService =  new AdminServiceImpl();
    private ProductView productView = new ProductView();
    private CustomerView customerView = new CustomerView();
    private InvoiceView invoiceView = new InvoiceView();
    private RevenueView revenueView = new RevenueView();
    private Scanner sc = new Scanner(System.in);

    public void displayLoginView() {
        while(true) {
            System.out.println("========== ĐĂNG NHẬP QUẢN TRỊ ==========");
            System.out.print("Tài khoản: ");
            String username = sc.nextLine();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();
            System.out.println("========================================");
            if (adminService.login(username, password)) {
                boolean isCheck = true;
                while (isCheck) {
                    System.out.println("========== MENU CHÍNH ==========");
                    System.out.println("1. Quản lý sản phẩm điện thoại");
                    System.out.println("2. Quản lý khách hàng");
                    System.out.println("3. Quản lý hóa đơn");
                    System.out.println("4. Thống kê doanh thu");
                    System.out.println("5. Đăng xuất");
                    System.out.println("================================");
                    System.out.print("Nhập lựa chọn: ");
                    try {
                        int choice = Integer.parseInt(sc.nextLine());
                        switch (choice) {
                            case 1:
                                productView.displayProductMenu();
                                break;
                            case 2:
                                customerView.displayCustomerMenu();
                                break;
                            case 3:
                                invoiceView.displayInvoiceMenu();
                                break;
                            case 4:
                                revenueView.displayRevenueMenu();
                                break;
                            case 5:
                                System.out.println("Đăng xuất thành công!");
                                isCheck = false;
                                break;
                            default:
                                System.err.println("Vui lòng chọn đúng!");
                                break;
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Vui lòng nhập đúng số!");
                    }
                }
                break;
            }
            else {
                System.out.println("Sai tài khoản hoặc mật khẩu! Vui lòng nhập lại!");
            }
        }
    }
}
