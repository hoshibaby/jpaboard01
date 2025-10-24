package org.jyr.jpademo.service;


import org.jyr.jpademo.domain.Board;
import org.jyr.jpademo.dto.BoardDTO;
import org.jyr.jpademo.dto.PageRequestDTO;
import org.jyr.jpademo.dto.PageResponseDTO;

import java.util.List;


public interface BoardService {
    Long insertBoard(BoardDTO boardDTO);
    //dto 설정 다하고 service 왔어. 모델멥퍼로 entity 를 수정할때 필드-필드명이 다르면 모델멥퍼가 안맞춰져,
    //서비스에 아래 메소드를 하나 만들어. 디폴트 메소드를 하나 구연함.
    //컨트롤러로 DTO를 보내
    List<BoardDTO> findAllBoards();
    //10/21
    BoardDTO findBoardById(Long bno, Integer mode);
    //내가 이름을 정하기 나름이나, 꼭 지켜야하는건 파라미터
    void updateBoard(BoardDTO boardDTO);
//    bno 몇번 데이터가 바꼈습니다. 하려면 log 하고 void 빼고 int 달고
    void deleteBoard(Long bno);
    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
            .bno(boardDTO.getBno())
            .title(boardDTO.getTitle())
            .content(boardDTO.getContent())
            .author(boardDTO.getAuthor())
            .build();
        return board;
    } //보드를 넣으면 엔티티형으로

    default BoardDTO entityToDTO(Board board) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .readcount(board.getReadcount())
                .regDate(board.getRegDate())
                .updateDate(board.getUpdateDate())
                .build();
        return boardDTO;
    } //엔티티를 넣으면 컨트롤러에는 DTO형으로 가야하니까 변환
}
//모델 멥퍼쓰는 것보다 필드명이 달라도 자유롭게 쓸수있어

// 레파지토리에서 꺼내면 엔티티, DTO를 변환시켜서 서비스로 보내
