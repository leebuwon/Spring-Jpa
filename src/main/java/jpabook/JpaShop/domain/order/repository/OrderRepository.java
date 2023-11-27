package jpabook.JpaShop.domain.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.JpaShop.api.order.dto.SimpleOrderQueryDto;
import jpabook.JpaShop.domain.member.entity.QMember;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.order.entity.OrderSearch;
import jpabook.JpaShop.domain.order.entity.OrderStatus;
import jpabook.JpaShop.domain.order.entity.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    public final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }
    public Order findOne(Long orderId){
        return em.find(Order.class, orderId);
    }

//    public List<Order> findAll(OrderSearch orderSearch){
//        return em.createQuery("select o from Order o join o.member m", Order.class)
//                .getResultList();
//    }

    /**
     * JPA Criteria
     * 권장 x
     * 이런거 쓰지말고 Query DSL을 사용하자!
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> cri = new ArrayList<>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null){
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            cri.add(status);
        }

        if (StringUtils.hasText(orderSearch.getMemberName())){
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            cri.add(name);
        }

        cq.where(cb.and(cri.toArray(new Predicate[cri.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Order> findAll(OrderSearch orderSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query.selectFrom(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName())) // where문에 메시지를 전달해줘서 결과만 반환받음
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression nameLike(String memberName) {
        if (!StringUtils.hasText(memberName)){
            return null;
        }
        return QMember.member.name.like(memberName);
    }

    // 동적쿼리
    private BooleanExpression statusEq(OrderStatus status){
        if (status == null){
            return null;
        }
        return QOrder.order.status.eq(status);
    }

    public List<Order> findAllWithMemberAndDelivery() {
        List<Order> query = em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery", Order.class).getResultList();
        return query;
    }

    // fetch join 에서 1 : N 을 페이징하면 페이징이 되지 않는다..(가장 큰 단점)
    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch  o.delivery d" +
                        " join fetch o.orderItemList oi" +
                        " join fetch oi.item i", Order.class)
                .setFirstResult(0)
                .setMaxResults(100)
                .getResultList();
    }

    public List<Order> findAllWithMemberAndDeliveryPage(int offset, int limit) {
        List<Order> query = em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return query;
    }
}
