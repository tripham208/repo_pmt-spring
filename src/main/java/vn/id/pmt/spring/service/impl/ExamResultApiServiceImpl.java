package vn.id.pmt.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.id.pmt.spring.dto.ExamResultDto;
import vn.id.pmt.spring.dto.request.PaginationParams;
import vn.id.pmt.spring.entity.jpa.ExamResult;
import vn.id.pmt.spring.exception.NotFoundException;
import vn.id.pmt.spring.repository.jpa.ExamResultRepository;
import vn.id.pmt.spring.service.ExamResultApiService;
import vn.id.pmt.spring.util.CSVUtil;
import vn.id.pmt.spring.util.MappingUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamResultApiServiceImpl implements ExamResultApiService {

    private final ExamResultRepository examResultRepository;
    private final MappingUtil mappingUtil;


    /**
     * Gets exam by id.
     *
     * @return Exam by id
     * @throws NotFoundException when not found
     */
    @Override
    public Optional<Object> getExamResultById() throws NotFoundException {
        return Optional.empty();
    }

    /**
     * Gets list exam.
     *
     * @return list Exam
     * @throws NotFoundException when not found
     */
    @Override
    public Optional<Object> getListExamResult() throws NotFoundException {
        Optional<List<ExamResult>> examResults = Optional.of(examResultRepository.findAll());

        if (examResults.get().isEmpty()) {
            throw new NotFoundException("Not found any records.");
        } else {
            List<ExamResultDto> listExamResultDto = mappingUtil.mapList(examResults.get(), ExamResultDto.class);
            return Optional.of(listExamResultDto);
        }
    }

    /**
     * Gets list exam by page.
     *
     * @param params the PaginationParams
     * @return the list exam by page
     * @throws NotFoundException when not found
     */
    @Override
    public Optional<Object> getListExamResultByPage(PaginationParams params) throws NotFoundException {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getPageSize());

        Optional<Page<ExamResult>> examResults = Optional.of(examResultRepository.findAll(pageable));


        if (examResults.get().isEmpty()) {
            throw new NotFoundException("Not found any records");
        } else {
            List<ExamResultDto> listExamResultDto = (List<ExamResultDto>) mappingUtil.mapIterable(examResults.get(), ExamResultDto.class);
            return Optional.of(listExamResultDto);
        }
    }

    /**
     * Insert exams by file.
     *
     * @param file the file
     */
    @Override
    public void insertExamResultByFile(MultipartFile file) {
        try {
            List<ExamResultDto> examResultDtoList = CSVUtil.csvToList(file.getInputStream(), ExamResultDto.class);
            List<ExamResult> examResults = mappingUtil.mapList(examResultDtoList, ExamResult.class);
            examResultRepository.saveAll(examResults);

        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }

    /**
     * Export csv byte array input stream.
     *
     * @return the byte array input stream
     */
    @Override
    public ByteArrayInputStream exportCSV() {
        List<ExamResult> examResults = examResultRepository.findAll();
        List<ExamResultDto> examResultDtoList = mappingUtil.mapList(examResults, ExamResultDto.class);
        return CSVUtil.exportCSV(examResultDtoList);
    }
}