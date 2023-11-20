package jpabook.JpaShop.domain.member.service;

import jpabook.JpaShop.api.member.dto.request.UpdateMemberRequest;
import jpabook.JpaShop.domain.member.entity.Member;
import jpabook.JpaShop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 가입
     * @param member
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> byName = memberRepository.findByName(member.getName());

        if (byName.stream().anyMatch(m -> m.getName().equals(member.getName()))) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
//        if (!byName.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
    }


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.find(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.find(id);
        member.setName(name);
    }
}
