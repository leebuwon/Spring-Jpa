package jpabook.JpaShop.domain.item.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.JpaShop.domain.item.dto.BookForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
@NoArgsConstructor
@SuperBuilder
public class Book extends Item{

    private String author;
    private String isbn;

    public static Book create(String name, int price, int stock, String author, String isbn){
        return Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stock)
                .author(author)
                .isbn(isbn)
                .build();
    }

    public static Book update(Long itemId, String name, int price, int stock, String author, String isbn){
        return Book.builder()
                .id(itemId)
                .name(name)
                .price(price)
                .stockQuantity(stock)
                .author(author)
                .isbn(isbn)
                .build();
    }
}
