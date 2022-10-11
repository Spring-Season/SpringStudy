package SpringSeason.Sutdy.service;

import SpringSeason.Sutdy.domain.Board;
import SpringSeason.Sutdy.dto.BoardDto;
import SpringSeason.Sutdy.dto.ErrorMessageDto;
import SpringSeason.Sutdy.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void saveFile(List<BoardDto> boardDtoList) {
        File file = new File("src/main/resources/result.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        for (BoardDto boardDto : boardDtoList) {
            //System.out.println("boardDto = " + boardDto);
//            System.out.println("boardDto.getCreatedDate() = " + boardDto.getCreatedDate());
//            System.out.println("boardDto.getModifiedDate() = " + boardDto.getModifiedDate());
        }
        try {
            //mapper.writeValue(new File("src/main/resources/result.json"), board);
            mapper.writeValue(file, boardDtoList);
//            mapper.writeValue(new File("src/main/resources/result.json"), boardDtoList);
            //mapper.writeValue(Paths.get("result.json").toFile(), boardDtoList);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void save(BoardDto boardDto){
        boardRepository.save(boardDto.toEntity());
//        saveFile(getBoardList());
    }

    public boolean update(BoardDto boardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardDto.getId());
        if (optionalBoard.isPresent()) {
            boardRepository.save(boardDto.toEntity());
            saveFile(getBoardList());
            return true;
        }
        return false;
    }

    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        boardList.forEach(board -> boardDtoList.add(
                BoardDto.builder()
                        .id(board.getId())
                        .author(board.getAuthor())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdDate(board.getCreatedDate())
                        .modifiedDate(board.getModifiedDate())
                        .build()
        ));
        for (BoardDto boardDto : boardDtoList) {
            System.out.println("boardDto = " + boardDto);
        }
//        for (Board board : boardList) {
//            BoardDto boardDto = BoardDto.builder()
//                    .id(board.getId())
//                    .author(board.getAuthor())
//                    .title(board.getTitle())
//                    .content(board.getContent())
//                    .createdDate(board.getCreatedDate())
//                    .modifiedDate(board.getModifiedDate())
//                    .build();
//            boardDtoList.add(boardDto);
//        }
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
//            saveFile(getBoardList());
            return true;
        }
        return false;
    }

}
