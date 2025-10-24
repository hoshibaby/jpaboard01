package org.jyr.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


@Entity // 이거 써두면 테이블이 자동으로 만들어져 -> repository에서 db처리가 되고 entity와 짝으로 만들면 됨.
@Setter
@Getter
@ToString
//@Data 코끼리 clean -> other 하는데 @Data 경고 떠서 필요한 내용만 분리함
@Builder
@AllArgsConstructor
@NoArgsConstructor //Builder의 세트 all + no
//@Table(name="tbl_board") 테이블 이름은 board 그대로 하려고
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //PK 만드는 방법 IDENTUTY 오토증가임
//    pk id 안만들면 보드에 빨간줄
    private Long bno; //long vs Long 소문자로 하면 객체가 안되고, Long 이면 객체화
    @Column(nullable = false)
    private String title;
    //255 글자는 작아서 크기를 늘리기로 함
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false)
    private String author;
//    상속받아서 필요없어졌어
//    @CreationTimestamp //스템프와 아래 패턴
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime regdate;
    @ColumnDefault(value = "0") // 디폴트 안넣으면 null 이고 null에서 뭘 더하면 항상 null.
                                // "" 인용부호를 항상 싸줘야해.
    private int readcount; //readcount 호출할때마다 1 증가

    public void updateReadcount() {
        this.readcount = readcount+1;
    }
    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

