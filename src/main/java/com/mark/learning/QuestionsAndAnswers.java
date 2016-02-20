package com.mark.learning;

import com.mark.learning.loaders.FileQuestionsLoader;
import com.mark.learning.loaders.QuestionLoader;
import com.mark.learning.loaders.TutorialsPointLoader;

import java.io.File;
import java.util.*;

public class QuestionsAndAnswers {

    private final Map<String, String> questions = new HashMap<>();
    private final Random random = new Random();

    public QuestionsAndAnswers() {
    }

    public void load(QuestionLoader loader) {
        questions.putAll(loader.loadQuestionsAndAnswers());
    }

    public String getRandomQuestion() {
        return (String)questions.keySet().toArray()[random.nextInt(questions.size())];
    }

    public String getAnswer(String question) {
        return questions.getOrDefault(question, "Unknown");
    }

    public static void main(String[] args) throws Exception{
        QuestionsAndAnswers questionsAndAnswers = new QuestionsAndAnswers();
        List<QuestionLoader> questionLoaders = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        questionLoaders.add(new TutorialsPointLoader());
        for (File file : new File("src/main/resources").listFiles()) {
            questionLoaders.add(new FileQuestionsLoader(file));
        }
        System.out.println("Please select, type 1 2 3 4 for multiple or leave blank for all:");
        for (int x = 0; x < questionLoaders.size(); x++){
            System.out.println(x + ". " + questionLoaders.get(x).name());
        }
        String options = scanner.nextLine();
        List<QuestionLoader> choice = new ArrayList<>();
        if (options == null || options.trim().isEmpty()) {
            choice = questionLoaders;
        } else {
            for (String option : options.split(" ")) {
                try {
                    int optionIndex = Integer.parseInt(option);
                    choice.add(questionLoaders.get(optionIndex));
                } catch (Exception e) {
                    System.out.println("Issue parsing option: " + option + ", got Error: " + e.getMessage());
                }
            }
        }
        choice.forEach((a) -> questionsAndAnswers.load(a));
        do {
            Runtime.getRuntime().exec("clear");
            String question = questionsAndAnswers.getRandomQuestion();
            System.out.println(question);
            System.in.read();
            System.out.println(questionsAndAnswers.getAnswer(question));
            System.out.println();
            System.in.read();
        } while (true);
    }
}
