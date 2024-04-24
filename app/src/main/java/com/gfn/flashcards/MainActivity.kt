package com.gfn.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gfn.flashcards.ui.theme.FlashcardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsTheme {
                ScaffoldContent()
            }
        }
    }
}


@Composable
fun MyApp(){
    var showMenu by rememberSaveable { mutableStateOf(1) }
    if(showMenu == 1){
        Menu(onContinueClicked = {showMenu = 0})
    }
    if (showMenu == 0){
        CardApp(onBackToMenuClicked = {showMenu = 1})
    }
}


// Basic Layout ordering
@Composable
fun ScaffoldContent() {
    var addCardClicked by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { addCardClicked = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add New Card")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if(addCardClicked) NewCard(onNewCardSaved = {addCardClicked = false}) else MyApp()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardAppPreview() {
    FlashcardsTheme {
        CardApp(onBackToMenuClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun NewCardPreview() {
    FlashcardsTheme {
        NewCard(onNewCardSaved = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CardEditorPreview() {
    FlashcardsTheme {
        CardEditor(1)
    }
}


@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    FlashcardsTheme {
        Menu(onContinueClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldingTest() {
    FlashcardsTheme {
        ScaffoldContent()
    }
}