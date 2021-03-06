package com.ggomjae.web.toywebservice.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.ggomjae.web.toywebservice.domain.Profile.Profile;
import com.ggomjae.web.toywebservice.domain.Profile.ProfileRepository;
import com.ggomjae.web.toywebservice.web.dto.ProfileRequestDto;
import com.ggomjae.web.toywebservice.web.dto.ProfileResponseDto;
import lombok.NoArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@NoArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));


        return s3Client.getUrl(bucket, fileName).toString();
    }

    public boolean delete(String currentFilePath){

        boolean isExistObject = s3Client.doesObjectExist(bucket, currentFilePath);

        if (isExistObject) {
            s3Client.deleteObject(bucket, currentFilePath);
            return true;
        }else{
            return false;
        }
    }
}
