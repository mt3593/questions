package com.mark.learning.loaders;

import java.util.Map;

public interface QuestionLoader {
    Map<String,String> loadQuestionsAndAnswers();
    String name();
}
