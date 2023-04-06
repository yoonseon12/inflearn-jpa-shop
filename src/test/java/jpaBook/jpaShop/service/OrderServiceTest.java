package jpaBook.jpaShop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jpaBook.jpaShop.domain.Address;
import jpaBook.jpaShop.domain.Member;
import jpaBook.jpaShop.domain.Order;
import jpaBook.jpaShop.domain.OrderStatus;
import jpaBook.jpaShop.domain.item.Book;
import jpaBook.jpaShop.domain.item.Item;
import jpaBook.jpaShop.exception.NotEnoughStockException;
import jpaBook.jpaShop.repository.OrderRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderServiceTest {
	
	@Autowired private EntityManager em;
	@Autowired private OrderService orderService;
	@Autowired private OrderRepository orderRepository;
	

	@Test
	@DisplayName("상품주문")
	void order() {
		// given
		Member member = createMember();
		Item book = createBook("시골 JPA", 10000, 10);
		
		int orderCount = 2;
		
		// when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		// then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals(OrderStatus.ORDER, getOrder.getStatus());
		assertEquals(1, getOrder.getOrderItems().size());
		assertEquals(10000 * 2, getOrder.getTotalPrice());
		assertEquals(10-2, book.getStockQuantity());
	}
	
	@Test
	@DisplayName("상품주문 재고수량 초과")
	void stockCountOver() {
		// given
		Member member = createMember();
		Item item = createBook("시골 JPA", 10000, 10);
		
		int orderCount = 20;
		
		// when
		assertThrows(NotEnoughStockException.class, () -> 
			orderService.order(member.getId(), item.getId(), orderCount)
			);
		
		// then
		fail("재고 수량 부족 예외가 발생해야 한다.");
		
	}
	
	@Test
	@DisplayName("주문 취소")
	void cencel() {
		// given
		Member member = createMember();
		Item book = createBook("시골 JPA", 10000, 10);
		
		int orderCount = 2;
		
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		// when
		orderService.cancelOrder(orderId);
		
		// then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals(OrderStatus.CANCLE, getOrder.getStatus());
		assertEquals(10, book.getStockQuantity());

	}
	
	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "거리", "123-123"));
		em.persist(member);
		return member;
	}
}
