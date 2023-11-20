package jpabook.JpaShop.api.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @NotEmpty(message = "이름은 필수 값 입니다.")
    private String name;
}
