package ra.QuanLyCuaHangDienThoai.presentation;

import ra.QuanLyCuaHangDienThoai.business.impl.ProductServiceImpl;
import ra.QuanLyCuaHangDienThoai.dao.impl.ProductDAOImpl;
import ra.QuanLyCuaHangDienThoai.model.Product;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    private ProductDAOImpl productDAO = new ProductDAOImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();
    private Scanner sc = new Scanner(System.in);

    public void displayProductMenu() {
        while (true) {
            System.out.println("========== QUẢN LÝ SẢN PHẨM ==========");
            System.out.println("1. Hiển thị danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Xóa sản phẩm theo ID");
            System.out.println("5. Tìm kiếm theo Brand");
            System.out.println("6. Tìm kiếm theo khoảng giá");
            System.out.println("7. Tìm kiếm theo tồn kho");
            System.out.println("8. Quay lại menu chính");
            System.out.println("=====================================");
            System.out.print("Nhập lựa chọn: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        showListProduct();
                        break;
                    case 2:
                        addProduct();
                        break;
                    case 3:
                        updateProduct();
                        break;
                    case 4:
                        deleteProduct();
                        break;
                    case 5:
                        searchProductByBrand();
                        break;
                    case 6:
                        searchProductByPrice();
                        break;
                    case 7:
                        searchProductByName();
                        break;
                    case 8:
                        return;
                    default:
                        System.err.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập đúng số!");
            }
        }
    }

    private void showListProduct() {
        List<Product> list = productDAO.listProduct();
        if (list.isEmpty()) {
            System.out.println("Danh sách trống!");
        } else {
            System.out.println("========== DANH SÁCH SẢN PHẨM ==========");
            for (Product p : list) {
                System.out.printf(" - ID Product: %d | name: %s | brand: %s | price: %.2f | stock: %d\n",
                        p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
            }
            System.out.println("========================================");
        }
    }

    private void addProduct() {
        try {
            while (true) {
                System.out.print("Nhập tên sản phẩm (Nhập 'exit' để dừng thêm sản phẩm): ");
                String name = sc.nextLine();
                if (name.equalsIgnoreCase("exit")) {
                    break;
                }
                while (productService.isExistProductName(name)) {
                    System.err.printf("Sản phẩm %s đã tồn tại!\n", name);
                    System.out.print("Nhập lại tên sản phẩm: ");
                    name = sc.nextLine();
                }
                System.out.print("Nhập tên nhãn hàng: ");
                String brand = sc.nextLine();
                System.out.print("Nhập giá bán: ");
                double price = Double.parseDouble(sc.nextLine());
                System.out.print("Nhập số lượng tồn kho: ");
                int stock = Integer.parseInt(sc.nextLine());
                while (!productService.isCheckPriceAndStock(price, stock)) {
                    System.err.println("Sản phẩm phải có giá bán và số lượng tồn kho lớn hơn hoặc bằng 0 !");
                    System.out.print("Nhập lại giá bán: ");
                    price = Double.parseDouble(sc.nextLine());
                    System.out.print("Nhập lại số lượng tồn kho: ");
                    stock = Integer.parseInt(sc.nextLine());
                }
                productDAO.addProduct(new Product(0, name, brand, price, stock));
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private void updateProduct() {
        try {
            System.out.print("Nhập ID của sản phẩm cần cập nhật: ");
            int id = Integer.parseInt(sc.nextLine());
            if (!productService.isExistProductId(id)) {
                System.err.printf("Sản phẩm với id %d không tồn tại!\n", id);
                return;
            }
            System.out.printf("Thông tin hiện tại của sản phẩm với id %d:\n", id);
            productDAO.infoProduct(id);
            System.out.println("=========================================");
            Product oldProduct = productDAO.getProductById(id);
            System.out.print("Nhập tên sản phẩm muốn thay đổi (enter để giữ nguyên): ");
            String name = sc.nextLine();
            if (name.isEmpty()) {
                name = oldProduct.getName();
            } else {
                while (!name.equalsIgnoreCase(oldProduct.getName()) && productService.isExistProductName(name)) {
                    System.err.printf("Sản phẩm %s đã tồn tại!\n", name);
                    System.out.print("Nhập lại tên sản phẩm muốn thay đổi (hoặc enter để giữ nguyên): ");
                    name = sc.nextLine();
                    if (name.isEmpty()) {
                        name = oldProduct.getName();
                        break;
                    }
                }
            }
            System.out.print("Nhập tên nhãn hàng muốn thay đổi (enter để giữ nguyên): ");
            String brand = sc.nextLine();
            if (brand.isEmpty()) {
                brand = oldProduct.getBrand();
            }
            System.out.print("Nhập giá bán muốn thay đổi (enter để giữ nguyên): ");
            String priceStr = sc.nextLine();
            double price = priceStr.isEmpty() ? oldProduct.getPrice() : Double.parseDouble(priceStr);
            System.out.print("Nhập số lượng tồn kho (enter để giữ nguyên): ");
            String stockStr = sc.nextLine();
            int stock = stockStr.isEmpty() ? oldProduct.getStock() : Integer.parseInt(stockStr);
            while (!productService.isCheckPriceAndStock(price, stock)) {
                System.err.println("Sản phẩm phải có giá bán và số lượng tồn kho lớn hơn hoặc bằng 0 !");
                System.out.print("Nhập lại giá bán muốn thay đổi (enter để giữ nguyên): ");
                priceStr = sc.nextLine();
                price = priceStr.isEmpty() ? oldProduct.getPrice() : Double.parseDouble(priceStr);
                System.out.print("Nhập lại số lượng tồn kho (enter để giữ nguyên): ");
                stockStr = sc.nextLine();
                stock = stockStr.isEmpty() ? oldProduct.getStock() : Integer.parseInt(stockStr);
            }
            productDAO.updateProduct(new Product(id, name, brand, price, stock));
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            System.out.print("Nhập ID của sản phẩm để xóa: ");
            int id = Integer.parseInt(sc.nextLine());
            if (!productService.isExistProductId(id)) {
                System.err.printf("Không tồn tại sản phẩm có id %d!\n", id);
                return;
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
                        productDAO.deleteProduct(id);
                        isCheck = false;
                        break;
                    case 2:
                        System.out.println("Không thực hiện xóa sản phẩm!");
                        isCheck = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        System.out.println("=====================================");
                        break;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private void searchProductByBrand() {
        System.out.print("Nhập từ khóa của nhãn hàng để tìm sản phẩm liên quan: ");
        String brand = sc.nextLine();
        if (productDAO.searchProductByBrand(brand).isEmpty()) {
            System.err.println("Không tồn tại sản phẩm nào phù hợp!");
        } else {
            System.out.println("Danh sách sản phẩm phù hợp:");
            for (Product p : productDAO.searchProductByBrand(brand)) {
                System.out.printf(" - ID Product: %d | name: %s | brand: %s | price: %.2f | stock: %d\n",
                        p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
            }
        }
    }

    private void searchProductByPrice() {
        try {
            System.out.println("Nhập khoảng giá để tìm kiếm sản phẩm:");
            System.out.print("Min: ");
            double min = Double.parseDouble(sc.nextLine());
            System.out.print("Max: ");
            double max = Double.parseDouble(sc.nextLine());
            if (productDAO.searchProductByPrice(min, max).isEmpty()) {
                System.err.println("Không tồn tại sản phẩm phù hợp!");
            }
            else {
                System.out.println("Danh sách sản phẩm phù hợp:");
                for (Product p : productDAO.searchProductByPrice(min, max)) {
                    System.out.printf(" - ID Product: %d | name: %s | brand: %s | price: %.2f | stock: %d\n",
                            p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private void searchProductByName() {
        try {
            System.out.print("Nhập từ khóa để tìm kiếm tên sản phẩm: ");
            String name = sc.nextLine();
            System.out.println("Nhập số lượng tồn kho mà bạn cần: ");
            int stock = Integer.parseInt(sc.nextLine());
            if (productDAO.searchProductByName(name, stock).isEmpty()) {
                System.out.println("Không tồn tại sản phẩm phù hợp!");
            }
            else {
                System.out.println("Danh sách sản phẩm phù hợp:");
                for (Product p : productDAO.searchProductByName(name, stock)) {
                    System.out.printf(" - ID Product: %d | name: %s | brand: %s | price: %.2f | stock: %d\n",
                            p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không đúng định dạng của số!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
