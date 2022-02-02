/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankapplicationweb.dao;

import static com.mycompany.bankapplicationweb.dao.DatabaseVariable.db;
import com.mycompany.bankapplicationweb.domain.Accounts;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Suresh
 */
@Repository
public class AccountDAOImplementation implements AccountDAO {

    @Override
    public boolean addAccount(int accountNumber, String accountName, int accountBalance) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (findAccount(accountNumber) == null) {
            String sql = "INSERT INTO `bankdb`.`account` (`accountNumber`, `accountName`, `accountBalance`) VALUES ('" + accountNumber + "', '" + accountName + "', '" + accountBalance + "');";
            return db.iud(sql);
        }
        return false;
    }

    @Override
    public Accounts findAccount(int accountNumber) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String sql = "select * from bankdb.account where accountNumber=" + accountNumber + ";";
        ResultSet rs = db.select(sql);
        try {
            while (rs.next()) {
                Accounts acc = new Accounts(rs.getInt(1), rs.getString(2), rs.getInt(3));
                return acc;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public void checkBalance(int accountNumber) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (findAccount(accountNumber) != null) {
            Accounts acc = findAccount(accountNumber);
            System.out.println("Balance is :" + acc.getAccountBalance());
        } else {
            System.out.println("Account doesn't exist");
        }
    }

    @Override
    public boolean depositeAmount(int accountNumber, int depositeBalance) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (findAccount(accountNumber) != null) {
            Accounts acc = findAccount(accountNumber);
            acc.setAccountBalance(acc.getAccountBalance() + depositeBalance);
            String sql = "UPDATE `bankdb`.`account` SET `accountBalance` = '" + acc.getAccountBalance() + "' WHERE (`accountNumber` = '" + accountNumber + "');";
            return db.iud(sql);
        }
        return false;
    }

    @Override
    public int withdrawAmount(int accountNumber, int withdrawBalance) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (findAccount(accountNumber) != null) {
            Accounts acc = findAccount(accountNumber);
            if (acc.getAccountBalance() > withdrawBalance) {
                acc.setAccountBalance(acc.getAccountBalance() - withdrawBalance);
                String sql = "UPDATE `bankdb`.`account` SET `accountBalance` = '" + acc.getAccountBalance() + "' WHERE (`accountNumber` = '" + accountNumber + "');";
                if (db.iud(sql)) {
                    return 1;
                }
            } else {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public int transferAmount(int senderAccountNumber, int recieverAccountNumber, int transferBalance) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if ((findAccount(senderAccountNumber) != null) && (findAccount(recieverAccountNumber) != null)) {
            Accounts acc1 = findAccount(senderAccountNumber);
            Accounts acc2 = findAccount(recieverAccountNumber);
            if (acc1.getAccountBalance() > transferBalance) {
                acc1.setAccountBalance(acc1.getAccountBalance() - transferBalance);
                acc2.setAccountBalance(acc2.getAccountBalance() + transferBalance);
                String sql1 = "UPDATE `bankdb`.`account` SET `accountBalance` = '" + acc1.getAccountBalance() + "' WHERE (`accountNumber` = '" + senderAccountNumber + "');";
                String sql2 = "UPDATE `bankdb`.`account` SET `accountBalance` = '" + acc2.getAccountBalance() + "' WHERE (`accountNumber` = '" + recieverAccountNumber + "');";
                if (db.iud(sql1) && db.iud(sql2)) {
                    return 1;
                }
            } else {
                return -1;
            }
        }
        return 0;
    }

}
