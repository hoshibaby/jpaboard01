package org.jyr.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Item {
    @Id
    @Column(name="item_id") //이렇게 이름을 바꿀 수 있어.
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id; //상품코드

    @Column(unique = true, nullable=false, length = 50) //유니크 속성 준 것
    private String itemNm; //상품명

    @Column(name="price", nullable = false)
    @ColumnDefault(value = "1000") //숫자든 문자든 꼭 문자열로 줘야해. 아니면 오류나
    private int price; //가격

//    @Column(ColumnDefinition = "int default 10 not null")
    @Column(nullable=false)
    @ColumnDefault(value="10") //위에 처럼 한줄로도 가능해
    private int stockNumber; //재고수량

    @Lob

    @Column(nullable=false)
    private String itemDetail; //상품 상세 설명
    //값들이 미리 정해져있으면 열거형으로 객체를 만들 수 있어. 도메인 -> 아이템셀스테이터스(열거형)

    @Enumerated(EnumType. STRING)
    //@Enumerated(EnumType.ORDINAL) //ORDINAL : 0,1,2 로 들어가. STRING : 판매중, 판매완료, 입고대기
    private ItemSellStatus itemSellStatus;

    @CreationTimestamp //데이터 생성날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regTime;

    @UpdateTimestamp //수정시간
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Transient
    private String memo;
    @Transient
    private String remark;




}
