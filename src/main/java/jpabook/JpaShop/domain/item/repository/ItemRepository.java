package jpabook.JpaShop.domain.item.repository;

import jakarta.persistence.EntityManager;
import jpabook.JpaShop.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        // save 당시 id가 null 이기때문에
        if (item.getId() == null){
            em.persist(item);
        } else {
            // 이 merge가 중요하다.
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findALl() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
