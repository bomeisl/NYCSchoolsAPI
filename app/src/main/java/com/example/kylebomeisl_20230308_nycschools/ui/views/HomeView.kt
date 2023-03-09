package com.example.kylebomeisl_20230308_nycschools.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kylebomeisl_20230308_nycschools.data.databases.School_DB
import com.example.kylebomeisl_20230308_nycschools.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    navController: NavController,
    schoolList: StateFlow<List<School_DB>>
) {
    var schoolList = schoolList.collectAsState()


    LazyColumn{

        items(schoolList.value) {school->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(15.dp)) {
                Text(text = school.name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center)
                Divider()
                Text(text = school.location,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                    )
                Divider()
                TextButton(onClick = {
                    navController.navigate(
                        "details/{school}"
                            .replace(
                                oldValue = "{school}",
                                newValue = "${school.name}"
                            )
                    )
                },
                    Modifier.background(Color.LightGray)
                ) {
                    Text(text = "${school.name} SAT Scores", fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
