package com.genie.Ecomm.service;


import com.genie.Ecomm.dto.OrderDto;
import com.genie.Ecomm.dto.OrderItemDTO;
import com.genie.Ecomm.model.OrderItem;
import com.genie.Ecomm.model.Orders;
import com.genie.Ecomm.model.Product;
import com.genie.Ecomm.model.User;
import com.genie.Ecomm.repo.OrderRepository;
import com.genie.Ecomm.repo.ProductRepository;
import com.genie.Ecomm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {

    @Autowired
    public UserRepository userrepo;
    @Autowired
    public ProductRepository productrepo;
     @Autowired
    private OrderRepository orderrepo;
    public OrderDto placeOrder(long userId, Map<Long, Integer> productsquantity, double totalamt) {
      User user= userrepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Orders orders=new Orders();
        orders.setUser(user);
        orders.setOrderdate(new Date());
        orders.setStatus("Pending");
        orders.setTotalamt(totalamt);
        List<OrderItem>  OrderItems=new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS=new ArrayList<>();
        for(Map.Entry<Long,Integer> entry:productsquantity.entrySet()){
            Product product=productrepo.findById(entry.getKey()).orElseThrow(()->new RuntimeException("Product not found"));
            OrderItem orderItem=new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(orders);
            orderItem.setQuantity(entry.getValue());
            OrderItems.add(orderItem);
            orderItemDTOS.add(new OrderItemDTO(product.getName(),entry.getValue(),product.getPrice()));

        }
        orders.setOrderitems(OrderItems);
        Orders save = orderrepo.save(orders);
        return new OrderDto(save.getId(),save.getTotalamt(),save.getStatus(),save.getOrderdate(),orderItemDTOS);

    }

    public List<OrderDto> getAllOrders() {
        List<Orders> allOrdersWithUsers = orderrepo.findAllOrdersWithUser();
        return allOrdersWithUsers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDto convertToDTO(Orders orders) {
        List<OrderItemDTO> OrderItems = orders.getOrderitems().stream().map(item -> new OrderItemDTO(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice())).collect(Collectors.toList());
        return new OrderDto(
                orders.getId(),
                orders.getTotalamt(),
                orders.getStatus(),
                orders.getOrderdate(),
                orders.getUser() != null ? orders.getUser().getName() : "Unknown Status",
                orders.getUser()!=null?orders.getUser().getEmail():"Unknown",
                OrderItems
        );

    }

    public List<OrderDto> getOrderByUser(long userId) {
        Optional<User> UserOp = userrepo.findById(userId);
        if(UserOp.isEmpty()) {
            throw new RuntimeException("User not found");
        } else {
            User user=UserOp.get();
            List<Orders> byUser = orderrepo.findByUser(user);
            return byUser.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
    }
}
