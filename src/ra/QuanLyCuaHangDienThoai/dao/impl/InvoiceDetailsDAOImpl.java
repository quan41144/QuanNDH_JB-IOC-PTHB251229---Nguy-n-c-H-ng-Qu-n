package ra.QuanLyCuaHangDienThoai.dao.impl;

import ra.QuanLyCuaHangDienThoai.dao.IInvoiceDetailsDAO;
import ra.QuanLyCuaHangDienThoai.model.InvoiceDetails;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailsDAOImpl implements IInvoiceDetailsDAO<InvoiceDetails> {
    @Override
    public void addInvoiceDetails(InvoiceDetails invoiceDetails) {
        String sql = "select * from add_invoice_details(?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, invoiceDetails.getInvoiceId());
            cs.setInt(2, invoiceDetails.getProductId());
            cs.setInt(3, invoiceDetails.getQuantity());
            cs.execute();
        }
        catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public List<InvoiceDetails> getInvoiceDetails(int invoiceId) {
        List<InvoiceDetails> list = new ArrayList<>();
        String sql = "select * from get_invoice_details(?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new InvoiceDetails(
                            rs.getInt("id"),
                            rs.getInt("invoice_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("unit_price")));
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
        return List.of();
    }
}
