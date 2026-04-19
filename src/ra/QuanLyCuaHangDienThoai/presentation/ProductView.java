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
        while(true) {
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
            int choice = Integer.parseInt(sc.nextLine());
            switch(choice) {
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
                    System.out.println("Invalid choice.");
            }
        }
    }
    private void showListProduct() {
        List<Product> list = productDAO.listProduct();
        if (list.isEmpty()) {
            System.out.println("Danh sách trống!");
        }
        else {
            for (Product p : list) {
                System.out.printf("ID Product: %d | name: %s | brand: %s | price: %.2f | stock: %d\n",
                        p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
            }
        }
    }
    private void addProduct() {
        System.out.print("Nhập tên sản phẩm: ");
        String name = sc.nextLine();
        if (productService.isExistProductName(name)) {
            System.err.printf("Sản phẩm %s đã tồn tại!\n", name);
            return;
        }
        System.out.print("Nhập tên nhãn hàng: ");
        String brand = sc.nextLine();
        System.out.print("Nhập giá bán: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập số lượng tồn kho: ");
        int stock = Integer.parseInt(sc.nextLine());
        if (!productService.isCheckPriceAndStock(price, stock)) {
            System.err.println("Sản phẩm phải có giá bán và số lượng tồn kho lớn hơn 0 !");
            return;
        }
        productDAO.addProduct(new Product(0, name, brand, price, stock));
    }
    private void updateProduct() {
        System.out.print("Nhập ID của sản phẩm cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());
        if (!productService.isExistProductId(id)) {
            System.err.printf("Sản phẩm với id %d không tồn tại!\n", id);
            return;
        }
        System.out.printf("Thông tin sản phẩm với id %d:\n", id);
        productDAO.infoProduct(id);
        System.out.println("=================================");
        System.out.print("Nhập tên sản phẩm muốn thay đổi (enter để giữ nguyên): ");
        String name = sc.nextLine();
        Product oldProduct = productDAO.getProductById(id);
        if (name.isEmpty()) {
            name = oldProduct.getName();
        }
        else {
            if (!name.equals(oldProduct.getName()) && productService.isExistProductName(name)) {
                System.err.printf("Lỗi: Sản phẩm %s đã tồn tại!\n", name);
                return;
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
        if (!productService.isCheckPriceAndStock(price, stock)) {
            System.err.println("Sản phẩm phải có giá bán và số lượng tồn kho lớn hơn 0 !");
            return;
        }
        productDAO.updateProduct(new Product(id, name, brand, price, stock));
    }
    private void deleteProduct() {
        System.out.print("Nhập ID của sản phẩm để xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        if (!productService.isExistProductId(id)) {
            System.out.printf("Không tồn tại sản phẩm có id %d!\n", id);
            return;
        }
        productDAO.deleteProduct(id);
    }
    private void searchProductByBrand() {
        System.out.print("Nhập tên nhãn hàng để tìm sản phẩm liên quan: ");
        String brand = sc.nextLine();
        if (!productService.isExistProductBrand(brand)) {
            System.out.printf("Không tồn tại nhãn hàng %s!\n", brand);
            return;
        }
        productDAO.searchProductByBrand(brand);
    }
    private void searchProductByPrice() {
        System.out.println("Nhập khoảng giá để tìm kiếm sản phẩm:");
        System.out.print("Min: ");
        double min = Double.parseDouble(sc.nextLine());
        System.out.print("Max: ");
        double max = Double.parseDouble(sc.nextLine());
        if (!productService.isExistProductPrice(min, max)) {
            System.out.printf("Không tồn tại sản phẩm có giá bán trong khoảng từ %.2f đến %.2f!\n", min, max);
            return;
        }
        productDAO.searchProductByPrice(min, max);
    }
    private void searchProductByName() {
        System.out.print("Nhập từ khóa để tìm kiếm tên sản phẩm: ");
        String name = sc.nextLine();
        if (!productService.isContainProductName(name)) {
            System.out.println("Không tìm thấy sản phẩm nào theo từ khóa!");
            return;
        }
        System.out.println("Nhập số lượng tồn kho mà bạn cần: ");
        int stock = Integer.parseInt(sc.nextLine());
        if (!productService.isCheckProductStock(name, stock)) {
            System.out.printf("Sản phẩm %s không đủ số lượng tồn kho!\n", name);
            return;
        }
        productDAO.searchProductByName(name, stock);
    }
}
