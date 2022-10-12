package SpringSeason.Sutdy.controller;

import SpringSeason.Sutdy.dto.BoardDto;
import SpringSeason.Sutdy.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("boardList", boardDtoList);
        return "board/list.html";
    }

    @GetMapping("/board")
    public String createBoard() {
        return "board/post.html";
    }

    @PostMapping("/board")
    public String save(BoardDto boardDto, Model model) {
        boardService.save(boardDto);
        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getBoard(id);
        if (boardDto == null) {
            model.addAttribute("msg","이미 삭제된 글입니다.");
            return "common/messageRedirect";
        }
        model.addAttribute("board", boardDto);
        return "board/detail.html";
    }

    @GetMapping("/board/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getBoard(id);
        System.out.println("boardDto = " + boardDto);
        model.addAttribute("board", boardDto);
        return "board/edit.html";
    }

    @PutMapping("/board/update/{id}")
    public String update(BoardDto boardDto, Model model) {
        boolean result = boardService.update(boardDto);
        if (!result) {
            model.addAttribute("msg", "이미 삭제된 글입니다.");
            return "common/messageRedirect";
        }
        return "redirect:/";
    }

    @DeleteMapping("/board/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        boolean result = boardService.deleteBoard(id);
        model.addAttribute("msg", "정상적으로 삭제되었습니다.");
        if (!result) {
            model.addAttribute("msg", "이미 삭제된 글입니다.");
        }
        return "common/messageRedirect";
    }

}
