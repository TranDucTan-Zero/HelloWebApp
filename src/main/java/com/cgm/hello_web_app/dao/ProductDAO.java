package com.cgm.hello_web_app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cgm.hello_web_app.eitities.Product;

public class ProductDAO {
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String url = "jdbc:mysql://localhost:3306/webapp";
    private String user = "root";
    private String password = "@Trantan24";

    public ArrayList<Product> getLatestProductList() {
        Connection conn = null;
        String sql = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Product> list = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, password);

            sql = "select * from product";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String image = rs.getString("image");

                Product product = new Product(id, image, name, price);
                list.add(product);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
    
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO product (image, name, price) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {
    
            pst.setString(1, product.getImage());
            pst.setString(2, product.getName());
            pst.setDouble(3, product.getPrice());
    
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Phương thức cập nhật thông tin của một sản phẩm trong cơ sở dữ liệu
    public boolean updateProduct(int id, Product product) {
        String sql = "UPDATE product SET image=?, name=?, price=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, product.getImage());
            pst.setString(2, product.getName());
            pst.setDouble(3, product.getPrice());
            pst.setInt(4, id);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa một sản phẩm khỏi cơ sở dữ liệu dựa trên ID
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
