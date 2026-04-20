package ra.QuanLyCuaHangDienThoai.business.impl;

import ra.QuanLyCuaHangDienThoai.business.ICustomerService;
import ra.QuanLyCuaHangDienThoai.model.Customer;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImpl implements ICustomerService<Customer> {
    @Override
    public boolean isExistPhone(String phone) {
        String sql = "select * from isExit_phone(?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
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
    public boolean isExistEmail(String email) {
        String sql = "select * from isExit_email(?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
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
    public boolean isExistCustomerId(int id) {
        String sql = "select * from isExit_customer_id(?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
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
