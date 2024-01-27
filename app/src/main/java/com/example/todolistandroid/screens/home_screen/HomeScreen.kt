package com.example.todolistandroid.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todolistandroid.common.FetchingState
import com.example.todolistandroid.composables.empty_payload.EmptyPayloadComposable
import com.example.todolistandroid.composables.error_screen.ErrorScreen
import com.example.todolistandroid.composables.item_tile.ItemTile
import com.example.todolistandroid.composables.loading_screen.LoadingScreen
import com.example.todolistandroid.models.Todo
import com.example.todolistandroid.ui.theme.TodoListAndroidTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, todoListViewModel : TodoListViewModel) {
    val uiState = todoListViewModel.uiState.collectAsState()

    LaunchedEffect(todoListViewModel) {
        todoListViewModel.getTodosList()
    }

    when (uiState.value.fetchingState) {
        FetchingState.ERROR -> ErrorScreen()

        FetchingState.SUCCESS -> {
            if (uiState.value.payload.isEmpty()) {
                EmptyPayloadComposable(navController = navController)
            } else {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Todo List")
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate("/create")
                        }) {
                            Icon(Icons.Filled.Add, "Create new item")
                        }
                    }


                ) { it ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),

                        ) {
                        items(uiState.value.payload) { item ->
                            ItemTile(item) {
                                it -> todoListViewModel.onDelete(it)
                            }

                        }
                    }
                }
            }
        }

        else -> {
            LoadingScreen()
        }
    }
}