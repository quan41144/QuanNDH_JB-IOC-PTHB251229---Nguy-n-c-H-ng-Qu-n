package ra.QuanLyCuaHangDienThoai.dao.impl;

import ra.QuanLyCuaHangDienThoai.dao.IInvoiceDAO;
import ra.QuanLyCuaHangDienThoai.model.Invoice;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements IInvoiceDAO<Invoice> {
    @Override
    public void addInvoice(int customer_id) {
        String sql = "select * from add_invoice(?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, customer_id);
            cs.execute();
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public Invoice infoNewInvoice() {
        String sql = "select * from info_new_invoice()";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Invoice(
                            rs.getInt("id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getDouble("total_amount")
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

    @Override
    public List<Invoice> listInvoices() {
        List<Invoice> list = new ArrayList<>();
        String sql = "select * from list_invoice()";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Invoice(
                       rs.getInt("id"),
                       rs.getInt("customer_id"),
                       rs.getTimestamp("created_at").toLocalDateTime(),
                       rs.getDouble("total_amount")
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
    public List<Invoice> searchInvoiceByCustomerName(String CustomerName) {
        List<Invoice> list = new ArrayList<>();
        String sql = "select * from search_invoice_by_customer_name(?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, CustomerName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Invoice(
                            rs.getInt("id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getDouble("total_amount")
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
    public List<Invoice> searchInvoiceByDate(LocalDate date) {
        List<Invoice> list = new ArrayList<>();
        String sql = "select * from search_invoice_by_date(?::date)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Invoice(
                            rs.getInt("id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getDouble("total_amount")
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
}
