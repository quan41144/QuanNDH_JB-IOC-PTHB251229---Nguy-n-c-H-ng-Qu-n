package ra.QuanLyCuaHangDienThoai.business.impl;

import ra.QuanLyCuaHangDienThoai.business.IInvoiceDetailsService;
import ra.QuanLyCuaHangDienThoai.model.InvoiceDetails;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceDetailsServiceImpl implements IInvoiceDetailsService<InvoiceDetails> {
    @Override
    public boolean checkProductStock(int productId, int quantity) {
        String sql = "select * from check_product_stock(?, ?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, productId);
            ps.setInt(2, quantity);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
        return false;
    }
}
