package org.example.manager.controller;

import org.example.manager.service.FileUploadService;
import org.example.model.vo.common.Result;
import org.example.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;


    @PostMapping("/fileUpload")
    public Result<String> fileUplodad(@RequestParam("file")MultipartFile multipartFile){
        String fileUrl = fileUploadService.fileUpload(multipartFile);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
