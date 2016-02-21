package com.mark.learning.loaders;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class FileQuestionsLoaderTest {

    @Test
    public void testLoadQuestionsAndAnswersFromNotes() throws Exception {
        //Given
        FileQuestionsLoader fileQuestionsLoader = new FileQuestionsLoader(new File("src/main/resources/notes.json"));
        //When
        Map<String, String> questionsAndAnswers = fileQuestionsLoader.loadQuestionsAndAnswers();
        //Then
        Assert.assertFalse("Nothing found in notes!", questionsAndAnswers.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidFileThrowException() {
        //Given
        FileQuestionsLoader fileQuestionsLoader = new FileQuestionsLoader(new File("src/test/resources/invalid.json"));
        //When then throw exception
        fileQuestionsLoader.loadQuestionsAndAnswers();
    }

    @Test(expected = RuntimeException.class)
    public void testMissingAnswerThrowException() {
        //Given
        FileQuestionsLoader fileQuestionsLoader = new FileQuestionsLoader(new File("src/test/resources/missingAnswer.json"));
        //When then throw exception
        fileQuestionsLoader.loadQuestionsAndAnswers();
    }

    @Test(expected = RuntimeException.class)
    public void testMissingQuestionThrowException() {
        //Given
        FileQuestionsLoader fileQuestionsLoader = new FileQuestionsLoader(new File("src/test/resources/missingQuestion.json"));
        //When then throw exception
        fileQuestionsLoader.loadQuestionsAndAnswers();
    }

    @Test
    public void testAllResourceFilesAreValidAndAreNotEmpty() {
        //Given
        File resourcesDirectory = new File("src/main/resources");
        for (File questionAnswerFile : resourcesDirectory.listFiles()) {
            //When then
            Assert.assertFalse(new FileQuestionsLoader(questionAnswerFile).loadQuestionsAndAnswers().isEmpty());
        }

    }
}