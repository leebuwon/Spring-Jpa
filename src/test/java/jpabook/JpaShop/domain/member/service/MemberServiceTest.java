package jpabook.JpaShop.domain.member.service;

import jpabook.JpaShop.domain.member.entity.Member;
import jpabook.JpaShop.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        Member member = new Member();
        member.setName("buwon");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.find(saveId));
    }


    @Test
    public void duplicateMember() throws Exception{
        Member member1 = new Member();
        member1.setName("buwon");

        Member member2 = new Member();
        member2.setName("buwon");

        memberService.join(member1);
        try {
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }

        fail("에외가 발생했다!!");
    }
}