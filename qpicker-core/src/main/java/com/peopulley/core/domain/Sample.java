package com.peopulley.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(appliesTo = "sample", comment = "샘플 테이블")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_id", columnDefinition = "BIGINT UNSIGNED comment '샘플 아이디'")
    private Long sampleId;

    @Column(name = "subject", columnDefinition = "varchar(255) comment '제목'")
    private String subject;

    @Column(name = "content", columnDefinition = "varchar(255) comment '내용'")
    private String content;



    public static Sample insertSample(){

        return null;
    }

    public void updateSample(){

    }






}
