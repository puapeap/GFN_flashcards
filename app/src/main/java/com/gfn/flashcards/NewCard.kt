package com.gfn.flashcards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.platform.LocalContext

@Composable
fun NewCard(onNewCardSaved: () -> Unit,){
    val context = LocalContext.current
    val databaseService = DatabaseManipulator(context)

    //databaseService.deleteDatabase(context)

    var displayText by rememberSaveable { mutableStateOf("No data") }


    var text1 by rememberSaveable { mutableStateOf("") }
    var text2 by rememberSaveable { mutableStateOf("") }
    var text3 by rememberSaveable { mutableStateOf("") }
    var text4 by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = text1,
            onValueChange = { text1 = it },
            label = { Text("Enter Question Title") }
        )
        OutlinedTextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Enter Question Body") }
        )
        OutlinedTextField(
            value = text3,
            onValueChange = { text3 = it },
            label = { Text("Enter Answer Title") }
        )
        OutlinedTextField(
            value = text4,
            onValueChange = { text4 = it },
            label = { Text("Enter Answer Body") }
        )
        ElevatedButton(
            onClick = {
                val db = databaseService.writableDatabase  // Get writable database

                // Ensure the table is created
                databaseService.onCreate(db)

                // Add a sample course
                databaseService.addCard(
                    db,
                    text1,
                    text2,
                    "",
                    text3,
                    text4,
                    "DefaultCardDeck",
                )

                // Retrieve the data from the database (this is just to test if it worked)
                val cursor = db.rawQuery("SELECT * FROM cards", null)

                val data = buildString {
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(cursor.getColumnIndexOrThrow("card_id"))
                        val questionTitle = cursor.getString(cursor.getColumnIndexOrThrow("question_title"))
                        val questionBody = cursor.getString(cursor.getColumnIndexOrThrow("question_body"))
                        val hint = cursor.getString(cursor.getColumnIndexOrThrow("hint"))
                        val answerTitle = cursor.getString(cursor.getColumnIndexOrThrow("answer_title"))
                        val answerBody = cursor.getString(cursor.getColumnIndexOrThrow("answer_body"))
                        val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))

                        append(
                            "ID: $id, Question Title: $questionTitle, " +
                                    "Question Body: $questionBody, Hint: $hint, " +
                                    "Answer Title: $answerTitle, Answer Body: $answerBody, " +
                                    "Category: $category\n"
                        )
                    }
                }

                displayText = if (data.isNotEmpty()) data else "No data"  // Update display text

                cursor.close()  // Close cursor
                db.close()  // Close database

            },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Save New Card")
        }

        ElevatedButton(onClick = { onNewCardSaved() }) {
            Text(text = "Go Back")
        }

        // Button to create and test the database
        /*ElevatedButton(
            onClick = {
                val db = databaseService.writableDatabase  // Get writable database

                // Ensure the table is created
                databaseService.onCreate(db)

                // Add a sample course
                databaseService.addCard(
                    db,
                    "Card 1",
                    "Card 1",
                    "Card 1",
                    "Card 1",
                    "Card 1",
                    "DefaultCardDeck",
                )

                // Retrieve the data from the database
                val cursor = db.rawQuery("SELECT * FROM cards", null)

                val data = buildString {
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(cursor.getColumnIndexOrThrow("card_id"))
                        val questionTitle = cursor.getString(cursor.getColumnIndexOrThrow("question_title"))
                        val questionBody = cursor.getString(cursor.getColumnIndexOrThrow("question_body"))
                        val hint = cursor.getString(cursor.getColumnIndexOrThrow("hint"))
                        val answerTitle = cursor.getString(cursor.getColumnIndexOrThrow("answer_title"))
                        val answerBody = cursor.getString(cursor.getColumnIndexOrThrow("answer_body"))
                        val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))

                        append(
                            "ID: $id, Question Title: $questionTitle, " +
                                    "Question Body: $questionBody, Hint: $hint, " +
                                    "Answer Title: $answerTitle, Answer Body: $answerBody, " +
                                    "Category: $category\n"
                        )
                    }
                }

                displayText = if (data.isNotEmpty()) data else "No data"  // Update display text

                cursor.close()  // Close cursor
                db.close()  // Close database
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Create and Test Database")
        }*/

        // Display the results of database operations
        Text(
            text = displayText,
            modifier = Modifier.padding(16.dp)
        )


    }
}