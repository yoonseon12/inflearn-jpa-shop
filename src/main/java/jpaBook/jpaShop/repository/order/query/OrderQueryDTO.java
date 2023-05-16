package jpaBook.jpaShop.repository.order.query;

import jpaBook.jpaShop.domain.Address;
import jpaBook.jpaShop.domain.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDTO {

    private Long orderId;
    private String name;
    private String orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDTO> orderItems;

    public OrderQueryDTO(Long orderId, String name, String orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDTO(Long orderId, String name, String orderDate, OrderStatus orderStatus, Address address, List<OrderItemQueryDTO> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }
}
