package com.example.mcagataybarin.androquiz;

/**
 * Created by mcagataybarin on 2/26/17.
 */

public class QuestionData {
    private Category[] categories = new Category[3];
    private User currentUser;

    public void initialize(){
        String[] SportQuestions = {"Question1", "Question2", "Question3", "Question4", "Question5"};
        String[] HistoryQuestions = {"Question1", "Question2", "Question3", "Question4", "Question5"};
        String[] ArtQuestions = {"Question1", "Question2", "Question3", "Question4", "Question5"};

        String[][] SportChoices = {{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"}};
        String[][] HistoryChoices = {{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"}};
        String[][] ArtChoices = {{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"},{"a", "b", "c", "d"}};

        int[] SportAnswers = {0,2,2,1,3};
        int[] HistoryAnswers = {0,1,2,1,3};
        int[] ArtAnswers = {2,1,1,2,0};

        Question[] Sport = new Question[5];
        Question[] History = new Question[5];
        Question[] Art = new Question[5];

        for(int i=0;i<5;i++){
            Question q1 = new Question(SportQuestions[i], SportChoices[i], SportAnswers[i], i*100 + 100);
            Question q2 = new Question(HistoryQuestions[i], HistoryChoices[i], HistoryAnswers[i], i*100 + 100);
            Question q3 = new Question(ArtQuestions[i], ArtChoices[i], ArtAnswers[i], i*100 + 100);

            Sport[i] = q1;
            History[i] = q2;
            Art[i] = q3;
        }

        Category sport = new Category("Sport", Sport);
        Category history = new Category("History", History);
        Category art = new Category("Art", Art);
        categories[0] = sport;
        categories[1] = history;
        categories[2] = art;

    }


    public static final QuestionData data = new QuestionData();

    public static QuestionData getInstance(){
        return data;
    }

    public Category[] getCategories(){
        return this.categories;
    }

    public void setUser(User usr){
        this.currentUser = usr;
    }

    public User getUser(){
        return this.currentUser;
    }
}
