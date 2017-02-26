package com.example.mcagataybarin.androquiz;

/**
 * Created by mcagataybarin on 2/26/17.
 */

public class QuestionData {
    Question[] Sport = new Question[5];
    Question[] History = new Question[5];
    Question[] Art = new Question[5];


    public QuestionData(){
        String[] SportQuestions = {"Question1", "Question2", "Question3", "Question4", "Question5"};
        String[] HistoryQuestions = {"Question1", "Question2", "Question3", "Question4", "Question5"};
        String[] ArtQuestions = {"Question1", "Question2", "Question3", "Question4", "Question5"};

        String[][] SportAnswers = {{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"}};
        String[][] HistoryAnswers = {{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"}};
        String[][] ArtAnswers = {{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"}};

        for(int i=0;i<5;i++){
            Question q1 = new Question(SportQuestions[i], SportAnswers[i], 0, i*100 + 100);
            Question q2 = new Question(HistoryQuestions[i], HistoryAnswers[i], 0, i*100 + 100);
            Question q3 = new Question(ArtQuestions[i], ArtAnswers[i], 0, i*100 + 100);

            Sport[i] = q1;
            History[i] = q2;
            Art[i] = q3;
        }
    }

    public Question getCategoryQuestion(String categoryName, int index){
        if(categoryName.equals("Sport")){
            return Sport[index];
        }else if(categoryName.equals("History")){
            return History[index];
        }else{
            return Art[index];
        }
    }
}
