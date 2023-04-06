package jpaBook.jpaShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jpaBook.jpaShop.domain.Member;
import jpaBook.jpaShop.repository.MemberRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {
	
	@Autowired private MemberService memberService;
	@Autowired private MemberRepository memberRepository;
	@Autowired private EntityManager em;
	
	@Test
	@DisplayName("회원가입 테스트")
	void join() {
		// given
		Member member = new Member();
		member.setName("kim");
		
		// when
		Long saveId = memberService.join(member);
		
		// then
		em.flush(); // 하면 디비에 영속성 컨택스트에 있는 쿼리는 나가기 때문에 쿼리가 보인다. 
		assertEquals(member, memberRepository.findOne(saveId));
//		위 코드와 같다. assertThat(member).isEqualTo(memberRepository.findOne(saveId)); 
		
	}
	
	@Test
	@DisplayName("중복 회원 예외처리 테스트")
	void testJoinDuplication() {
		// given
		Member member1 = new Member();
		member1.setName("kim1");
		Member member2 = new Member();
		member2.setName("kim1");
		
		// when
		memberService.join(member1);
		//memberService.join(member2); // 회원명 중복으로 예외발생해야한다.
		
		// then		
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		assertEquals("중복 회원 존재", exception.getMessage());		
		
	}


}
