package ra.QuanLyCuaHangDienThoai.dao.impl;

import ra.QuanLyCuaHangDienThoai.dao.ICustomerDAO;
import ra.QuanLyCuaHangDienThoai.model.Customer;
import ra.QuanLyCuaHangDienThoai.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO<Customer> {
    @Override
    public void addCustomer(Customer customer) {
        String sql = "call add_customer(?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setString(1, customer.getName());
            cs.setString(2, customer.getPhone());
            cs.setString(3, customer.getEmail());
            cs.setString(4, customer.getAddress());
            cs.execute();
            System.out.println("Customer Added Successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void infoCustomer(int id) {
        String sql = "select * from info_customer(?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("ID Customer: %d | Name: %s | Phone: %s | Email: %s | Address: %s\n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address")
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
    public void updateCustomer(Customer customer) {
        String sql = "call update_customer(?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, customer.getId());
            cs.setString(2, customer.getName());
            cs.setString(3, customer.getPhone());
            cs.setString(4, customer.getEmail());
            cs.setString(5, customer.getAddress());
            cs.execute();
            System.out.println("Customer Updated Successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "call delete_customer(?)";
        try (Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setInt(1, id);
            cs.execute();
            System.out.println("Customer Deleted Successfully");
        }
        catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        String sql = "select * from info_customer(?)";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address")
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
    public List<Customer> listCustomer() {
        List<Customer> customers = new ArrayList<>();
        String sql = "select * from list_customer()";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customers.add(new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address"))
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
        return customers;
    }

    @Override
    public Customer infoNewCustomer() {
        String sql = "select * from info_new_customer()";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address")
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
