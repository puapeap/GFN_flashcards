package com.gfn.flashcards;

public interface CardBackend {
    public String getQuestionTitle(int cardIndex);

    public String getQuestionBody(int cardIndex);

    public String getHintText(int cardIndex);

    public String getAnswerTitle(int cardIndex);

    public String getAnswerBody(int cardIndex);

    public int calculateNextCardId(int currentCardIndex);

    public int calculatePreviousCardId(int currentCardIndex);

    public int getNumberOfCards();

    public void sortCardsBySuccessRate();
}
