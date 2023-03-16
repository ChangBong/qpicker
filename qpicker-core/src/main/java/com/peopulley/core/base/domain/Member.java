package com.peopulley.core.base.domain;


import com.peopulley.core.common.enums.MemberRole;
import com.peopulley.core.base.dto.MemberRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "member", comment = "회원")
@ToString()
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id", columnDefinition = "BIGINT unsigned comment '회원 아이디'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "member_name", columnDefinition = "varchar(255) comment '회원 이름'")
    private String memberName;

    @Column(name = "email", columnDefinition = "varchar(255) comment '이메일'")
    private String email;

    @Column(name = "password", columnDefinition = "varchar(255) comment '패스워드'")
    private String password;

    @Column(name = "member_status", columnDefinition = "int comment '회원 상태 1: 정회원 9: 탈퇴회원'")
    private int memberStatus;

    @Column(name = "birth", columnDefinition = "varchar(20) comment '생년월일'")
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", columnDefinition = "varchar(100) comment '사용자 권한'")
    private MemberRole memberRole;

    @Column(name = "profile_img", columnDefinition = "varchar(255) comment '프로필 이미지 Path'")
    private String profileImg;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id", columnDefinition = "BIGINT unsigned comment '암호키 아이디'")
    private Salt salt;

    public static Member insertMember(MemberRequestDto requestDto){
        return Member.builder()
                .build();
    }

    public void updateMember(MemberRequestDto requestDto){
    }

    private void deleteMember(Member member){
    }

}
