package org.jyr.jpademo.controller;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.jyr.jpademo.dto.BoardDTO;
import org.jyr.jpademo.dto.PageRequestDTO;
import org.jyr.jpademo.dto.PageResponseDTO;
import org.jyr.jpademo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/board")

public class BoardController {
    @Autowired
    BoardService boardService;


    //register form 열기 위한
    @GetMapping("register")
    public void registerGet(){
        log.info("registerGet");
    }

    //register 열고-저장
    @PostMapping("register")
    public String registerPost(BoardDTO boardDTO){
        log.info("registerPost"); //저장하고 나서 bno가 생김
        Long bno = boardService.insertBoard(boardDTO);
        log.info("board insert success : bno="+bno);
        return "redirect:/board/list";
    }
    //10/21 리스트 주석처리하고
    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<BoardDTO> responseDTO=boardService.getList(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
        model.addAttribute("pageRequestDTO",pageRequestDTO);
    }
    //@GetMapping("list")
    //list 처리
    public void list(Model model){
        log.info("list");
        model.addAttribute("boards", boardService.findAllBoards()); // 이름은 주기 마련 boards라고 줌
    }

    //10/21 파람 아니라도 부트에는 값 들어가 read 만들어졌어!
    @GetMapping({"read", "modify"})
    public void readBoard(Long bno, Integer mode, PageRequestDTO pageRequestDTO, Model model){
        // PageRequestDTO pageRequestDTO 클래스 쓰고 변수이름을 소문자로 해주면 view까지 다 가. DTO를 떼거나 다른 이름을 쓰면
        //아래처럼 model.addAttribute("PageRequestDTO",pageRequestDTO); 보내줘야해. 맞나?
        log.info("readBoard");
        model.addAttribute("PageRequestDTO",pageRequestDTO);
        model.addAttribute("board", boardService.findBoardById(bno,mode));
    }
    @PostMapping("modify")
    public String modifyBoard(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
        //보내줄 값이 많으면 줄줄 쓰지 않고 redirect 넣어서 아래 풀어서 써
        //상세보기 페이지로 갈거라서 string을 넣었다?
        log.info("modifyBoard"+boardDTO); //데이터 잘 전달되고 있는지 확인
        boardService.updateBoard(boardDTO);
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        redirectAttributes.addAttribute("mode",1);
        return "redirect:/board/read?bno="+boardDTO.getBno(); //상세보기 페이지로 이동!
    }
    @GetMapping("remove")
    public String removeBoard(Long bno){
        log.info("removeBoard");
        boardService.deleteBoard(bno);
        return "redirect:/board/list";
    }


}
