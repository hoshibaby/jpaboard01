package org.jyr.jpademo.repository;

import org.jyr.jpademo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByName(String name);
//    jpa가 제공 안하는 함수도 만들어써
//    Member findByUsername(String username); 이건 없어서 에러나

    List<Member> findByNameLike(String name);
}

// integer 했는데 long 값이 들어갔어. long 값이 더 크니까 차떼고 포떼고가 가능하니 int에 들어가진건데 int는 long에 못들어가
// 이건 라잌 정수와 소수가 있는데 정수는 뒤에 .00을 붙히면 소수 표현이 가능한데, 소수는 .25 이걸 뗄수 없으니 정수가 안됨.
// 넣을 때는 타입을 안보는데 가지고 나올때는 충돌이 발생 할 수있어.
