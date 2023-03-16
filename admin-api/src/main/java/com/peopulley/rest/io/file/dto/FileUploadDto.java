package com.peopulley.rest.io.file.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FileUploadDto<T extends Object> {

    private MultipartFile multipartFile;

    private MultipartFile[] multipartFiles;

    private String filePath;

    private long memberSeq;

    private String prefixFileName;

    private T resultObj;

}
