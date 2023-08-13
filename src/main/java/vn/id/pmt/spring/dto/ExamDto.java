package vn.id.pmt.spring.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Integer examId;
    private String typeExam;
    private LocalDate executeDate;
}
