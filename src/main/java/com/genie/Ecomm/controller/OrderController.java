package com.genie.Ecomm.controller;


import com.genie.Ecomm.dto.OrderDto;
import com.genie.Ecomm.model.OrderRequest;
import com.genie.Ecomm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @PostMapping("/place/{userId}")
    public OrderDto placeOrder(@PathVariable long userId, @RequestBody OrderRequest orderRequest){
      return orderService.placeOrder(userId,orderRequest.getProductsquantity(),orderRequest.getTotalamt());
    }


    @GetMapping("/all-orders")
    public List<OrderDto> getAllOrders(){
       return orderService.getAllOrders();
    }

    @GetMapping("/user/{UserId}")
    public List <OrderDto> getOrderByUser(@PathVariable long UserId){
       return orderService.getOrderByUser(UserId);

    }


}
