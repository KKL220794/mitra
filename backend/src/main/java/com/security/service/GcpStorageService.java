package com.security.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class GcpStorageService {

    @Autowired
    private Storage storage;

    @Value("gcp.storage.bucket-name.uploadedFiles")
    private String uploadFilesBucket;

    public boolean uploadFile(String objectName, File file) throws IOException {
        BlobId blobId = BlobId.of(uploadFilesBucket, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return true;
    }

}

