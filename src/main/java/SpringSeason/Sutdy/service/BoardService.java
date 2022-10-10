package SpringSeason.Sutdy.service;

import SpringSeason.Sutdy.domain.Board;
import SpringSeason.Sutdy.dto.BoardDto;
import SpringSeason.Sutdy.dto.ErrorMessageDto;
import SpringSeason.Sutdy.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void save(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    }

    public boolean update(BoardDto boardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardDto.getId());
        if (optionalBoard.isPresent()) {
            boardRepository.save(boardDto.toEntity());
            return true;
        }
        return false;
    }

    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

//        boardList.forEach(board -> boardDtoList.add(
//                BoardDto.builder()
//                        .id(board.getId())
//                        .author(board.getAuthor())
//                        .title(board.getTitle())
//                        .content(board.getContent())
//                        .createdDate(board.getCreatedDate())
//                        .build()
//        ));

        for (Board board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    public BoardDto getBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()) {
            return null;
        }
        Board board = optionalBoard.get();
        return BoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
    }

    public boolean deleteBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
