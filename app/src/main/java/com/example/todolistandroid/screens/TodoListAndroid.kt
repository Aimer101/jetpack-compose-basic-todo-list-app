package com.example.todolistandroid.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistandroid.ui.theme.TodoListAndroidTheme
import androidx.navigation.compose.rememberNavController
import com.example.todolistandroid.screens.create_item.CreateItem
import com.example.todolistandroid.screens.home_screen.HomeScreen
import com.example.todolistandroid.screens.home_screen.TodoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListAndroid(modifier: Modifier = Modifier) {
    val TAG = "TodoListAndroid"
    val navController = rememberNavController()
    val todoListViewModel: TodoListViewModel = viewModel(factory = TodoListViewModel.Factory)


    NavHost(
        navController = navController,
        startDestination = "/",
    ) {
        composable(route = "/") {
            HomeScreen(navController = navController, todoListViewModel = todoListViewModel)
        }

        composable(route = "/create") {
            CreateItem(navController = navController) {
                item -> todoListViewModel.addItemToMainList(item = item)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoListAndroidTheme {
        TodoListAndroid()
    }
}