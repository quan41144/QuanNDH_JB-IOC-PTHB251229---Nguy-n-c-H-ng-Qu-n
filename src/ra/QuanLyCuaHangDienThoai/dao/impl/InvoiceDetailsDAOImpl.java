package ra.QuanLyCuaHangDienThoai.dao.impl;

import ra.QuanLyCuaHangDienThoai.dao.IInvoiceDetailsDAO;
import ra.QuanLyCuaHangDienThoai.model.InvoiceDetails;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        System.out.println("Invoice added successfully");
    }

    @Override
    public List<InvoiceDetails> getInvoiceDetails(int invoiceId) {
        List<InvoiceDetails> list = new ArrayList<>();

        return List.of();
    }
}
