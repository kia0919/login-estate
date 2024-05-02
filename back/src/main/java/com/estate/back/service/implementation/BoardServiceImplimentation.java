package com.estate.back.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.estate.back.dto.request.board.PostBoardRequestDto;
import com.estate.back.dto.response.ResponseDto;
import com.estate.back.entity.BoardEntity;
import com.estate.back.repository.BoardRepository;
import com.estate.back.repository.UserRepository;
import com.estate.back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplimentation implements BoardService {
    
    // 의존성 주입
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId) {
        
        try {

            boolean isExistUser = userRepository.existsByUserId(userId);
            // 만약에 false이면은 return ResponseDto.authenticationFailed 반환
            if (!isExistUser) return ResponseDto.authenticationFailed();

            // BoardEntity 생성 위의 dto와 userId를 가져옴
            BoardEntity boardEntity = new BoardEntity(dto, userId);
            boardRepository.save(boardEntity);

        // catch: 발생하는 예외를 출력, databaseError를 반환
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        // Try블럭이 정상 실행 시 ResponseDto에 있는 success를 반환
        return ResponseDto.success();

    }
    
}
