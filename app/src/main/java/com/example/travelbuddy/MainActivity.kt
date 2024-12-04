package com.example.travelbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddy.ui.theme.TravelBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TravelBuddyTheme {
                HomeScreen(navController = navController)
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf0f0f0))
            .padding(16.dp)
    ) {
        Text(
            text = "¡Explora tu próximo destino!",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SearchBar(query, onSearchChange = { query = it }, onSearchClick = {
            if (query.isNotEmpty()) {
                navController.navigate("search/$query")
            }
        })

        Spacer(modifier = Modifier.height(32.dp))

        CategoryButtons(navController)

        PopularDestinationsSection(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onSearchChange: (String) -> Unit, onSearchClick: () -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onSearchChange,
        label = { Text("Busca tu destino") },
        placeholder = { Text("Ej: París, Nueva York...") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
        },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun CategoryButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryButton("Hoteles") {
            navController.navigate("search/hotels")
        }

        CategoryButton("Vuelos") {
            navController.navigate("search/flights")
        }

        CategoryButton("Rutas Cercanas") {
            navController.navigate("search/routes")
        }
    }
}

@Composable
fun CategoryButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(100.dp)
            .height(50.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun PopularDestinationsSection(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Destinos Populares",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOf("París", "Nueva York", "Tokio", "Londres", "Barcelona")) { destination ->
                DestinationCard(destination, navController)
            }
        }
    }
}

@Composable
fun DestinationCard(destination: String, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clickable { navController.navigate("search/$destination") },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = destination,
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    TravelBuddyTheme {
        HomeScreen(navController = navController)
    }
}