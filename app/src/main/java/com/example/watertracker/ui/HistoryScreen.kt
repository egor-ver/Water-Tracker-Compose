package com.example.watertracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HistoryScreen(viewModel: WaterViewModel = viewModel(), onBackClick: () -> Unit){
    val history by viewModel.history.collectAsState(initial = emptyList())
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item{
                    Text("История выпитой воды по дням")
                }
                items(history){  day ->
                    Text("${day.date}: ${day.amount} мл")
                }
                item{
                    Button({onBackClick()}){
                        Text("Назад")
                    }
                }
            }
        }
    }
}


