package org.example.manager.service.imp;

import cn.hutool.core.date.DateUtil;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.properties.MinioProperties;
import org.example.manager.service.FileUploadService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import io.minio.errors.MinioException;

@Slf4j
@Service
public class FileUploadServiceImp implements FileUploadService {
    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    // Minio的地址
                    .endpoint(minioProperties.getEndpoint())
                    // 账号 密码
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();

            // 设置存放在仓库中的文件名称
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");

            String uuid = UUID.randomUUID().toString().replace("-", "");
            // 通过日期 + UUID 生成  20230801/443e1e772bef482c95be28704bec58a901.jpg
            String fileName = dateDir+"/"+uuid+multipartFile.getOriginalFilename();

            // 判断桶是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
            } else {
                System.out.println("Bucket 'elite-image' already exists.");
            }

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .object(fileName)
                    .build();

            // 进入添加
            minioClient.putObject(putObjectArgs);

            // 获取上传Minio后的文件名称  地址名称 + 桶名称 + 文件名称
            return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName ;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
