package jpaBook.jpaShop.api;

import jpaBook.jpaShop.domain.Address;
import jpaBook.jpaShop.domain.Order;
import jpaBook.jpaShop.domain.OrderItem;
import jpaBook.jpaShop.domain.OrderStatus;
import jpaBook.jpaShop.repository.OrderRepository;
import jpaBook.jpaShop.repository.OrderSearch;
import jpaBook.jpaShop.repository.order.query.OrderFlatDTO;
import jpaBook.jpaShop.repository.order.query.OrderItemQueryDTO;
import jpaBook.jpaShop.repository.order.query.OrderQueryDTO;
import jpaBook.jpaShop.repository.order.query.OrderQueryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.*;

;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDTO> result = orders.stream()
                .map(o -> new OrderDTO(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDTO> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDTO> result = orders.stream()
                .map(o -> new OrderDTO(o))
                .collect(toList());

        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO> orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDTO> result = orders.stream()
                .map(o -> new OrderDTO(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDTO> orderV4_page() {
        return orderQueryRepository.findOrderQueryDTOs();
    }

    @Getter
    static class OrderDTO {
        private Long orderId;
        private String name;
        private String orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDTO> orderItems;

        public OrderDTO(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDTO(orderItem))
                    .collect(toList());
        }
    }

    @Getter
    static class OrderItemDTO {
        private String itemName;
        private int orderPrice;
        private int count;
        public OrderItemDTO(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDTO> ordersV5() {
        return orderQueryRepository.findAllByDTO_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDTO> ordersV6() {
        List<OrderFlatDTO> flats = orderQueryRepository.findAllByDTO_flat();

        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDTO(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDTO(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDTO(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

}
