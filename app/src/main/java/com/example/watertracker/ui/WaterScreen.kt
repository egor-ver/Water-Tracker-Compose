package com.example.watertracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WaterScreen(viewModel: WaterViewModel = viewModel(), onHistoryClick: () -> Unit){
    val uiState by viewModel.waterUiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Трекер Воды",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = "Выпито сегодня: ${uiState.current}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        val progress = when{
            uiState.goal > 0 -> (uiState.current.toFloat() / uiState.goal).coerceAtLeast(0f)
            else -> 0f
        }
        LinearProgressIndicator(
            progress = {progress},
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Дневная цель: ${uiState.goal}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        var text by remember {
            mutableStateOf("")
        }
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {Text("Введите цель, мл")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        Button(onClick = {text.toIntOrNull()?.let { viewModel.updateGoal(it) }}){
            Text("Установить цель")
        }
        Row {
            Button({viewModel.addWater(250)}){
                Text("+250 мл")
            }
            Button({viewModel.addWater(500)}){
                Text("+500 мл")
            }
            Button({viewModel.addWater(1000)}){
                Text("+1000 мл")
            }
        }
        Button(
            {viewModel.deleteLast()},
            colors =
                ButtonDefaults.buttonColors(Red)
        ){
            Text("Удалить последнее")
        }
        Button({onHistoryClick()}){
            Text("Перейти к истории")
        }
    }
}




