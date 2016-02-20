package com.mark.learning.loaders;

import com.sun.jersey.api.client.Client;

import java.util.HashMap;
import java.util.Map;

public class TutorialsPointLoader implements QuestionLoader {

    @Override
    public Map<String, String> loadQuestionsAndAnswers() {
        Map<String, String> questions = new HashMap<>();
        try {
            Client client = Client.create();
            String response = client.asyncResource("http://www.tutorialspoint.com/java/java_interview_questions.htm").get(String.class).get();
            String[] split = response.split("<section class=\"toggle\">");
            for (int x =1; x < split.length; x++) {
                String questionAndAnswer = split[x];
                String question = questionAndAnswer.substring(questionAndAnswer.indexOf("<label>") + 7, questionAndAnswer.indexOf("</label>"));
                String answer = questionAndAnswer.substring(questionAndAnswer.indexOf("<p>") + 3, questionAndAnswer.indexOf("</p>"));
                questions.put(question.trim(), answer.trim());
            }
            return questions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String name() {
        return "Tutorials Point web site";
    }
}
