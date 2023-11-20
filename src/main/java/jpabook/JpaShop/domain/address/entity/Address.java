package jpabook.JpaShop.domain.address.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Embeddable
@SuperBuilder
@NoArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public static Address create(String city, String street, String zipcode){
        return Address.builder()
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();
    }
}