package jpabook.JpaShop.global.initdb;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.JpaShop.domain.address.entity.Address;
import jpabook.JpaShop.domain.delivery.Delivery;
import jpabook.JpaShop.domain.item.entity.Book;
import jpabook.JpaShop.domain.member.entity.Member;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.orderItem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = saveMember("userA", "seoul", "1234", "123-123");
            saveMember(member);

            Book book1 = saveBook("JPA1", 10000, 10, "Lee", "1234");
            saveBook(book1);

            Book book2 = saveBook("JPA2", 20000, 20, "Lee", "5678");
            saveBook(book2);

            OrderItem orderItem1 = saveOrder(book1, 10000, 3);
            OrderItem orderItem2 = saveOrder(book2, 20000, 8);

            Delivery delivery = saveDelivery(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            saveOrder(order);
        }


        public void dbInit2() {
            Member member = saveMember("userB", "Ganga", "2525", "152-152");
            saveMember(member);

            Book book1 = saveBook("Spring1", 50000, 15, "Kim", "55555");
            saveBook(book1);

            Book book2 = saveBook("Spring2", 40000, 25, "Kim", "66666");
            saveBook(book2);

            OrderItem orderItem1 = saveOrder(book1, 50000, 1);
            OrderItem orderItem2 = saveOrder(book2, 40000, 5);

            Delivery delivery = saveDelivery(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            saveOrder(order);
        }

        private Member saveMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(Address.create(city, street, zipcode));
            return member;
        }

        private Book saveBook(String bookName, int price, int stock, String author, String isbn){
            return Book.create(bookName, price, stock, author, isbn);
        }

        private OrderItem saveOrder(Book book, int price, int stock) {
            return OrderItem.createOrderItem(book, price, stock);
        }

        private Delivery saveDelivery(Address address) {
            Delivery delivery = new Delivery();
            delivery.setAddress(address);
            return delivery;
        }

        private void saveMember(Member member) {
            em.persist(member);
        }

        private void saveBook(Book book){
            em.persist(book);
        }

        private void saveOrder(Order order) {
            em.persist(order);
        }
    }
}

