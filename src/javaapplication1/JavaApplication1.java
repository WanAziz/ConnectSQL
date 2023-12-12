package javaapplication1;

import static com.mysql.cj.Messages.getString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;
import static javax.swing.UIManager.getInt;

public class JavaApplication1 {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mahasiswa";
    static final String USER = "root";
    static final String PASS = "";
    
    static Scanner scanner = new Scanner(System.in);
        
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;    
    
    static boolean getId(int masukan){
        boolean cek = false;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM mahasiswa";
            rs = stmt.executeQuery(sql);
            while (rs.next()) { 
                if (masukan == rs.getInt("id")){
                    cek = true;
                }
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {}
        return cek;
    }
    
    static void showData(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM mahasiswa";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {                
                System.out.println("ID : " + rs.getInt("id") + " NAMA : " + rs.getString("nama") + " AlAMAT : " + rs.getString("alamat"));
            }
            
            stmt.close();
            conn.close();
        } catch (Exception e) {}
    }
    
    static void inputData(){
        System.out.print("Masukkan nama : ");
        String name = scanner.nextLine();
        System.out.print("Masukkan alamat : ");
        String alamat = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO `mahasiswa` (`id`,`nama`, `alamat`) VALUES (NULL, '"+name+"', '"+alamat+"')";
            stmt.execute(sql);    
            stmt.close();
            conn.close();
        } catch (Exception e) {}
    }
    
    static void updateData(){
        showData();
        System.out.print("Masukkan id : ");
        int idSelect = scanner.nextInt();
        scanner.nextLine();
        
        if(getId(idSelect)){
            System.out.print("Masukkan namaBaru : ");
            String nameNew = scanner.nextLine();
            System.out.print("Masukkan alamatBaru : ");
            String alamatNew = scanner.nextLine();
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                stmt = conn.createStatement();
                String sql = "UPDATE `mahasiswa` SET `nama` = '"+nameNew+"', `alamat` = '"+alamatNew+"' WHERE `mahasiswa`.`id` = "+idSelect;
                stmt.execute(sql);    
                stmt.close();
                conn.close();
            } catch (Exception e) {}
        }else{
                System.out.println("id salah");
        }
    }
    
    public static void main(String[] args) {
        while (true) {
            System.out.println("=== Data Mahasiswa ===");
            System.out.println("1. Input Data");
            System.out.println("2. Tampil Data");
            System.out.println("3. Update Data");
            System.out.println("0. Keluar");
            System.out.print("Pilih nomer menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
                inputData();
                break;
            case 2:
                showData();
                break;
            case 3:
                updateData();
                break;
            case 0:
                System.exit(0);
            default:
                System.out.println(
                    "Pilihan tidak valid. Silakan coba lagi.");
                break;
            }
        }       
    }
}
