package me.sjtumeow.meow.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import me.sjtumeow.meow.model.result.QiniuTokenResult;
import me.sjtumeow.meow.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    private Integer expires = 3600;

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public QiniuTokenResult getUploadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, expires, new StringMap());
        return new QiniuTokenResult(upToken, expires);
    }

}
