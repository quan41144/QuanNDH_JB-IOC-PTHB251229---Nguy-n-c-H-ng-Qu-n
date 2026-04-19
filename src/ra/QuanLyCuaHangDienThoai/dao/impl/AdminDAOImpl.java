package ra.QuanLyCuaHangDienThoai.dao.impl;

import ra.QuanLyCuaHangDienThoai.dao.IAdminDAO;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {
    @Override
    public boolean checkLogin(String username, String password) {
        String sql = "select * from check_admin(?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
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
