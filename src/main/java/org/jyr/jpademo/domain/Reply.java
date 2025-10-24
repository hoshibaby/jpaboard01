package org.jyr.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

//테이블이 만들어짐
public class Reply extends BaseEntity {
//    상속을 하니 수정 날짜 등록일 지정 필요 없음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private Long bno;
    private String content;
    private String writer;
    @Transient //테이블 필드로 안하고 싶을 때
    private String memo;
}
