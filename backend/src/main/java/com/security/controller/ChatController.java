package com.security.controller;

import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.security.model.UploadFileRequest;
import com.security.service.GcpStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatSession chatSession;
    private GcpStorageService gcpStorageService;

    @PostMapping("/message")
    public String chat(@PathVariable String text) throws IOException {
        GenerateContentResponse generateContentResponse = this.chatSession.sendMessage(text);
        return ResponseHandler.getText(generateContentResponse);
    }


    @PostMapping("/uploadFile")
    public Boolean uploadFile(@PathVariable UploadFileRequest uploadFileRequest) throws IOException {
        return gcpStorageService.uploadFile(uploadFileRequest.getFileName(),uploadFileRequest.getFile());
    }
}
