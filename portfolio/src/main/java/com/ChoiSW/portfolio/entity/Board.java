package com.ChoiSW.portfolio.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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


    @ManyToOne
    @JsonIgnore
    private User user;


}
