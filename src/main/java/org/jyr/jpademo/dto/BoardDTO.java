package org.jyr.jpademo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BoardDTO {
    private Long bno;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    //notempty 쓰려고 코끼리 가서 Thymeleaf Layout Dialect, Spring Boot Starter Validation 넣었어
    private String author;
    private int readcount;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

}
