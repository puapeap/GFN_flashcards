package com.gfn.flashcards;

public class Card {
    //Datenklasse, Komposition in ConcreteCardBackend

    private String questionTitle;
    private String questionBody;
    private String hintText;
    private String answerTitle;
    private String answerBody;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private float successRate = 0.0f;

    // Constructor to initialize all attributes
    public Card(String questionTitle, String questionBody, String hintText, String answerTitle, String answerBody) {
        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.hintText = hintText;
        this.answerTitle = answerTitle;
        this.answerBody = answerBody;
    }

    public Card(String questionTitle, String questionBody, String hintText, String answerTitle, String answerBody, float successRate) {
        this.questionTitle = questionTitle;
        this.questionBody = questionBody;
        this.hintText = hintText;
        this.answerTitle = answerTitle;
        this.answerBody = answerBody;
        this.successRate = successRate;
    }

    // Getter and Setter for questionTitle
    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    // Getter and Setter for questionBody
    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    // Getter and Setter for hintText
    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    // Getter and Setter for answerTitle
    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    // Getter and Setter for answerBody
    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }

    public int getWrongAnswers(){
        return wrongAnswers;
    }

    public int getCorrectAnswers(){
        return correctAnswers;
    }

    public float getSuccessRate(){
        return successRate;
    }
}

