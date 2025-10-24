package org.jyr.jpademo.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.jyr.jpademo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
@Log4j2

public class RepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberRepository memberRepository;

//    데이터를 입력하는 식
    @Test
    public void MemberInsertTest(){
        Member member = Member.builder()
                .name("문준휘")
                .password("123456")
                .email("abc7@naver.com")
                .addr("심천")
                .build();
        memberRepository.save(member);
    }
//    save()로 저장

    //10/21 하긴 했지만 이거 잘 안쓴다. pagerequestDTO 만들어서 쓴다.
    @Test
    public void pageListTest(){

//        page import를 <t>로 할 것
        Page<Member> memberPage = memberRepository.findAll(PageRequest.of(0, 3, Sort.by("id").descending()));
        List<Member> members = memberPage.getContent(); //리스트 정보, total page size 등 정보를 가지고 와
        int totalPage = memberPage.getTotalPages();
        log.info("totalpages:{}",totalPage);
        for(Member member:members){
            log.info(member);
        }
    }

//    모든 데이터를 가지고 오는 식
    @Test
    public void findAll(){
        List<Member> members = memberRepository.findAll();
        for (Member member : members){
         log.info(member.toString());
        }
    }
    //findAll로 찾기

    @Test
    public void findById(){
        Member member = memberRepository.findById(1L).get(); //optional로 싸여있는 member를 가지고 온다.
        Member member1 = memberRepository.findById(2L).orElse(null); //Long 이니까 L을 붙혀준거야.
        log.info(member1);
        log.info(member);

    }

//    업데이트함수는 없어. 셋이나 빌드해서 바꿀수있는 것들을 바꾸면 됨.
    @Test
    public void updateMember(){
        Member member = memberRepository.findById(1L).get();
        member.setPassword("abcd");
        member.setAddr("경기남양주");
        memberRepository.save(member);
    }

//    Id아니고 mno라고 해놨더라도 ById임 ByMno 아니야. 아이디 필드임.
    @Test
    public void deleteMember(){
        memberRepository.deleteById(1L);
    }

    @Test
    public void findByName(){
        Member member = memberRepository.findByName("권순영");
        log.info(member.toString());
    }

    @Test
    public void findByNameLike(){
        List<Member> members = memberRepository.findByNameLike("순영");
        for(Member member : members){
            log.info(member.toString());
        }
    }

}


