package com.example.kylebomeisl_20230308_nycschools.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kylebomeisl_20230308_nycschools.ui.theme.DarkGreen
import com.example.kylebomeisl_20230308_nycschools.ui.viewmodels.DetailsViewModel
import com.example.kylebomeisl_20230308_nycschools.ui.viewmodels.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HostView(
    homeViewModel: HomeViewModel = viewModel(),
    detailsViewModel: DetailsViewModel = viewModel()
    ) {

        val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = { NYCTopNavBar(navController = navController) },
        content = {
            Surface(
                modifier = Modifier
                    .padding(top = 80.dp, start = 20.dp, bottom = 80.dp, end = 20.dp)
            ) {

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            navController,
                            homeViewModel.schoolList
                        )
                    }
                    composable("details/{school}") {
                        val school = it.arguments?.getString("school")
                        school?.let {
                            DetailsScreen(
                                school,
                                navController = navController,
                                detailsViewModel.satList
                            )
                        }

                    }
                }
            }
        },
        bottomBar = {}
    )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYCTopNavBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(
            "NYC Schools",
            fontFamily = FontFamily.Monospace,
            fontSize = 25.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
                },
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )}
        },
        colors = TopAppBarDefaults
            .mediumTopAppBarColors(
                containerColor = DarkGreen,
                navigationIconContentColor = Color.DarkGray,
                actionIconContentColor = Color.Blue
            )
    )
}
