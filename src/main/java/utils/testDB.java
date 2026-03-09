/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author HP - Gia Khiêm
 */
public class testDB {
    public static void main(String[] args) {
        DBContext db = new DBContext(); // Tạo kết nối
        if (db.conn != null) {
            System.out.println("Kết nối SQL Server thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
