package com.example.cit.domain.member.member.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.dto.PlayerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class MemberMeDto {
    @NonNull
    private long id;
    @NonNull
    private LocalDateTime createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private String username;
    @NonNull
    private String name;
    @NonNull
    private String cellphoneNo;
    @NonNull
    private List<String> authorities;
    @NonNull
    private PlayerDto player;

    public MemberMeDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.name = member.getName();
        this.cellphoneNo = member.getCellphoneNo();
        this.authorities = member.getAuthoritiesAsStringList();
        this.player = new PlayerDto(member.getPlayer());
    }
}