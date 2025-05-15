package umc_8th.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc_8th.spring.domain.common.BaseEntity;
import umc_8th.spring.domain.enums.Gender;
import umc_8th.spring.domain.enums.MemberStatus;
import umc_8th.spring.domain.enums.SocialType;
import umc_8th.spring.domain.mapping.MemberAgree;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.domain.mapping.MemberPrefer;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(length = 100)
    private String profileImage;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date birth;

    @Column(nullable = false, length = 40)
    private String specAddress;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberStatus status;

    private LocalDate inactiveDate;

    //@Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long points;

    @Column(nullable = false)
    private Boolean phoneAuth;

    @Column(nullable = true, length = 20)
    private String phoneNum;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberAgree> memberAgreeList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberPrefer> memberPreferList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMission> memberMissionList = new ArrayList<>();
}
