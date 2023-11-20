package jpabook.JpaShop.domain.item.service;

import jpabook.JpaShop.domain.item.entity.Book;
import jpabook.JpaShop.domain.item.entity.Item;
import jpabook.JpaShop.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    /**
     * 변경 감지 기능 사용
     * 변경 감지가 병합보다 더 좋은 기능이다. (merge를 쓰지 말자)
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn) {
        Book book = Book.update(itemId, name, price, stockQuantity, author, isbn);
        itemRepository.save(book);
    }

    /**
     * 병합 감지는 아래 로직과 같이 동작한다.
     * 거의 다른게 없지만 조금 차이가 안다.
     * 병합은 모든 필드를 업데이트 하기 때문에 위험할 수 있다. 만약에 null값으로 세팅이 된다면 null이 들어가게 되는 문제가 생김!
     */
//    @Transactional
//    public Item updateItem2(Long itemId, Book bookParam){
//        Item findItem = itemRepository.findOne(itemId);
//        findItem.setPrice(bookParam.getPrice());
//        findItem.setName(bookParam.getName());
//        findItem.setStockQuantity(bookParam.getStockQuantity());
//        return findItem;
//    }

    public List<Item> findItems(){
        return itemRepository.findALl();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
