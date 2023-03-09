package com.example.kylebomeisl_20230308_nycschools.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kylebomeisl_20230308_nycschools.R
import com.example.kylebomeisl_20230308_nycschools.data.network.SAT_network
import com.example.kylebomeisl_20230308_nycschools.ui.theme.SAT
import com.example.kylebomeisl_20230308_nycschools.ui.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DetailsScreen(
    school: String,
    navController: NavHostController,
    satList: MutableStateFlow<MutableList<SAT_network>>
) {
    val SAT = satList.collectAsState()

    if (SAT.value.isEmpty()) {
        Text(text = "Loading...")
    } else {

        var target = SAT.value.filter { it.school_name.lowercase().contentEquals(school.lowercase())}
        if (target.isEmpty()) {
            Text(text = "Loading...")
            if(target.isNotEmpty()) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SATCard(target = target)
                }

            }
        } else {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SATCard(target = target)
            }


        }
    }

}

@Composable
fun SATCard(target: List<SAT_network>){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(5.dp),

            ) {
            Text(
                text = target.first().school_name,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "SAT Scores",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            color = SAT
        )
        Divider()
        Spacer(modifier = Modifier.height(3.dp))
        Row() {
            Icon(painter = painterResource(id = R.drawable.outline_calculate_24),
                contentDescription = "",
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Average Math SAT Score:", fontSize = 17.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = target.first().sat_math_avg_score)
        }
        Row {
            Icon(painter = painterResource(id = R.drawable.baseline_menu_book_24),
                contentDescription = "",
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Average Critical Reading SAT Score:",
                fontSize = 17.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = target.first().sat_critical_reading_avg_score)
        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.baseline_create_24),
                contentDescription = "", tint = Color.Blue )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Average Writing SAT Score:", fontSize = 17.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = target.first().sat_writing_avg_score)
        }
        
        
        
    }
}