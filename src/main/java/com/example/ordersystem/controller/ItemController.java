package com.example.ordersystem.controller;

import com.example.ordersystem.model.Account;
import com.example.ordersystem.model.Cart;
import com.example.ordersystem.model.Item;
import com.example.ordersystem.model.Pagination;
import com.example.ordersystem.service.AccountService;
import com.example.ordersystem.service.CartService;
import com.example.ordersystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;

    @GetMapping("/shop")
    public String listAll(ModelMap model, @RequestParam(defaultValue = "1") int page) {

        // The number of total items
        int totalNum = itemService.findTotal();

        Pagination pagination = new Pagination(totalNum, page);

        int beginIndex = pagination.getBeginIndex();

        // Max num of items in a page
        int pageSize = pagination.getPageSize();

        List<Item> shopList = itemService.findListPaging(beginIndex, pageSize);

        model.addAttribute("shopList", shopList);
        model.addAttribute("pagination", pagination);

        float cartSum = 0;
        int cartQty = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            Account loggedInAcc = (Account)auth.getPrincipal();
            Long userId = loggedInAcc.getId();

            Account user = accountService.getAccountById(userId);
            List<Cart> cartList = cartService.getAllCarts(user);

            cartQty = cartList.size();
            for (Cart cart : cartList) {
                cartSum += cart.getSmallSum();
            }
        }catch (ClassCastException e){
            System.out.println("Not account");
        }
        model.addAttribute("cartSum",cartSum);
        model.addAttribute("cartQty",cartQty);

        return "shop";
    }

    @GetMapping("/shop-details")
    public String readDetail(ModelMap model, @RequestParam("id") Long id) throws Exception {

        model.addAttribute("shopDetail", itemService.getItem(id).get());

        float cartSum = 0;
        int cartQty = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            Account loggedInAcc = (Account)auth.getPrincipal();
            Long userId = loggedInAcc.getId();

            Account user = accountService.getAccountById(userId);
            List<Cart> cartList = cartService.getAllCarts(user);

            cartQty = cartList.size();
            for (Cart cart : cartList) {
                cartSum += cart.getSmallSum();
            }
        }catch (ClassCastException e){
            System.out.println("Not account");
        }
        model.addAttribute("cartSum",cartSum);
        model.addAttribute("cartQty",cartQty);

        return "shop-details";
    }

    @GetMapping(path = "/item-form")
    public String itemForm(ModelMap model){
        float cartSum = 0;
        int cartQty = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            Account loggedInAcc = (Account)auth.getPrincipal();
            Long userId = loggedInAcc.getId();

            Account user = accountService.getAccountById(userId);
            List<Cart> cartList = cartService.getAllCarts(user);

            cartQty = cartList.size();
            for (Cart cart : cartList) {
                cartSum += cart.getSmallSum();
            }
        }catch (ClassCastException e){
            System.out.println("Not account");
        }
        model.addAttribute("cartSum",cartSum);
        model.addAttribute("cartQty",cartQty);
        return "item-form";
    }

    @GetMapping(path = "/item-list")
    public String itemList(ModelMap model){
        List<Item> itemList = itemService.getAllItems();
        model.addAttribute("itemList",itemList);
        float cartSum = 0;
        int cartQty = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            Account loggedInAcc = (Account)auth.getPrincipal();
            Long userId = loggedInAcc.getId();

            Account user = accountService.getAccountById(userId);
            List<Cart> cartList = cartService.getAllCarts(user);

            cartQty = cartList.size();
            for (Cart cart : cartList) {
                cartSum += cart.getSmallSum();
            }
        }catch (ClassCastException e){
            System.out.println("Not account");
        }
        model.addAttribute("cartSum",cartSum);
        model.addAttribute("cartQty",cartQty);
        return "item-list";
    }
}
