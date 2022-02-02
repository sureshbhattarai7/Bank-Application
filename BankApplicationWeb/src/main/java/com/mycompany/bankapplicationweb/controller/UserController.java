/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankapplicationweb.controller;

import com.mycompany.bankapplicationweb.command.LoginCommand;
import com.mycompany.bankapplicationweb.dao.UserDAO;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Suresh
 */
@Controller
public class UserController {

    @Autowired
    UserDAO ud;

    @RequestMapping(value={"/", "/login"})
    public String LoginForm(Model m, HttpSession session) {
        m.addAttribute("command", new LoginCommand());
        session.invalidate();
        return "login";
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public String loginProcess(@ModelAttribute("command") LoginCommand lc, Model m, HttpSession session) {
        if (ud.login(lc.getuName(), lc.getpWord())) {
            session.setAttribute("uName", lc.getuName());
            return "redirect:dashboard";
        } else {
            m.addAttribute("message", "Invalid Credentials");
            return "login";
        }
    }

    @RequestMapping("/dashboard")
    public String dash(Model m, HttpSession session) {
        if (session.getAttribute("uName") != null) {
            m.addAttribute("title", "Bank Application");
            m.addAttribute("message", "Login Successful");
            return "dashboard";
        } else {
            return "redirect:login";
        }
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:login";
    }
}
