package jpabook.JpaShop.domain.order.repository.orderSimpleQuery;

import jakarta.persistence.EntityManager;
import jpabook.JpaShop.api.order.dto.SimpleOrderQueryDto;
import jpabook.JpaShop.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    public final EntityManager em;

    public List<SimpleOrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.JpaShop.api.order.dto.SimpleOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", SimpleOrderQueryDto.class).getResultList();
    }
}
