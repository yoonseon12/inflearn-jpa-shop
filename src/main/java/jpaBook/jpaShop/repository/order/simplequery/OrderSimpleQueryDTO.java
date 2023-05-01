package jpaBook.jpaShop.repository.order.simplequery;

import jpaBook.jpaShop.domain.Address;
import jpaBook.jpaShop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSimpleQueryDTO {
    private Long orderId;
    private String name;
    private String orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDTO(Long orderId, String name, String orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
