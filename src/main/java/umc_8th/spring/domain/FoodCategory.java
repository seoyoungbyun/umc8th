package umc_8th.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import umc_8th.spring.domain.common.BaseEntity;
import umc_8th.spring.domain.mapping.MemberPrefer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberPrefer> memberPreferList = new HashSet<>();
}
