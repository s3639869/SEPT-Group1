package com.example.ordersystem.controller;

import com.example.ordersystem.model.Account;
import com.example.ordersystem.model.Order;
import com.example.ordersystem.service.AccountService;
import com.example.ordersystem.service.ItemService;
import com.example.ordersystem.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * This class is used for routing RESTful endpoints to admin functionalities which consists of
 * view a specific account's details, order histories and order details
 */

@Controller
@AllArgsConstructor
public class AccountDetailsController {
    private AccountService accountService;
    private OrderService orderService;
    private ItemService itemService;

    // Admin view specific user's account details and user's order history
    @RequestMapping(value="account-management/account-details/{id}", method= RequestMethod.GET)
    public String viewUserDetails(@PathVariable Long id, ModelMap model){
        Account accountToView = accountService.getAccountById(id);
        model.addAttribute("account", accountToView);
        List<Order> userOrdersList = orderService.getOrdersByAccountId(id);
        userOrdersList.sort(Comparator.comparing(Order::getId)); // sort order by id number(low to high id number)
        model.addAttribute("orderList", userOrdersList);
        return "account-details";
    }

    // Admin view specific user's order details
    @RequestMapping(value = "account-management/account-details/{id}/order-details/{orderID}", method = RequestMethod.GET)
    public String adminViewOrderDetails(@PathVariable Long id, @PathVariable Long orderID, ModelMap model){
        Account accountToView = accountService.getAccountById(id);
        model.addAttribute("account", accountToView);
        Order orderToView = orderService.getOrderById(orderID).get();
        model.addAttribute("order", orderToView);
        String itemString = orderToView.getItems();
        itemString = itemString.substring(1, itemString.length() - 1);
        String[] items = itemString.split("\\Q},{\\E");
        ArrayList<String[]> itemInfo = new ArrayList<>();
        int totalQuantity = 0;
        for(String item: items) {
            String[] iteminfo = item.split(",");
            iteminfo[0] = itemService.getItem(Long.valueOf(iteminfo[0])).get().getItemName();
            itemInfo.add(iteminfo);
            totalQuantity += Integer.parseInt(iteminfo[2]);
        }
        model.addAttribute("itemInfo", itemInfo);
        model.addAttribute("totalQuantity", totalQuantity);
        return "admin_view_order_details";
    }
}
