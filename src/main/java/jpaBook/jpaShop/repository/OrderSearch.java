package jpaBook.jpaShop.repository;

import jpaBook.jpaShop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
	private String memberName; // 회원 명
	private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]
}
