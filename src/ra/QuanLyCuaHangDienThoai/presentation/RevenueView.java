package ra.QuanLyCuaHangDienThoai.presentation;

import ra.QuanLyCuaHangDienThoai.business.impl.InvoiceServiceImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.InvoiceDAOImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RevenueView {
    private InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
    private InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
    private Scanner sc = new Scanner(System.in);
    public void displayRevenueMenu() {
        try {
            while (true) {
                System.out.println("========== THỐNG KÊ DOANH THU ==========");
                System.out.println("1. Doanh thu theo ngày");
                System.out.println("2. Doanh thu theo tháng");
                System.out.println("3. Doanh thu theo năm");
                System.out.println("4. Quay lại menu chính");
                System.out.println("========================================");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        dailyRevenueView();
                        break;
                    case 2:
                        monthlyRevenueView();
                        break;
                    case 3:
                        yearlyRevenueView();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Try again!");
                        break;
                }
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
    private void dailyRevenueView() {
        System.out.print("Nhập ngày/tháng/năm: ");
        LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (!date.isBefore(LocalDate.now())) {
            System.err.printf("Thời gian không đúng, vì hiện tại mới đang là %s", LocalDate.now());
            return;
        }
        double res = invoiceDAO.dailyRevenue(date);
        System.out.println("Tổng doanh thu của ngày " + date + " là: " + res);
    }
    private void monthlyRevenueView() {
        System.out.print("Nhập tháng: ");
        int month = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập năm: ");
        int year = Integer.parseInt(sc.nextLine());
        LocalDate date = LocalDate.of(year, month, 1);
        if (!date.isBefore(LocalDate.now())) {
            System.err.printf("Thời gian không đúng, vì hiện tại mới đang là %s", LocalDate.now());
            return;
        }
        double res = invoiceDAO.monthlyRevenue(month, year);
        System.out.println("Tổng doanh thu của tháng " + month + " năm " + year + " là: " + res);
    }
    private void yearlyRevenueView() {
        System.out.print("Nhập năm: ");
        int year = Integer.parseInt(sc.nextLine());
        LocalDate date = LocalDate.of(year, 1, 1);
        if (!date.isBefore(LocalDate.now())) {
            System.err.printf("Thời gian không đúng, vì hiện tại mới đang là %s", LocalDate.now());
            return;
        }
        double res = invoiceDAO.yearRevenue(year);
        System.out.println("Tổng doanh thu của năm " + year + " là: " + res);
    }
}
