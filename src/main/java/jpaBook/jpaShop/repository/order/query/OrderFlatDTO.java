package jpaBook.jpaShop.repository.order.query;

import jpaBook.jpaShop.domain.Address;
import jpaBook.jpaShop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderFlatDTO {
    private Long orderId;
    private String name;
    private String orderDate;
    private OrderStatus orderStatus;
    private Address address;

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderFlatDTO(Long orderId, String name, String orderDate, OrderStatus orderStatus, Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
