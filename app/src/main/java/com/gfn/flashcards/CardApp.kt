package com.gfn.flashcards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// import Java classes


@Composable
fun CardApp(
    onBackToMenuClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Java deck object
    val cardService = ConcreteCardBackend()

    var cardId: Int by rememberSaveable { mutableIntStateOf(0) }
    val numberOfCards: Int = cardService.getNumberOfCards()
    var questionTitle: String by rememberSaveable { mutableStateOf(cardService.getQuestionTitle(cardId))}
    var questionBody: String by rememberSaveable { mutableStateOf(cardService.getQuestionBody(cardId))}
    var hintText: String by rememberSaveable { mutableStateOf( cardService.getHintText(cardId))}
    var answerTitle: String by rememberSaveable { mutableStateOf( cardService.getAnswerTitle(cardId))}
    var answerBody: String by rememberSaveable { mutableStateOf( cardService.getAnswerBody(cardId))}

    var revealed = false // true means answer has been shown
    var flipped by rememberSaveable { mutableStateOf(false) } // true means showing the answer
    var hinted = false // reduce points by 50%
    var correct = false // self evaluated as answered correctly

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            Text(
                text = "card number: " + cardId.toString(),
                fontSize = 12.sp,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (!flipped) questionTitle
                else answerTitle,
                fontSize = 30.sp,
                lineHeight = 35.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (!flipped) questionBody
                else answerBody,
                fontSize = 20.sp,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            if (flipped){
                Image(
                    painter = painterResource(id = R.drawable.test_answer_pic),
                    contentDescription = "Image description",
                    modifier = Modifier.size(150.dp) // Example modifier for size
                )
            }
            ElevatedButton(
                onClick = { flipped = !flipped },
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    if (flipped) stringResource(R.string.show_question) else stringResource(R.string.reveal_solution)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ElevatedButton(
                        onClick = { if(cardId > 0) {
                            cardId = cardService.calculatePreviousCardId(cardId)
                            flipped = false
                            questionTitle = cardService.getQuestionTitle(cardId)
                            questionBody = cardService.getQuestionBody(cardId)
                            answerTitle = cardService.getAnswerTitle(cardId)
                            answerBody = cardService.getAnswerBody(cardId)
                        } else {
                            onBackToMenuClicked()
                        }
                                  },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(if(cardId > 0) "previous" else "Menu")
                    }
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text("Edit")
                }
                ElevatedButton(
                    onClick = {if (cardId < (numberOfCards - 1)) {
                        cardId = cardService.calculateNextCardId(cardId)
                        flipped = false
                        questionTitle = cardService.getQuestionTitle(cardId)
                        questionBody = cardService.getQuestionBody(cardId)
                        answerTitle = cardService.getAnswerTitle(cardId)
                        answerBody = cardService.getAnswerBody(cardId)
                    } else {onBackToMenuClicked()}
                              },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(if(cardId < (numberOfCards - 1)) "next" else "finish")
                }
            }
        }
    }
}