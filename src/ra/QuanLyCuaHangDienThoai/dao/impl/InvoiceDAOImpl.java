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
        String sql = "call add_invoice(?, ?)";
        int p_invoice_id = -1;
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, customer_id);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();
            p_invoice_id = cs.getInt(2);
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
                            rs.getInt("out_id"),
                            rs.getInt("out_customer_id"),
                            rs.getTimestamp("out_created_at").toLocalDateTime(),
                            rs.getDouble("out_total_amount")
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
    public void addFinalInvoice(int invoice_id) {
        String sql = "call add_final_invoice(?)";
        try (Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, invoice_id);
            cs.execute();
            System.out.println("Invoice added successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void deleteInvoice(int invoice_id) {
        String sql = "call delete_invoice(?)";
        try (Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, invoice_id);
            cs.execute();
            System.out.println("Deleted invoice successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
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
                       rs.getInt("out_id"),
                       rs.getInt("out_customer_id"),
                       rs.getTimestamp("out_created_at").toLocalDateTime(),
                       rs.getDouble("out_total_amount")
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

    @Override
    public double dailyRevenue(LocalDate date) {
        String sql = "select * from daily_revenue(?::date)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   return rs.getDouble(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public double monthlyRevenue(int month, int year) {
        String sql = "select * from monthly_revenue(?, ?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
           ps.setInt(1, month);
           ps.setInt(2, year);
           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                   return rs.getDouble(1);
               }
           }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public double yearRevenue(int year) {
        String sql = "select * from year_revenue(?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return 0;
    }
}
