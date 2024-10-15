package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val DBtest = DBClass(this@MainActivity)
        Log.d("CodeLab_DB", "onCreate: ")

        val favoritePokemon = DBtest.getFavoritePokemon()
        Log.d("CodeLab_DB", "Favorite Pokémon: $favoritePokemon")

        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize(), names = DBtest.get2DRecords().toList(), favorite = favoritePokemon)
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier, names: List<Array<String>>, favorite: String) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = Color(0xFFE0F7FA)) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Column {
                Text(text = "Favorite Pokémon: $favorite", modifier = Modifier.padding(16.dp), color = Color(0xFF004D40))
                Greetings(names = names)
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("My Favorite Pokémon!", color = Color(0xFF004D40)) // Change text color
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)) // Change button color
        ) {
            Text("Click to see them!", color = Color.White) // Change button text color
        }
    }
}

@Composable
private fun Greetings(modifier: Modifier = Modifier, names: List<Array<String>>) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
            Log.d("CodeLab_DB", "name[0] = ${name[0]}")
            Log.d("CodeLab_DB", "name[1] = ${name[1]}")
        }
    }
}

@Composable
private fun Greeting(name: Array<String>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB2DFDB)), // Change card background color
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
        Log.d("CodeLab_DB", "Greeting: ")
    }
}

@Composable
private fun CardContent(name: Array<String>) {
    var expanded by remember { mutableStateOf(false) }

    Log.d("CodeLab_DB", "name = $name ")
    Log.d("CodeLab_DB", "name[0] = ${name[0]} ")
    Log.d("CodeLab_DB", "name[1] = ${name[1]} ")

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Presenting!, ", color = Color(0xFF004D40)) // Change color of greeting
            Text(
                text = name[0],
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF00796B) // Change color of Pokémon name
                )
            )
            if (expanded) {
                Text(
                    text = name[1], // Show Pokémon details
                    color = Color(0xFF004D40) // Change color of Pokémon details
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
