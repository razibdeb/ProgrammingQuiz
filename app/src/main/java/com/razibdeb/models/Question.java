package com.razibdeb.models;

import com.parse.ParseObject;

import java.util.ArrayList;

import other.Constants;

/**
 * Created by Razib Chandra Deb on 2/9/15.
 * Email: razibdeb@gmail.com
 */
public class Question {
    public int numberOfOptions;
    public int answerIndex;
    public ArrayList<String> options;
    public String questionText;
    public String objectId;

    public void Question(ParseObject parseObject)
    {
        if (parseObject != null)
        {
            questionText = (String)parseObject.get(Constants.QUESTION_TEXT);
            numberOfOptions = (int)parseObject.get(Constants.QUESTION_OPTIONS);
            answerIndex = (int)parseObject.get(Constants.QUESTION_ANSWER_INDEX);
            options = (ArrayList<String>)parseObject.get(Constants.QUESTION_OPTIONS);
            objectId = parseObject.getObjectId().toString();
        }
    }
}
//ArrayList<String> testStringArrayList = (ArrayList<String>)user.get("Roots");
//String myString = testStringArrayList.get(1);