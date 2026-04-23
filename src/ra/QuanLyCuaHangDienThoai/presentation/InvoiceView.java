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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InvoiceView {
    private ProductView productView = new ProductView();
    private ProductDAOImpl productDAO = new ProductDAOImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();
    private CustomerView customerView = new CustomerView();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
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
            } catch (NumberFormatException e) {
                System.err.println("Không đúng định dạng của số!");
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private void showListInvoice() {
        List<Invoice> invoices = invoiceDAO.listInvoices();
        if (invoices.isEmpty()) {
            System.err.println("Danh sách trống");
        }
        for (Invoice invoice : invoices) {
            System.out.println("ID Invoice: " + invoice.getId() + " | ID Customer: " + invoice.getCustomerId() + " | Created_at: " + invoice.getCreatedAt());
            List<InvoiceDetails> details = invoiceDetailsDAO.getInvoiceDetails(invoice.getId());
            if (details.isEmpty()) {
                System.err.println("Danh sách trống");
            } else {
                for (InvoiceDetails d : details) {
                    System.out.printf("   + SP ID: %d | NAME: %S | SL: %d | Giá: %,.0f VNĐ\n",
                            d.getProductId(), productDAO.getProductById(d.getProductId()).getName() , d.getQuantity(), d.getUnitPrice());
                }
            }
            System.out.printf("Total_amount: %,.0f VNĐ\n", invoice.getTotalAmount());
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
                            int newInvoiceId = invoiceDAO.addInvoice(customer.getId());
                            Invoice invoice = invoiceDAO.infoNewInvoice();
                            boolean isCheck1 = true;
                            boolean hasItem = false;
                            while (isCheck1) {
                                productView.showListProduct();
                                if (productDAO.listProduct().isEmpty()) {
                                    System.err.println("Xin lỗi, chúng tôi không còn mặt hàng nào để bán!");
                                    customerDAO.deleteCustomer(customer.getId());
                                    invoiceDAO.deleteInvoice(newInvoiceId);
                                    isCheck1 = false;
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
                                    Product product = productDAO.getProductById(productId);
                                    if (!productService.isCheckProductStock(productId)) {
                                        System.err.printf("Sản phẩm %s đã được bán hết! Vui lòng chọn sản phẩm khác!", product.getName());
                                    } else {
                                        System.out.print("Nhập số lượng muốn mua: ");
                                        int quantity = Integer.parseInt(sc.nextLine());
                                        if (quantity <= 0) {
                                            System.err.println("Số lượng phải lớn hơn 0!");
                                        }
                                        else if (!invoiceDetailsService.checkProductStock(productId, quantity)) {
                                            System.err.printf("Sản phẩm %s không đủ số lượng tồn kho!", product.getName());
                                            System.err.printf("Hiện tại sản phẩm %s chỉ còn %d trong kho! Vui lòng chọn lại số lượng hoặc bạn có thể tham khảo sản phẩm khác!\n", product.getName(), product.getStock());
                                        } else {
                                            invoiceDetailsDAO.addInvoiceDetails(new InvoiceDetails(0, newInvoiceId,  productId, quantity, 0));
                                            hasItem = true;
                                        }
                                    }
                                }
                            }
                            if (hasItem) {
                                invoiceDAO.addFinalInvoice(newInvoiceId);
                            }
                            else {
                                invoiceDAO.deleteInvoice(newInvoiceId);
                                customerDAO.deleteCustomer(customer.getId());
                                System.out.println("Không có sản phẩm nào. Hủy hóa đơn.");
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
            } else {
                int newInvoiceId = invoiceDAO.addInvoice(id);
                Invoice invoice = invoiceDAO.infoNewInvoice();
                boolean isCheck1 = true;
                boolean hasItem = false;
                while (isCheck1) {
                    productView.showListProduct();
                    if (productDAO.listProduct().isEmpty()) {
                        System.err.println("Xin lỗi, chúng tôi không còn mặt hàng nào để bán!");
                        invoiceDAO.deleteInvoice(newInvoiceId);
                        isCheck1 = false;
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
                        Product product = productDAO.getProductById(productId);
                        if (!productService.isCheckProductStock(productId)) {
                            System.err.printf("Sản phẩm %s đã được bán hết! Vui lòng chọn sản phẩm khác!\n", product.getName());
                        } else {
                            System.out.print("Nhập số lượng muốn mua: ");
                            int quantity = Integer.parseInt(sc.nextLine());
                            if (quantity <= 0) {
                                System.err.println("Số lượng phải lớn hơn 0!");
                            }
                            else if (!invoiceDetailsService.checkProductStock(productId, quantity)) {
                                System.err.printf("Sản phẩm %s không đủ số lượng tồn kho!\n", product.getName());
                                System.err.printf("Hiện tại sản phẩm %s chỉ còn %d trong kho! Vui lòng chọn lại số lượng hoặc bạn có thể tham khảo sản phẩm khác!\n", product.getName(), product.getStock());
                            } else {
                                invoiceDetailsDAO.addInvoiceDetails(new InvoiceDetails(0, newInvoiceId, productId, quantity, 0));
                                hasItem = true;
                            }
                        }
                    }
                }
                if (hasItem) {
                    invoiceDAO.addFinalInvoice(newInvoiceId);
                }
                else {
                    invoiceDAO.deleteInvoice(newInvoiceId);
                    System.out.println("Không có sản phẩm nào được mua! Hủy hóa đơn!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private void searchInvoice() {
        try {
            boolean isCheck2 = true;
            while (isCheck2) {
                System.out.println("1. Tìm theo tên khách hàng");
                System.out.println("2. Tìm theo ngày/tháng/năm");
                System.out.println("3. Quay lại menu hóa đơn");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Nhập từ khóa để tìm kiếm hóa đơn theo tên khách hàng: ");
                        String customerName = sc.nextLine();
                        List<Invoice> list = invoiceDAO.searchInvoiceByCustomerName(customerName);
                        if (list.isEmpty()) {
                            System.err.println("Không tồn tại hóa đơn nào phù hợp!");
                            isCheck2 = false;
                        } else {
                            System.out.println("========== DANH SÁCH HÓA ĐƠN ==========");
                            for (Invoice invoice : list) {
                                System.out.printf(" - ID Invoice: %d | Customer ID: %d | Customer Name: %s | created_at: %s | total_amount: %,.0f VNĐ\n",
                                        invoice.getId(),
                                        invoice.getCustomerId(),
                                        customerDAO.getCustomerById(invoice.getCustomerId()).getName(),
                                        invoice.getCreatedAt(),
                                        invoice.getTotalAmount()
                                );
                            }
                            isCheck2 = false;
                        }
                        break;
                    case 2:
                        System.out.print("Nhập ngày/tháng/năm để tìm kiếm hóa đơn phù hợp: ");
                        LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        if (!date.isBefore(LocalDate.now().plusDays(1))) {
                            System.err.printf("Thời gian không đúng, vì hiện tại mới đang là %s\n", LocalDate.now());
                        }
                        else {
                            List<Invoice> list2 = invoiceDAO.searchInvoiceByDate(date);
                            if (list2.isEmpty()) {
                                System.err.println("Không tồn tại hóa đơn nào phù hợp!");
                                isCheck2 = false;
                            } else {
                                System.out.println("========== DANH SÁCH HÓA ĐƠN ==========");
                                for (Invoice invoice : list2) {
                                    System.out.printf(" - ID Invoice: %d | Customer ID: %d | Customer Name: %s | created_at: %s | total_amount: %,.0f VNĐ\n",
                                            invoice.getId(),
                                            invoice.getCustomerId(),
                                            customerDAO.getCustomerById(invoice.getCustomerId()).getName(),
                                            invoice.getCreatedAt(),
                                            invoice.getTotalAmount()
                                    );
                                }
                                isCheck2 = false;
                            }
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.err.println("Invalid choice! Try again!");
                        break;
                }
            }
        } catch (DateTimeParseException e) {
            System.err.println("Không đúng định dạng của ngày/tháng/năm !");
        } catch (NumberFormatException e) {
            System.err.println("Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
