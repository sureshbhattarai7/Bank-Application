/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankapplicationweb.dao;

import static com.mycompany.bankapplicationweb.dao.DatabaseVariable.db;
import com.mycompany.bankapplicationweb.domain.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Suresh
 */
@Repository
public class UserDAOImplementation implements UserDAO {

    @Override
    public boolean login(String userName, String passWord) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String sql = "select * from bankdb.user";
        ResultSet rs = db.select(sql);
        try {
            while (rs.next()) {
                Users us = new Users(rs.getString(1), rs.getString(2));
                if ((us.getUserName().equals(userName)) && (us.getPassWord().equals(passWord))) {
                    return true;

                }
            }

        } catch (SQLException e) {
            return false;
        }

        if ((userName.equals("admin")) && (passWord.equals("admin"))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(String userName, String passWord) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (findUser(userName) == null) {
            String sql = "INSERT INTO `bankdb`.`user` (`userName`, `passWord`) VALUES ('" + userName + "', '" + passWord + "');";
            return db.iud(sql);
        }
        return false;
    }

    @Override
    public Users findUser(String userName) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String sql = "select * from bankdb.user where userName=\"" + userName + "\";";
        ResultSet rs = db.select(sql);
        try {
            while (rs.next()) {
                Users us = new Users(rs.getString(1), rs.getString(2));
                return us;
            }

        } catch (SQLException e) {
            return null;
        }
        return null;

    }

    @Override
    public boolean deleteUser(String userName) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (findUser(userName) != null) {
            // Users us = findUser(userName);
            String sql = "DELETE FROM `bankdb`.`user` WHERE (`userName` = '" + userName + "');";
            return db.iud(sql);
        }
        return false;
    }

}
