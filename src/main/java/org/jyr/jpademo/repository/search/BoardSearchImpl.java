package org.jyr.jpademo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.jyr.jpademo.domain.Board;
import org.jyr.jpademo.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    //이거 넣고 나서 에러줄 생긴거 메서드 생성 2번해서 아래 3개를 잡았어
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query=from(qboard); //qboard로부터 쿼리를 생성
        BooleanBuilder builder = new BooleanBuilder(); //where절은 true ;false라서 아래의 문자가 들어가 있으면 true
        builder.or(qboard.title.containsIgnoreCase("호랑5")); //title like '%1%'
        //containsIgnoreCase 영문일때 대소문자 구분 안해
        builder.or(qboard.content.containsIgnoreCase("호랑5")); // or content like '%1%'
        builder.or(qboard.author.containsIgnoreCase("호랑5"));
        query.where(builder); // or or or 결과 들을 모아서 builder 값을 넣어서 where 절을 만든다.
        //where (title like '%호랑5%' or content like '%호랑5%' or author like '%호랑5') and bno>0 limit 0,5
        query.where(qboard.bno.gt(0));
        // gt => bno 0보다 더 큰거 가지고와. = 다 가지고 와, index에서 먼저 검색하고 찾아서 성능이 좋아져
        this.getQuerydsl().applyPagination(pageable,query); // 쿼리 실행됨
        List<Board> list=query.fetch(); //() 비워두면 검색한 데이터 전부다 넣어줘. (갯수) 원하는 갯수만 넣을 수도 있어
        long count=query.fetchCount(); //전체 레코드 수를 얻을 수 있어

        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query=from(qboard);
        //키워드가 있을 경우 types 들어가고 없으면
        if(types!=null&&types.length>0 && keyword!=null){
            BooleanBuilder builder = new BooleanBuilder();
            for (String type : types) { //types 갯수만큼 for 돌아서
                switch (type) {
                    case "t" :
                        builder.or(qboard.title.containsIgnoreCase(keyword));
                        break;
                    case "c" :
                        builder.or(qboard.content.containsIgnoreCase(keyword));
                        break;
                    case "w" :
                        builder.or(qboard.author.containsIgnoreCase(keyword));
                }
            }
        query.where(builder);
        }
        query.where(qboard.bno.gt(0));
        this.getQuerydsl().applyPagination(pageable,query);
        List<Board> list=query.fetch();
        long count=query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }
}
