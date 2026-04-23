package ra.QuanLyCuaHangDienThoai;

import ra.QuanLyCuaHangDienThoai.presentation.LoginView;

import java.util.Scanner;

public class Main {
    private static LoginView loginView = new LoginView();
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("========== HỆ THỐNG QUẢN LÝ CỬA HÀNG ==========");
                System.out.println("1. Đăng nhập Admin");
                System.out.println("2. Thoát");
                System.out.println("===============================================");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        loginView.displayLoginView();
                        break;
                    case 2:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Không đúng định dạng của số!");
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}