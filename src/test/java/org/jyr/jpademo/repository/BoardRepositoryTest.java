package org.jyr.jpademo.repository;

import jakarta.persistence.Lob;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.jyr.jpademo.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
@Log4j2

public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void SearchBoard(){
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
        // page 0 은 1page, size 5개, bno로 분류, 역순으로
        Page<Board> result = boardRepository.search1(pageable);
        List<Board> boards = result.getContent();
        for (Board board : boards) {
            log.info(board.toString());
        }
        log.info(result.stream().count());
        log.info(result.getSize());
    }


    @Test
    public void insertBoard(){
        Board board = Board.builder()
            .title("title3")
            .content("content3")
            .author("user03")
            .build();
        boardRepository.save(board);
    }

    @Test
    public void findLikeAll(){
//        보드 레파지토리에서 이거 @Query 블락해서 에러나기때문에 블락했어
//        List<Board> boards = boardRepository.findByTitleAndContent("2");
//        for (Board board : boards) {
//            log.info(board);
//        }
    }
}
