/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Map;

/**
 *
 * @author ali
 */
public class Question {
    private int qid;
    private String qtext;
    private Map<String, Integer> optionsMap;
    private Map<String, Integer> answers;
    private boolean updatedAnswers;

    public Question (int qid, String qtext, Map<String, Integer> optionsMap ){
        this.qid = qid;
        this.qtext = qtext;
        this.optionsMap = optionsMap;
        this.updatedAnswers = false;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Integer> answers) {
        this.answers = answers;
        this.updatedAnswers = true;
    }
    
    public void answersUpdatedPersisted(){
        this.updatedAnswers = false;
    }

    public int getQid() {
        return qid;
    }

    public String getQtext() {
        return qtext;
    }

    public Map<String, Integer> getOptionsMap() {
        return optionsMap;
    }

    public boolean isUpdatedAnswers() {
        return updatedAnswers;
    }
}
