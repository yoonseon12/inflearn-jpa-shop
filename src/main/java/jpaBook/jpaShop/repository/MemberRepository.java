package jpaBook.jpaShop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jpaBook.jpaShop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	
	private final EntityManager em; // 스프링이 엔티티매니저를 만들어서 주입해준다.

	// 회원 정보 저장
	public void save(Member member) {
		em.persist(member);
	}
	
	// 회원 조회
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	// 회원 목록 조회
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}
	
	// 이름으로 회원 조회
	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
