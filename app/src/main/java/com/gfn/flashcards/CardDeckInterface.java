package com.gfn.flashcards;

public interface CardDeckInterface {
    String getQuestionTitle(int cardIndex);
    String getQuestionBody(int cardIndex);
    String getHintText(int cardIndex);
    String getAnswerTitle(int cardIndex);
    String getAnswerBody(int cardIndex);
    int calculateNextCardId(int currentCardIndex);
    int calculatePreviousCardId(int currentCardIndex);
    int getNumberOfCards();
    void sortCardsBySuccessRate();
}
