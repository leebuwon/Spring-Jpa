package jpabook.JpaShop.domain.order.service;

import jpabook.JpaShop.domain.delivery.Delivery;
import jpabook.JpaShop.domain.item.entity.Item;
import jpabook.JpaShop.domain.item.repository.ItemRepository;
import jpabook.JpaShop.domain.member.entity.Member;
import jpabook.JpaShop.domain.member.repository.MemberRepositoryOld;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.order.entity.OrderSearch;
import jpabook.JpaShop.domain.order.repository.OrderRepository;
import jpabook.JpaShop.domain.orderItem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void deleteOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrder(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
