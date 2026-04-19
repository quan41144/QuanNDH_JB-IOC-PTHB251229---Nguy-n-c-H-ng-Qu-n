package ra.QuanLyCuaHangDienThoai.business.impl;

import ra.QuanLyCuaHangDienThoai.business.IAdminService;
import ra.QuanLyCuaHangDienThoai.dao.IAdminDAO;
import ra.QuanLyCuaHangDienThoai.dao.impl.AdminDAOImpl;

public class AdminServiceImpl implements IAdminService {
    private IAdminDAO adminDAO = new AdminDAOImpl();
    @Override
    public boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        return adminDAO.checkLogin(username, password);
    }
}
