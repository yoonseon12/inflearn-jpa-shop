package jpaBook.jpaShop.api;

import jpaBook.jpaShop.domain.Address;
import jpaBook.jpaShop.domain.Order;
import jpaBook.jpaShop.domain.OrderStatus;
import jpaBook.jpaShop.repository.OrderRepository;
import jpaBook.jpaShop.repository.OrderSearch;
import jpaBook.jpaShop.repository.order.simplequery.OrderSimpleQueryDTO;
import jpaBook.jpaShop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/sample-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    @GetMapping("/api/v2/sample-orders")
    public List<SimpleOrderDTO> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDTO> result = orders.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());
        return result;
    }

    @Data
    static class SimpleOrderDTO {
        private Long orderId;
        private String name;
        private String orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDTO(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        };
    }

    @GetMapping("/api/v3/sample-orders")
    public List<SimpleOrderDTO> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> result = orders.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/sample-orders")
    public List<OrderSimpleQueryDTO> orderV4() {
        return orderSimpleQueryRepository.findOrderDTOs();
    }

}
