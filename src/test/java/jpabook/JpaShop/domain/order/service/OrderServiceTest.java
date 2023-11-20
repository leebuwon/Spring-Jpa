package jpabook.JpaShop.domain.order.service;

import jakarta.persistence.EntityManager;
import jpabook.JpaShop.domain.address.entity.Address;
import jpabook.JpaShop.domain.item.entity.Book;
import jpabook.JpaShop.domain.item.entity.Item;
import jpabook.JpaShop.domain.member.entity.Member;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.order.entity.OrderStatus;
import jpabook.JpaShop.domain.order.repository.OrderRepository;
import jpabook.JpaShop.global.exception.NotEnoughStockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        Member member = new Member();
        member.setName("buwon");
        Address address = createAddress();
        member.setAddress(address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);

        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus());
        assertEquals(1, order.getOrderItemList().size());
        assertEquals(8, item.getStockQuantity());

    }

    @Test
    public void 상품취소() throws Exception{
        Member member = new Member();
        member.setName("buwon");
        Address address = createAddress();
        member.setAddress(address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.deleteOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, order.getStatus());
        assertEquals(10, item.getStockQuantity());
    }


    @Test
    public void 상품주문_재고수량초과() throws Exception{
        Member member = new Member();
        member.setName("buwon");
        Address address = createAddress();
        member.setAddress(address);
        em.persist(member);

        Item item = new Book();
        item.setName("JPA");
        item.setPrice(10000);
        item.setStockQuantity(10);
        em.persist(item);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
    }


    private Address createAddress() {
        return Address.builder()
                .city("seoul")
                .street("river")
                .zipcode("123-123")
                .build();
    }
}