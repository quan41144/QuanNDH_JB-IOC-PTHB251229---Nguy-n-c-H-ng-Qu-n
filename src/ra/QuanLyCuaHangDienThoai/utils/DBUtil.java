package ra.QuanLyCuaHangDienThoai.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String url = "jdbc:postgresql://localhost:5432/ioc_be_102";
    private static final String username = "postgres";
    private static final String password = "123456";
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi khác: " + e.getMessage());
        }
        return con;
    }
}
