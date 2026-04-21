package ra.QuanLyCuaHangDienThoai.presentation;

import ra.QuanLyCuaHangDienThoai.business.impl.InvoiceDetailsServiceImpl;
import ra.QuanLyCuaHangDienThoai.business.impl.InvoiceServiceImpl;
import ra.QuanLyCuaHangDienThoai.business.impl.ProductServiceImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.CustomerDAOImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.InvoiceDAOImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.InvoiceDetailsDAOImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.ProductDAOImpl;
import ra.QuanLyCuaHangDienThoai.model.Customer;
import ra.QuanLyCuaHangDienThoai.model.Invoice;
import ra.QuanLyCuaHangDienThoai.model.InvoiceDetails;
import ra.QuanLyCuaHangDienThoai.model.Product;

import java.util.Scanner;

public class InvoiceView {
    private ProductView productView = new ProductView();
    private ProductDAOImpl productDAO = new ProductDAOImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();
    private CustomerView customerView = new CustomerView();
    private CustomerDAOImpl customerDAO =  new CustomerDAOImpl();
    private InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
    private InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
    private InvoiceDetailsDAOImpl invoiceDetailsDAO = new InvoiceDetailsDAOImpl();
    private InvoiceDetailsServiceImpl invoiceDetailsService = new InvoiceDetailsServiceImpl();
    private Scanner sc = new Scanner(System.in);
    public void displayInvoiceMenu() {
        while (true) {
            System.out.println("========== QUẢN LÝ HÓA ĐƠN ==========");
            System.out.println("1. Hiển thị danh sách hóa đơn");
            System.out.println("2. Thêm mới hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Quay lại menu chính");
            System.out.println("=====================================");
            System.out.print("Nhập lựa chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        showListInvoice();
                        break;
                    case 2:
                        addInvoice();
                        break;
                    case 3:
                        searchInvoice();
                        break;
                    case 4:
                        return;
                    default:
                        System.err.println("Invalid choice.");
                        break;
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
    private void showListInvoice() {
        if (invoiceDAO.listInvoices().isEmpty()) {
            System.err.println("Danh sách trống!");
        }
        else {
            System.out.println("========== DANH SÁCH HÓA ĐƠN ==========");
            for (Invoice invoice : invoiceDAO.listInvoices()) {
                System.out.printf("ID Invoice: %d | ID Customer: %d | Created_at: %s | Total_Amount: %.2f\n",
                        invoice.getId(), invoice.getCustomerId(), invoice.getCreatedAt(), invoice.getTotalAmount()
                );
            }
            System.out.println("=======================================");
        }
    }
    private void addInvoice() {
        try {
            System.out.print("Nhập ID khách hàng: ");
            int id = Integer.parseInt(sc.nextLine());
            if (!invoiceService.isExistCustomerId(id)) {
                System.err.printf("Không tồn tại khách hàng có id %d\n", id);
                boolean isCheck = true;
                while (isCheck) {
                    System.out.println("=====================================");
                    System.out.println("Khách hàng mua lần đầu. Tạo mới khách hàng?");
                    System.out.println("1. Đồng ý");
                    System.out.println("2. Không đồng ý");
                    System.out.print("Nhập lựa chọn: ");
                    int choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {
                        case 1:
                            customerView.addCustomer();
                            Customer customer = customerDAO.infoNewCustomer();
                            invoiceDAO.addInvoice(customer.getId());
                            Invoice invoice = invoiceDAO.infoNewInvoice();
                            boolean isCheck1 = true;
                            while (isCheck1) {
                                productView.showListProduct();
                                if (productDAO.listProduct().isEmpty()) {
                                    System.out.println("Xin lỗi, chúng tôi không còn mặt hàng nào để bán!");
//                                    xóa khách hàng và xóa đơn hàng mới nhất
                                    break;
                                }
                                System.out.print("Nhập ID sản phẩm muốn mua (Nhập -1 để kết thúc chọn sản phẩm): ");
                                int productId = Integer.parseInt(sc.nextLine());
                                if (productId == -1) {
                                    break;
                                }
                                while (!productService.isExistProductId(productId)) {
                                    System.err.printf("Không tồn tại sản phẩm có id %d", productId);
                                    System.out.print("Nhập lại ID sản phẩm muốn mua (Nhập -1 để kết thúc chọn sản phẩm): ");
                                    productId = Integer.parseInt(sc.nextLine());
                                    if (productId == -1) {
                                        isCheck1 = false;
                                        break;
                                    }
                                }
                                if (productService.isExistProductId(productId)) {
                                    System.out.print("Nhập số lượng muốn mua: ");
                                    int quantity = Integer.parseInt(sc.nextLine());
                                    Product product = productDAO.getProductById(productId);
                                    if (!invoiceDetailsService.checkProductStock(productId, quantity)) {
                                        System.err.printf("Sản phẩm %s không đủ số lượng tồn kho!", product.getName());
                                    }
                                    else {
                                        invoiceDetailsDAO.addInvoiceDetails(new InvoiceDetails(invoice.getId(), productId, quantity, 0));
                                    }
                                }
                            }
                            isCheck = false;
                            break;
                        case 2:
                            System.out.println("Không thực hiện tạo mới khách hàng và đơn hàng!");
                            isCheck = false;
                            break;
                        default:
                            System.err.println("Invalid choice. Try again!");
                            break;
                    }
                }
            }
            else {
                invoiceDAO.addInvoice(id);
                Invoice invoice = invoiceDAO.infoNewInvoice();
                boolean isCheck1 = true;
                while (isCheck1) {
                    productView.showListProduct();
                    if (productDAO.listProduct().isEmpty()) {
                        System.out.println("Xin lỗi, chúng tôi không còn mặt hàng nào để bán!");
//                                     xóa đơn hàng mới nhất
                        break;
                    }
                    System.out.print("Nhập ID sản phẩm muốn mua (Nhập -1 để kết thúc chọn sản phẩm): ");
                    int productId = Integer.parseInt(sc.nextLine());
                    if (productId == -1) {
                        break;
                    }
                    while (!productService.isExistProductId(productId)) {
                        System.err.printf("Không tồn tại sản phẩm có id %d", productId);
                        System.out.print("Nhập lại ID sản phẩm muốn mua (Nhập -1 để kết thúc chọn sản phẩm): ");
                        productId = Integer.parseInt(sc.nextLine());
                        if (productId == -1) {
                            isCheck1 = false;
                            break;
                        }
                    }
                    if (productService.isExistProductId(productId)) {
                        System.out.print("Nhập số lượng muốn mua: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        Product product = productDAO.getProductById(productId);
                        if (!invoiceDetailsService.checkProductStock(productId, quantity)) {
                            System.err.printf("Sản phẩm %s không đủ số lượng tồn kho!", product.getName());
                        }
                        else {
                            invoiceDetailsDAO.addInvoiceDetails(new InvoiceDetails(invoice.getId(), productId, quantity, 0));
                        }
                    }
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
