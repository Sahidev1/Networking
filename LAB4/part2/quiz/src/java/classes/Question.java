/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Map;
import java.util.Set;

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
    private String[] keyArr;

    public Question (int qid, String qtext, Map<String, Integer> optionsMap ){
        this.qid = qid;
        this.qtext = qtext;
        this.optionsMap = optionsMap;
        this.updatedAnswers = false;
        this.genOptionsKeyArray();
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
        String[] arr = (String[]) optionsMap.keySet().toArray();
        return optionsMap;
    }
    
    private void genOptionsKeyArray (){
        Set<String> keySet = optionsMap.keySet();
        int size =  keySet.size();
        String[] arr = new String[size];
        int i = 0;
        for (String s: keySet){
            arr[i++] = s;
        }
        this.keyArr = arr;
    }

    public boolean isUpdatedAnswers() {
        return updatedAnswers;
    }

    public String[] getKeyArr() {
        return keyArr;
    }
}
