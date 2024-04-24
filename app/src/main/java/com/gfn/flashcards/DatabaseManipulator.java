package com.gfn.flashcards;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.SQLException;

public class DatabaseManipulator extends SQLiteOpenHelper{

    private static final String DB_NAME = "flashcarddb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "cards";
    private static final String ID_COL = "card_id";
    private static final String QUESTION_TITLE_COL = "question_title";
    private static final String QUESTION_BODY_COL = "question_body";
    private static final String HINT_COL = "hint";
    private static final String ANSWER_TITLE_COL = "answer_title";
    private static final String ANSWER_BODY_COL = "answer_body";
    private static final String CATEGORY_COL = "category";

    public DatabaseManipulator(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUESTION_TITLE_COL + " VARCHAR(255), "
                + QUESTION_BODY_COL + " TEXT, "
                + HINT_COL + " TEXT, "
                + ANSWER_TITLE_COL + " VARCHAR(255), "
                + ANSWER_BODY_COL + " TEXT, "
                + CATEGORY_COL + " VARCHAR(255))";

        db.execSQL(query);
    }

    public void addCard(SQLiteDatabase db, String questionTitle, String questionBody, String hint,
                        String answerTitle, String answerBody, String category) {
        ContentValues values = new ContentValues();
        values.put(QUESTION_TITLE_COL, questionTitle);
        values.put(QUESTION_BODY_COL, questionBody);
        values.put(HINT_COL, hint);
        values.put(ANSWER_TITLE_COL, answerTitle);
        values.put(ANSWER_BODY_COL, answerBody);
        values.put(CATEGORY_COL, category);

        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Card getCardById(SQLiteDatabase db, int cardId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + cardId;
        Cursor cursor = null;
        Card flashcard = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                int questionTitleIndex = cursor.getColumnIndex(QUESTION_TITLE_COL);
                int questionBodyIndex = cursor.getColumnIndex(QUESTION_BODY_COL);
                int hintIndex = cursor.getColumnIndex(HINT_COL);
                int answerTitleIndex = cursor.getColumnIndex(ANSWER_TITLE_COL);
                int answerBodyIndex = cursor.getColumnIndex(ANSWER_BODY_COL);
                int categoryIndex = cursor.getColumnIndex(CATEGORY_COL);

                String questionTitle = cursor.getString(questionTitleIndex);
                String questionBody = cursor.getString(questionBodyIndex);
                String hint = cursor.getString(hintIndex);
                String answerTitle = cursor.getString(answerTitleIndex);
                String answerBody = cursor.getString(answerBodyIndex);

                // category is not yet an attribute of the Card class
                String category = cursor.getString(categoryIndex);

                flashcard = new Card(
                        questionTitle,
                        questionBody,
                        hint,
                        answerTitle,
                        answerBody
                );
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return flashcard;
    }

    public ArrayList<Card> getCardsByCategory(SQLiteDatabase db, String category) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CATEGORY_COL + " = ?";
        ArrayList<Card> cardList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{category});

            if (cursor.moveToFirst()) {
                do {
                    int questionTitleIndex = cursor.getColumnIndex(QUESTION_TITLE_COL);
                    int questionBodyIndex = cursor.getColumnIndex(QUESTION_BODY_COL);
                    int hintIndex = cursor.getColumnIndex(HINT_COL);
                    int answerTitleIndex = cursor.getColumnIndex(ANSWER_TITLE_COL);
                    int answerBodyIndex = cursor.getColumnIndex(ANSWER_BODY_COL);

                    String questionTitle = cursor.getString(questionTitleIndex);
                    String questionBody = cursor.getString(questionBodyIndex);
                    String hint = cursor.getString(hintIndex);
                    String answerTitle = cursor.getString(answerTitleIndex);
                    String answerBody = cursor.getString(answerBodyIndex);

                    Card card = new Card(
                            questionTitle,
                            questionBody,
                            hint,
                            answerTitle,
                            answerBody
                    );

                    cardList.add(card);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return cardList;
    }

    // New method to delete the database
    public void deleteDatabase(Context context) {
        context.deleteDatabase(DB_NAME);
    }
}
