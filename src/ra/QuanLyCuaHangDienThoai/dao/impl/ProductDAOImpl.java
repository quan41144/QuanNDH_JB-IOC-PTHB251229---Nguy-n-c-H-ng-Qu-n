package ra.QuanLyCuaHangDienThoai.dao.impl;

import ra.QuanLyCuaHangDienThoai.dao.IProductDAO;
import ra.QuanLyCuaHangDienThoai.model.Product;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements IProductDAO<Product> {
    @Override
    public void addProduct(Product product) {
        String sql = "select * from add_product(?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, product.getName());
            cs.setString(2, product.getBrand());
            cs.setDouble(3, product.getPrice());
            cs.setInt(4, product.getStock());
            cs.execute();
            System.out.println("Added product successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void infoProduct(int id) {
        String sql = "select * from info_product(?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("ID Product: %d | name: %s | brand: %s | price: %.2f | stock: %d",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void updateProduct(Product product) {
        String sql = "select * from update_product(?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, product.getId());
            cs.setString(2, product.getName());
            cs.setString(3, product.getBrand());
            cs.setDouble(4, product.getPrice());
            cs.setInt(5, product.getStock());
            cs.execute();
            System.out.println("Updated product successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "select * from delete_product(?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.execute();
            System.out.println("Deleted product successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public List<Product> listProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "select * from list_product()";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(new Product(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("stock")));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Product> searchProductByBrand(String brand) {
        List<Product> list = new ArrayList<>();
        String sql = "select * from search_product_by_brand(?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, brand);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                       rs.getInt("id"),
                       rs.getString("name"),
                       rs.getString("brand"),
                       rs.getDouble("price"),
                       rs.getInt("stock")
                    ));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Product> searchProductByPrice(double begin, double end) {
        List<Product> list = new ArrayList<>();
        String sql = "select * from search_product_by_price(?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, begin);
            ps.setDouble(2, end);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    ));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Product> searchProductByName(String name, int stock) {
        List<Product> list = new ArrayList<>();
        String sql = "select * from search_product_by_name(?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, stock);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    ));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return list;
    }
    @Override
    public Product getProductById(int id) {
        String sql = "select * from info_product(?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return null;
    }
}
