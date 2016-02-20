package com.mark.learning.loaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class FileQuestionsLoader implements QuestionLoader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File file;

    public FileQuestionsLoader(File file) {
        this.file = file;
    }

    @Override
    public Map<String, String> loadQuestionsAndAnswers() {
        try {
            Map<String, String> questions = new HashMap<>();
            JsonNode notes = mapper.readTree(file);
            for (JsonNode qa : notes.path("questions")) {
                List<String> answer = new ArrayList<>();
                for (JsonNode partAnswer : qa.path("answer")) {
                    answer.add(partAnswer.asText());
                }
                questions.put(qa.path("question").asText(), String.join("\n", answer));
            }
            return questions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() {
        return file.getName();
    }

}
