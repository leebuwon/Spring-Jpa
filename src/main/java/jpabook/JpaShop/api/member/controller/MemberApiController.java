package jpabook.JpaShop.api.member.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.JpaShop.api.member.dto.MemberDto;
import jpabook.JpaShop.api.member.dto.request.CreateMemberRequest;
import jpabook.JpaShop.api.member.dto.request.UpdateMemberRequest;
import jpabook.JpaShop.api.member.dto.response.CreateMemberResponse;
import jpabook.JpaShop.api.member.dto.response.UpdateMemberResponse;
import jpabook.JpaShop.domain.member.entity.Member;
import jpabook.JpaShop.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long memberId = memberService.join(member);
        return new CreateMemberResponse(memberId);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request){
        // 커맨드랑 쿼리를 나누는 방법
        memberService.update(id, request.getName());
        Member member = memberService.findOne(id);

        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream()
                .map(member -> new MemberDto(member.getName())).toList();

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
