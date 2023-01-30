package com.example.costofelectricity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.costofelectricity.ui.theme.CostOfElectricityTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostOfElectricityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Costs()
                }
            }
        }
    }
}

@Composable
fun Costs() {
    val appModifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    var price by remember { mutableStateOf(0f) }
    var vatChecked: Boolean by remember { mutableStateOf(false) }

    var consumptionInput: String by remember { mutableStateOf("") }
    val consumption: Float = consumptionInput.toFloatOrNull() ?: 0.0f

    val cost = (price * 100).roundToInt() / 100.00 * consumption
    val payment = if (vatChecked) cost + (cost/100 * 10) else cost + (cost / 100 * 24)



    Column() {
        Text(
            text = "Cost of electricity",
            modifier = appModifier,
            color = MaterialTheme.colors.primary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = consumptionInput,
            onValueChange = {consumptionInput = it },
            label = { Text(text = "Consumption in kWh") },
            modifier = appModifier,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Price/kWh ${String.format("%.2f", price).replace(".", ",")} €", modifier = Modifier.padding(16.dp,0.dp))
        Slider(
            value = price,
            onValueChange = {price = it},
            valueRange = 0.00f..1.00f,
            modifier = Modifier.padding(16.dp,0.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = vatChecked,
                onCheckedChange = {vatChecked = it}
            )
            Text(text = "VAT 10%")
        }
        Row(modifier = appModifier, horizontalArrangement = Arrangement.Center) {
            Surface(
                modifier = Modifier.shadow(10.dp),
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "${String.format("%.2f", payment).replace(".", ",")} €",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }


        
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CostOfElectricityTheme {
        Costs()
    }
}