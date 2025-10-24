package org.jyr.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="tbl_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String password;
    @Column(unique = true)
    private String email;

//    private String memo;

    @Column(name="address")
    private String addr;
}

// Member class -> MemerRepository 설정 -> test가서 RepositoryTest 만들어서 테스트 하니 표도 만들어지고 데이터도 들어가.
