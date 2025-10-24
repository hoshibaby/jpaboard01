package org.jyr.jpademo.service;

import lombok.extern.log4j.Log4j2;
import org.jyr.jpademo.domain.Board;
import org.jyr.jpademo.dto.BoardDTO;
import org.jyr.jpademo.dto.PageRequestDTO;
import org.jyr.jpademo.dto.PageResponseDTO;
import org.jyr.jpademo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //BEAN 객체라 인지하고 컨테이너 객체를 만들고
@Log4j2

public class BoardServiveImpl implements BoardService{
    @Autowired
    private BoardRepository boardRepository;

//    data insert
    @Override
    public Long insertBoard(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO); //save는 저장된 Entity를 리턴
        Long bno=boardRepository.save(board).getBno();
        return bno;
    }

    //    전체 리스트 가지고 옴
    @Override
    public List<BoardDTO> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDTO> dtos = new ArrayList<>();
        for(Board board:boards){
            dtos.add(entityToDTO(board));
        }
        log.info(dtos);
        return dtos;
    }

    //10/21 있으면 bno 없으면 null로!
    @Override
    public BoardDTO findBoardById(Long bno, Integer mode) {
        Board board = boardRepository.findById(bno).orElse(null); //값이 있으면 bno, 없으면 null
        //원래 Optional<Board>로 리턴됨 꺼내기 위해서는 .get을 해도 되고?
        if(mode==1){
            board.updateReadcount();
            boardRepository.save(board);
        }
        return entityToDTO(board);
    }

    @Override
    public void updateBoard(BoardDTO boardDTO) {
//        그냥 저장하면 리드카운트랑 다 없어지니까
        Board board = boardRepository.findById(boardDTO.getBno()).orElse(null);
//        getBno 로 기존데이타를 가져와
        board.change(boardDTO.getTitle(), boardDTO.getContent());
//       체인지 함수 부르고 이것 두개만 바꿔
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long bno) {
        boardRepository.deleteById(bno);
    }

//    페이징처리
    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("bno"); //bno 내림차순 해
//        Page<Board> result = boardRepository.findKeyword(pageRequestDTO.getKeyword(),pageable);
        //Page<Board> result = boardRepository.findAll(pageable); //페이징처리만 할거면 이것만 있어도 괜찮아. findAll에 pageble을 검색하면 페이징 정보(1page), 레코드 크기만큼, bno 내림차순까지 result로 이름을
        Page<Board> result = boardRepository.searchAll(
                pageRequestDTO.getTypes(),
                pageRequestDTO.getKeyword(),
                pageable);
        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());  // 게시판 정보를 먼저 꺼내고 엔티티로 받아서 dto 객체로 바꿔 => 리스트로 만들어줘서 리스트 저장 /for문 안돌리고 stream으로 잡음
        int total = (int)result.getTotalElements(); //전체 페이지 갯수?
        log.info("-----------------"+total);
        //페이징에 검색기능을 넣을거야
        PageResponseDTO<BoardDTO> responseDTO=PageResponseDTO.<BoardDTO>withAll() //BoardDTO 객체로 만들거야
                .pageRequestDTO(pageRequestDTO) //DTO에 있는 정보 + LIST + TOTAL 정보 넣고 BUILD
                .dtoList(dtoList)
                .total(total)
                .build();

        return responseDTO;
    }
}
