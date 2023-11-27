package jpabook.JpaShop;

import jpabook.JpaShop.domain.member.repository.MemberRepositoryOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaShopApplicationTests {

	@Autowired
    MemberRepositoryOld memberRepository;


}
