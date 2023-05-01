package jpaBook.jpaShop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpaBook.jpaShop.domain.*;
import jpaBook.jpaShop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 총 주문 2개
 * * userA
 *   * JPA1 BOOK
 *   * JPA2 BOOK
 * * userB
 *   * SPRING1 BOOK
 *   * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    public final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA","서울", "1", "11111");
            em.persist(member);

            Book book1 = new Book();
            createBook(book1, "JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = new Book();
            createBook(book2, "JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB","진주", "2", "22222");
            em.persist(member);

            Book book1 = new Book();
            createBook(book1, "SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = new Book();
            createBook(book2, "SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }


        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(Book book, String JPA1_BOOK, int price, int stockQuantity) {
            book.setName(JPA1_BOOK);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }

}
