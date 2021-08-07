package com.example.ordersystem.controller;

import com.example.ordersystem.model.Account;
import com.example.ordersystem.model.AccountRole;
import com.example.ordersystem.service.AccountService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private AccountService accountService;
    
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    // ModelMap is used to parse objects from controller to html through thymeleaf

    @RequestMapping(value="registration", method=RequestMethod.GET)
    public String getForm(ModelMap model){
        model.addAttribute("account", new Account());
        return "registration";
    }

    @RequestMapping(value="registration", method=RequestMethod.POST)
    public String register(@ModelAttribute(value="account") Account account, Model model){
        model.addAttribute("account", account);
        account.setAccountRole(AccountRole.USER);
        accountService.signUpAccount(account);
        return "redirect:/login";
    }

    @RequestMapping(value="user", method=RequestMethod.GET)
    public String getWelcomePage(ModelMap model){
        model.addAttribute("account", new Account());
        return "loggedin_index";
    }

    @RequestMapping(value="user/update", method=RequestMethod.GET)
    public String getUpdateAccountForm(ModelMap model){
        // Get current account authorization and get that account
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInAcc = (Account)auth.getPrincipal();
        model.addAttribute("account", loggedInAcc);
        return "update_account";
    }

    @RequestMapping(value="user/update", method=RequestMethod.POST)
    public String update(@Valid Account account){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInAcc = (Account)auth.getPrincipal();
        Long userId = loggedInAcc.getId();
        account.setId(userId);
        accountService.updateAccount(userId, account);
        return "redirect:/logout" ;
    }

    @RequestMapping(value="user/update/password", method=RequestMethod.POST)
    public String updatePassword(@Valid Account account){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInAcc = (Account)auth.getPrincipal();
        Long userId = loggedInAcc.getId();
        account.setId(userId);

        accountService.changePassword(userId, account.getPassword());
        return "redirect:/logout";
    }

    @RequestMapping(value="user/delete", method=RequestMethod.POST)
    public String deleteAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInAcc = (Account)auth.getPrincipal();
        Long userId = loggedInAcc.getId();
        accountService.deleteAccount(userId);
        return "redirect:/logout";
    }

    @RequestMapping(value="admin/account-management", method=RequestMethod.GET)
    public String showAccountManagementSystem(ModelMap model){
        List<Account> accountList = accountService.getAllAccounts();
        model.addAttribute("accountList",accountList);
        return "account_list";
    }

    @RequestMapping(value="admin/account-management/make-admin/{id}", method= RequestMethod.GET)
    public String makeAccountAdmin(@PathVariable Long id){
        accountService.setAccountRole(id, AccountRole.ADMIN);
        return "redirect:/admin/account-management";
    }

    @RequestMapping(value="admin/account-management/revoke-admin/{id}", method= RequestMethod.GET)
    public String revokeAccountAdmin(@PathVariable Long id){
        accountService.setAccountRole(id, AccountRole.USER);
        return "redirect:/admin/account-management";
    }
}
