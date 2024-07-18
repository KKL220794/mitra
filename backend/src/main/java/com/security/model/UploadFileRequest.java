package com.security.model;

import lombok.Getter;

import java.io.File;

public class UploadFileRequest {
    @Getter
    private String fileName;
    @Getter
    private File file;
}
