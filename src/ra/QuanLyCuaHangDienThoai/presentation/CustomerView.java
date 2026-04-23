package ra.QuanLyCuaHangDienThoai.presentation;

import ra.QuanLyCuaHangDienThoai.business.impl.CustomerServiceImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.CustomerDAOImpl;
import ra.QuanLyCuaHangDienThoai.model.Customer;

import java.util.Scanner;

public class CustomerView {
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private CustomerServiceImpl customerService = new CustomerServiceImpl();
    private Scanner sc = new Scanner(System.in);
    public void displayCustomerMenu() {
        while (true) {
            System.out.println("========== QUẢN LÝ KHÁCH HÀNG ==========");
            System.out.println("1. Hiển thị danh sách khách hàng");
            System.out.println("2. Thêm khách hàng mới");
            System.out.println("3. Cập nhật thông tin khách hàng");
            System.out.println("4. Xóa khách hàng theo ID");
            System.out.println("5. Quay lại menu chính");
            System.out.println("========================================");
            System.out.print("Nhập lựa chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        showListCustomer();
                        break;
                    case 2:
                        addCustomer();
                        break;
                    case 3:
                        updateCustomer();
                        break;
                    case 4:
                        deleteCustomer();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid Choice");
                        break;
                }
            }
            catch (NumberFormatException e) {
                System.err.println("Vui lòng chọn đúng số!");
            }
        }
    }
    private void showListCustomer() {
        if (customerDAO.listCustomer().isEmpty()) {
            System.err.println("Danh sách trống!");
        }
        else {
            System.out.println("========== DANH SÁCH KHÁCH HÀNG ==========");
            for (Customer customer : customerDAO.listCustomer()) {
                System.out.printf(" - ID Customer: %d | Name: %s | Phone: %s | Email: %s | Address: %s\n",
                        customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail(), customer.getAddress()
                );
            }
            System.out.println("==========================================");
        }
    }
    public void addCustomer() {
        System.out.print("Nhập tên khách hàng mới: ");
        String name = sc.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String phone = sc.nextLine();
        while (customerService.isExistPhone(phone)) {
            System.err.printf("Số điện thoại %s đã tồn tại!\n", phone);
            System.out.print("Nhập lại số điện thoại: ");
            phone = sc.nextLine();
        }
        System.out.print("Nhập Email: ");
        String email = sc.nextLine();
        while (customerService.isExistEmail(email)) {
            System.err.printf("Email %s đã tồn tại!\n", email);
            System.out.print("Nhập lại Email: ");
            email = sc.nextLine();
        }
        System.out.print("Nhập địa chỉ: ");
        String address = sc.nextLine();
        customerDAO.addCustomer(new Customer(0 , name, phone, email, address));
    }
    private void updateCustomer() {
        try {
            System.out.print("Nhập ID khách hàng để cập nhật (-1 để kết thúc cập nhật): ");
            int id = Integer.parseInt(sc.nextLine());
            if (id == -1) return;
            while (!customerService.isExistCustomerId(id)) {
                System.err.println("Không tồn tại khách hàng có id " + id);
                System.out.print("Nhập lại ID khách hàng để cập nhật (-1 để kết thúc cập nhật): ");
                id = Integer.parseInt(sc.nextLine());
                if (id == -1) return;
            }
            System.out.printf("Thông tin hiện tại của khách hàng có id %d:\n", id);
            customerDAO.infoCustomer(id);
            System.out.println("===========================================");
            Customer oldCustomer = customerDAO.getCustomerById(id);
            System.out.print("Nhập tên khách hàng muốn thay đổi (enter để giữ nguyên): ");
            String name = sc.nextLine();
            if (name.isEmpty()) {
                name = oldCustomer.getName();
            }
            System.out.print("Nhập số điện thoại muốn thay đổi (enter để giữ nguyên): ");
            String phone = sc.nextLine();
            if (phone.isEmpty()) {
                phone = oldCustomer.getPhone();
            }
            while (!phone.equalsIgnoreCase(oldCustomer.getPhone()) && customerService.isExistPhone(phone)) {
                System.err.printf("Số điện thoại %s đã tồn tại!\n", phone);
                System.out.print("Nhập lại số điện thoại muốn thay đổi (enter để giữ nguyên): ");
                phone = sc.nextLine();
            }
            System.out.print("Nhập Email muốn thay đổi (enter để giữ nguyên): ");
            String email = sc.nextLine();
            if (email.isEmpty()) {
                email = oldCustomer.getEmail();
            }
            while (!email.equalsIgnoreCase(oldCustomer.getEmail()) && customerService.isExistEmail(email)) {
                System.err.printf("Email %s đã tồn tại!\n", email);
                System.out.print("Nhập lại Email muốn thay đổi (enter để giữ nguyên): ");
                email = sc.nextLine();
            }
            System.out.print("Nhập địa chỉ muốn thay đổi (enter để giữ nguyên): ");
            String address = sc.nextLine();
            if (address.isEmpty()) {
                address = oldCustomer.getAddress();
            }
            customerDAO.updateCustomer(new Customer(id, name, phone, email, address));
        }
        catch (NumberFormatException e) {
            System.err.println("Không đúng định dạng của số!");
        }
        catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
    private void deleteCustomer() {
        try {
            System.out.print("Nhập ID khách hàng để xóa (-1 để kết thúc xóa): ");
            int id = Integer.parseInt(sc.nextLine());
            if (id == -1) return;
            while (!customerService.isExistCustomerId(id)) {
                System.err.printf("Không tồn tại khách hàng có id %d\n", id);
                System.out.print("Nhập lại ID khách hàng để xóa (-1 để kết thúc xóa): ");
                id = Integer.parseInt(sc.nextLine());
                if (id == -1) return;
            }
            boolean isCheck = true;
            while (isCheck) {
                System.out.println("Bạn có chắc chắn muốn xóa không?");
                System.out.println("1. Đồng ý");
                System.out.println("2. Không đồng ý");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        customerDAO.deleteCustomer(id);
                        isCheck = false;
                        break;
                    case 2:
                        System.out.println("Không thực hiện xóa khách hàng!");
                        isCheck = false;
                        break;
                    default:
                        System.err.println("Invalid choice.");
                        System.out.println("=====================================");
                        break;
                }
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Không đúng định dạng của số!");
        }
        catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
