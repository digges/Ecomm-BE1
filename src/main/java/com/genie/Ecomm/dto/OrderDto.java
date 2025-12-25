package com.genie.Ecomm.dto;

import com.genie.Ecomm.model.OrderItem;

import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;
    private double totalamt;
    private String status;
    private Date orderdate;
    private String username;
    private String email;

    private List<OrderItemDTO> orderitems;

    public OrderDto(Long id, double totalamt, String status, Date orderdate, String username, String email, List<OrderItemDTO> orderitems) {
        this.id = id;
        this.totalamt = totalamt;
        this.status = status;
        this.orderdate = orderdate;
        this.username = username;
        this.email = email;
        this.orderitems = orderitems;
    }
    public OrderDto(Long id, double totalamt, String status, Date orderdate,  List<OrderItemDTO> orderitems) {
        this.id = id;
        this.totalamt = totalamt;
        this.status = status;
        this.orderdate = orderdate;
        this.orderitems = orderitems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(double totalamt) {
        this.totalamt = totalamt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItemDTO> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<OrderItemDTO> orderitems) {
        this.orderitems = orderitems;
    }
}
