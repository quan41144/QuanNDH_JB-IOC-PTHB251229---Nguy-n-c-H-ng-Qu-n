package ra.QuanLyCuaHangDienThoai.business.impl;

import ra.QuanLyCuaHangDienThoai.business.IInvoiceService;
import ra.QuanLyCuaHangDienThoai.model.Invoice;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceServiceImpl implements IInvoiceService<Invoice> {
    @Override
    public boolean isExistCustomerId(int id) {
        String sql = "select * from isExist_customer_id(?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
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
