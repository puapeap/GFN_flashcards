package com.gfn.flashcards;
import java.util.ArrayList;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ConcreteCardDeck implements CardDeckInterface {
    private int numberOfCards = 0;
    private ArrayList<Card> cardList;

    private DatabaseManipulator databaseManipulator;
    private SQLiteDatabase db;

    private String cardDeckCategory = "DefaultCardDeck";

    public ConcreteCardDeck(Context context){
        try {
            // Initialize the database manipulator and get a writable database instance
            databaseManipulator = new DatabaseManipulator(context);
            db = databaseManipulator.getWritableDatabase();

            // Retrieve cards for the specified category
            cardList = databaseManipulator.getCardsByCategory(db, cardDeckCategory);

            closeDb();

            numberOfCards = cardList.size();
            this.sortCardsBySuccessRate();

        } catch (Exception e) {
            cardList = new ArrayList<>();
        }


        // this is just for testing, need to save cards somewhere and load them when initializing
        /*cardList.add(new Card(
                "What is the difference between == and .equals() in Java?",
                "(When used on objects)",
                "hintText0",
                "answerTitle0",
                "== Compares references to check if two objects point to the same memory location .equals(): Compares values to determine if two objects contain the same data or attributes.",
                0.9f));
        cardList.add(new Card(
                "questionTitle1",
                "questionBody1",
                "hintText1",
                "answerTitle1",
                "answerBody1",
                0.1f));
        cardList.add(new Card(
                "questionTitle2",
                "questionBody2",
                "hintText2",
                "answerTitle2",
                "answerBody2",
                0.7f));
        cardList.add(new Card(
                "questionTitle3",
                "questionBody3",
                "hintText3",
                "answerTitle3",
                "answerBody3",
                0.6f));
        cardList.add(databaseManipulator.getCardById(db, 1));*/

    }

    @Override
    public String getQuestionTitle(int cardIndex) {
        String QuestionTitle;
        if (cardIndex >= 0 && cardIndex < numberOfCards){
            QuestionTitle = cardList.get(cardIndex).getQuestionTitle();
        } else {QuestionTitle = "";}
        return QuestionTitle;
    }

    @Override
    public String getQuestionBody(int cardIndex){
        String QuestionBody;
        if (cardIndex >= 0 && cardIndex < numberOfCards){
            QuestionBody = cardList.get(cardIndex).getQuestionBody();
        } else {QuestionBody = "error: card does not exist";}
        return QuestionBody;
    }

    @Override
    public String getHintText(int cardIndex){
        return "Test";
    }

    @Override
    public String getAnswerTitle(int cardIndex){
        String AnswerTitle;
        if (cardIndex >= 0 && cardIndex < numberOfCards){
            AnswerTitle = cardList.get(cardIndex).getAnswerTitle();
        } else {AnswerTitle = "error: card does not exist";}
        return AnswerTitle;
    }

    @Override
    public String getAnswerBody(int cardIndex){
        String AnswerBody;
        if (cardIndex >= 0 && cardIndex < numberOfCards){
            AnswerBody = cardList.get(cardIndex).getAnswerBody();
        } else {AnswerBody = "error: card does not exist";}
        return AnswerBody;
    }

    @Override
    public int calculateNextCardId(int currentCardIndex){
        if (currentCardIndex >= 0 && currentCardIndex < (numberOfCards - 1)){
            return ++currentCardIndex;
        }
        return 0;
    }

    @Override
    public int calculatePreviousCardId(int currentCardIndex){
        if (currentCardIndex >= 1){
            return --currentCardIndex;
        }
        return 0;
    }

    @Override
    public int getNumberOfCards(){
        return numberOfCards;
    }
    @Override
    public void sortCardsBySuccessRate(){
        // Sort the ArrayList by successRate in ascending order
        cardList.sort((c1, c2) -> Float.compare(c1.getSuccessRate(), c2.getSuccessRate()));
    }

    public void closeDb() {
        // Close the database to prevent resource leaks
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
