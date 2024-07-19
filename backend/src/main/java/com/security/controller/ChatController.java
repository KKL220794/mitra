package com.security.controller;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    @Value("${gcp.project.id}")
    private String projectId;

    @Value("${gcp.vertex.location}")
    private String location;

    @Value("${gcp.vertex.model.name}")
    private String modelName;


    @PostMapping("/message")
    public String chat(@RequestBody String text) throws IOException {
            try (VertexAI vertexAI = new VertexAI(projectId, location)) {
                GenerativeModel model = new GenerativeModel(modelName, vertexAI);
                model.withGenerationConfig(GenerationConfig.newBuilder().build());
                GenerateContentResponse response = model.generateContent(text);
                String output = ResponseHandler.getText(response);
                return output;
            }
    }


//    @PostMapping("/uploadFile")
//    public Boolean uploadFile(@PathVariable UploadFileRequest uploadFileRequest) throws IOException {
//        return false;
//    }
}
