package com.example.cit.domain.env.env.entity;

import com.example.cit.global.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Env extends BaseEntity {

    private String SiteName;
    @Column(columnDefinition = "TEXT")
    private String forbiddenWords;

}

