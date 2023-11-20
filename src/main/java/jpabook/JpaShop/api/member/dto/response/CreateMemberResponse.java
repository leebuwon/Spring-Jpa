package jpabook.JpaShop.api.member.dto.response;

import lombok.Data;

@Data
public class CreateMemberResponse {

    private Long id;

    public CreateMemberResponse(Long id) {
        this.id = id;
    }
}
