package com.ChoiSW.portfolio.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
//더티체킹으로 인해서 업데이트 되는 컬럼만 수정하도록 하는 어노테이션
//@DynamicUpdate
public class Board {

    @Id // 프라이머리 키라는뜻 제일 위에것만
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @NotBlank(message ="제목을 입력해주세요")
    @Size(max=20,message = "20자 이하입니다")
    private String title;

    @NotBlank(message ="내용을 입력해주세요")
    @Size(max=30,message = "30자 이하입니다")
    private String content;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private int viewCount;

    private Boolean isDeleted;

    //@ManyToOne(fetch = FetchType.LAZY) //이렇게하면 N+1 문제가 발생할수있다
    @ManyToOne
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnore
    private User user;


}
