package jpaBook.jpaShop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpaBook.jpaShop.domain.Member;
import jpaBook.jpaShop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	// 회원 가입
	@Transactional
	public Long join(Member member) {
		validateDuplidateMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	// 중복 회원 검증
	private void validateDuplidateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName()); // Alt + Shift + L
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("중복 회원 존재");
		}
	}
	
	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	// 단일 회원 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

}
