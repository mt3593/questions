package com.mark.learning.loaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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
                String question = qa.path("question").asText();
                if (!question.trim().isEmpty()) {
                    if (!answer.isEmpty()) {
                        questions.put(question, String.join("\n", answer));
                    } else {
                        throw new RuntimeException("Missing answer to question: " + question);
                    }
                } else {
                    throw new RuntimeException("Missing question to answer: " + answer);
                }
            }
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() {
        return file.getName();
    }

}
