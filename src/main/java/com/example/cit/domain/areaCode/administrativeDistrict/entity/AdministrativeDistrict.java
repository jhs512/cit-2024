package com.example.cit.domain.areaCode.administrativeDistrict.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class AdministrativeDistrict {
    @Id
    private Long code;
    private String name;
    private long regionCode;
}
