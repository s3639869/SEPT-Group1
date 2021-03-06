package com.example.ordersystem;

import com.example.ordersystem.model.*;
import com.example.ordersystem.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class is used for populating the database with dummy accounts and sample items in the shop
 * It will only run if the app.init-db property in the application.properties file is set to true
 * Important: It should only be run once when running the application for the first time,
 * which means the app.init-db property should be set to false the rest of the time.
 * Otherwise, conflicts may occur in the database.
 */
@Component
@ConditionalOnProperty(name = "app.init-db", havingValue="true")
@AllArgsConstructor
public class DbInitializer implements CommandLineRunner {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemImageService itemImageService;

    @Override
    public void run(String... args){


        //Initialize admin account
        Account admin = new Account("John", "Doe", "123 Tech Street", "0708563876", "admin@gmail.com", "admin", AccountRole.ADMIN);

        accountService.signUpAccount(admin);
        accountService.setAccountRole(admin.getId(), AccountRole.ADMIN);

        //Initialize user account
        Account user1 = new Account("Jeffrey", "Babble", "456 Flower Lane", "0903682439", "user@gmail.com", "password", AccountRole.USER);
        accountService.signUpAccount(user1);

        //Initialize another account with a real email for testing
        Account user2 = new Account("Sarah", "Lenon", "789 Queen Road", "0908142756", "cakeorder.user@gmail.com", "123", AccountRole.ADMIN);
        accountService.signUpAccount(user2);
        accountService.setAccountRole(user2.getId(), AccountRole.ADMIN);

        //Populate sample items in the shop
        Item item1 = new Item("Cream cupcake", "A delicious cupcake with vanilla cream to brighten your day", "product-1.jpg", new BigDecimal("21.00"), "Cupcake",true);
        Item item2 = new Item("Chocolate cupcake","A delicious cupcake with chocolate toppings to sweeten your day", "product-2.jpg", new BigDecimal("22.00"),"Cupcake",true);
        Item item3 = new Item("Unicorn Cake","A colorfully decorated cake. Brings some magical vanilla and cream to your life","cake-1.jpg",new BigDecimal("40.00"),"Cake",true);
        Item item4 = new Item("Chocolate & Raspberry Cake","The ultimate combo. A creamy cake covered with sweet chocolate and fresh raspberries","cake-2.jpg|cake-2-2.jpg",new BigDecimal("35.00"),"Cake",true);
        Item item5 = new Item("Lemon Flower Sandwich Biscuits","Two biscuits sandwiched together. The buttery flavor and zingy taste of lemon await you","biscuit-1.jpg",new BigDecimal("10.00"),"Biscuit",true);
        Item item6 = new Item("Fall Leaves Sugar Biscuits","Colorful biscuits with a sprinkle of sugar to sweeten the season","biscuit-2.jpg",new BigDecimal("15.50"),"Biscuit",true);
        Item item7 = new Item("Pink Strawberry Donut","A fluffy and pink donut with colorful sprinkles. Yummy","donut-1.jpeg",new BigDecimal("15.00"),"Donut",true);
        Item item8 = new Item("Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item9 = new Item("1Cream cupcake", "A delicious cupcake with vanilla cream to brighten your day", "product-1.jpg", new BigDecimal("21.00"), "Cupcake",true);
        Item item10 = new Item("1Chocolate cupcake","A delicious cupcake with chocolate toppings to sweeten your day", "product-2.jpg", new BigDecimal("22.00"),"Cupcake",true);
        Item item11 = new Item("1Unicorn Cake","A colorfully decorated cake. Brings some magical vanilla and cream to your life","cake-1.jpg",new BigDecimal("40.00"),"Cake",true);
        Item item12 = new Item("1Chocolate & Raspberry Cake","The ultimate combo. A creamy cake covered with sweet chocolate and fresh raspberries","cake-2.jpg|cake-2-2.jpg",new BigDecimal("35.00"),"Cake",true);
        Item item13 = new Item("1Lemon Flower Sandwich Biscuits","Two biscuits sandwiched together. The buttery flavor and zingy taste of lemon await you","biscuit-1.jpg",new BigDecimal("10.00"),"Biscuit",true);
        Item item14 = new Item("1Fall Leaves Sugar Biscuits","Colorful biscuits with a sprinkle of sugar to sweeten the season","biscuit-2.jpg",new BigDecimal("15.50"),"Biscuit",true);
        Item item15 = new Item("1Pink Strawberry Donut","A fluffy and pink donut with colorful sprinkles. Yummy","donut-1.jpeg",new BigDecimal("15.00"),"Donut",true);
        Item item16 = new Item("1Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item17 = new Item("2Cream cupcake", "A delicious cupcake with vanilla cream to brighten your day", "product-1.jpg", new BigDecimal("21.00"), "Cupcake",true);
        Item item18 = new Item("2Chocolate cupcake","A delicious cupcake with chocolate toppings to sweeten your day", "product-2.jpg", new BigDecimal("22.00"),"Cupcake",true);
        Item item19 = new Item("2Unicorn Cake","A colorfully decorated cake. Brings some magical vanilla and cream to your life","cake-1.jpg",new BigDecimal("40.00"),"Cake",true);
        Item item20 = new Item("2Chocolate & Raspberry Cake","The ultimate combo. A creamy cake covered with sweet chocolate and fresh raspberries","cake-2.jpg|cake-2-2.jpg",new BigDecimal("35.00"),"Cake",true);
        Item item21 = new Item("2Lemon Flower Sandwich Biscuits","Two biscuits sandwiched together. The buttery flavor and zingy taste of lemon await you","biscuit-1.jpg",new BigDecimal("10.00"),"Biscuit",true);
        Item item22 = new Item("2Fall Leaves Sugar Biscuits","Colorful biscuits with a sprinkle of sugar to sweeten the season","biscuit-2.jpg",new BigDecimal("15.50"),"Biscuit",true);
        Item item23 = new Item("2Pink Strawberry Donut","A fluffy and pink donut with colorful sprinkles. Yummy","donut-1.jpeg",new BigDecimal("15.00"),"Donut",true);
        Item item24 = new Item("24Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item25 = new Item("25Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item26 = new Item("26Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item27 = new Item("27Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item28 = new Item("28Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item29 = new Item("29Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item30 = new Item("30Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item31 = new Item("31Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item32 = new Item("32Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item33 = new Item("33Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item34 = new Item("34Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item35 = new Item("35Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item36 = new Item("36Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item37 = new Item("37Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item38 = new Item("38Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item39 = new Item("39Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item40 = new Item("40Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);
        Item item41 = new Item("41Smiley Face Donut","Look how happy that donut is. Doesn't it put a smile on your face?","donut-2.jpg|donut-2-2.jpg|donut-2-3.jpg|donut-2-4.png",new BigDecimal("17.00"),"Donut",true);

        itemService.saveItem(item1);
        itemService.saveItem(item2);
        itemService.saveItem(item3);
        itemService.saveItem(item4);
        itemService.saveItem(item5);
        itemService.saveItem(item6);
        itemService.saveItem(item7);
        itemService.saveItem(item8);
        itemService.saveItem(item9);
        itemService.saveItem(item10);
        itemService.saveItem(item11);
        itemService.saveItem(item12);
        itemService.saveItem(item13);
        itemService.saveItem(item14);
        itemService.saveItem(item15);
        itemService.saveItem(item16);
        itemService.saveItem(item17);
        itemService.saveItem(item18);
        itemService.saveItem(item19);
        itemService.saveItem(item20);
        itemService.saveItem(item21);
        itemService.saveItem(item22);
        itemService.saveItem(item23);
        itemService.saveItem(item24);

        itemService.saveItem(item25);
        itemService.saveItem(item26);
        itemService.saveItem(item27);
        itemService.saveItem(item28);
        itemService.saveItem(item29);
        itemService.saveItem(item30);
        itemService.saveItem(item31);
        itemService.saveItem(item32);
        itemService.saveItem(item33);
        itemService.saveItem(item34);
        itemService.saveItem(item35);
        itemService.saveItem(item36);
        itemService.saveItem(item37);
        itemService.saveItem(item38);
        itemService.saveItem(item39);
        itemService.saveItem(item40);
        itemService.saveItem(item41);


        List<Item> itemList = itemService.getAllItems();

        //Run a for loop to save each sample item's images in the database
        for(Item item: itemList){
            String[] strings = item.getItemImage().split("[|]");
            for(String imgname: strings){
                try{
                    byte[] byteArray = Files.readAllBytes(Paths.get(("src\\main\\resources\\static\\img\\shop\\"+imgname).replace("\\", File.separator)));
                    MockMultipartFile file = new MockMultipartFile("file", imgname, "multipart/form-data", byteArray);
                    itemImageService.saveItemImage(item.getId(),file);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
