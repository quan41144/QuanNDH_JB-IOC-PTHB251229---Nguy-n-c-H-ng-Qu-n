package ra.QuanLyCuaHangDienThoai.business.impl;

import ra.QuanLyCuaHangDienThoai.business.IProductService;
import ra.QuanLyCuaHangDienThoai.model.Product;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductServiceImpl implements IProductService<Product> {

    @Override
    public boolean isExistProductName(String name) {
        String sql = "select * from isExist_product_name(?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql))
        {
            cs.setString(1, name);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isContainProductName(String name) {
        String sql = "select * from isContain_product_name(?)";
        try (Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql))
        {
            cs.setString(1, name);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isCheckProductStock(String name, int stock) {
        String sql = "select * from isCheck_product_stock(?, ?)";
        try (Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setString(1, name);
            cs.setInt(2, stock);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isExistProductId(int id) {
        String sql = "select * from isExist_product_id(?)";
        try (Connection con = DBUtil.getConnection();
        CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isExistProductBrand(String brand) {
        String sql = "select * from isExist_product_brand(?)";
        try (Connection con = DBUtil.getConnection();
        CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, brand);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isExistProductPrice(double begin, double end) {
        String sql = "select * from isExist_product_price(?, ?)";
        try (Connection con = DBUtil.getConnection();
        CallableStatement cs = con.prepareCall(sql)) {
            cs.setDouble(1, begin);
            cs.setDouble(2, end);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isCheckPriceAndStock(double price, int stock) {
        String sql = "select * from isCheck_price_stock(?, ?)";
        try (Connection con = DBUtil.getConnection();
        CallableStatement cs = con.prepareCall(sql)) {
            cs.setDouble(1, price);
            cs.setInt(2, stock);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return false;
    }
}
