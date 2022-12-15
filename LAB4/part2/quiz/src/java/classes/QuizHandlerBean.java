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
import javax.ejb.Stateful;

/**
 *
 * @author ali
 */
@Stateful
public class QuizHandlerBean {
    private final Map<Integer, Quiz> quizMap = new HashMap<>();
    public QuizHandlerBean (){

    }

    public Map<Integer, Quiz> getQuizMap() {
        return quizMap;
    }
}
