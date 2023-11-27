package jpabook.JpaShop.domain.member.repository;

import jpabook.JpaShop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long MemberId);

    List<Member> findByName(String name);
}
