/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ali
 */

public class Quiz {
    private int quiz_id;
    private int user_id;
    private int lastQuizPoints;
    private int currQuizPoints;
    private boolean hasUserDoneQuiz;
    private String quizName;
    private final List<Question> questions = new ArrayList<>();
    
    public Quiz (){
        quiz_id = -1;
        user_id = -1;
        this.hasUserDoneQuiz = false;
        this.lastQuizPoints = -1;
        this.currQuizPoints = 0;
    }
    
    public void addQuestion (int qid, String qtext, String options, String answers){
        String opts[] = options.split("/");
        String ans[] = answers.split("/");
        Map<String,Integer> optMap = new HashMap<>();
        for (int i = 0; i < opts.length; i++){
            optMap.put(opts[i], Integer.parseInt(ans[i]));
        }
        Question q = new Question (qid, qtext, optMap);
        questions.add(q);
    }
    
    public void calculateQuizPoints (){
        int point = 0;
        for (Question q: questions){
            point += q.getAnswerPoints();
        }
        this.currQuizPoints = point;
    }
    
    public List<Question> getQuestions (){
        return questions;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setHasUserDoneQuiz(boolean hasUserDoneQuiz) {
        this.hasUserDoneQuiz = hasUserDoneQuiz;
    }

    public boolean isHasUserDoneQuiz() {
        return hasUserDoneQuiz;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getLastQuizPoints() {
        return lastQuizPoints;
    }

    public void setLastQuizPoints(int lastQuizPoints) {
        this.lastQuizPoints = lastQuizPoints;
    }

    public int getCurrQuizPoints() {
        return currQuizPoints;
    }
}
