package com.example.mytodoapp.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytodoapp.R
import com.example.mytodoapp.data.Item
import com.example.mytodoapp.data.ItemsRepository
import com.example.mytodoapp.ui.AppViewModelProvider
import com.example.mytodoapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


object HomeDestination: NavigationDestination {
    override val route="home"
    override val titleRes= R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry:()->Unit={},
    navigateToItemUpdate:(Int) ->Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel=
        viewModel(factory= AppViewModelProvider.Factory)
){
    val itemList by viewModel.homeUiState.itemList
        .collectAsState(initial = emptyList())
    var showDone by remember { mutableStateOf(false) }
    var filteredItemList by remember(itemList,showDone){
        mutableStateOf(itemList.filter {
            if(showDone) true
            else it.done == false
        })
    }
    val scrollBehavior=TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar={
            TodoAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                scrollBehavior=scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape=MaterialTheme.shapes.medium,
                modifier=Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.item_entry_title)
                )
            }
        }) { innerpadding ->
        HomeBody(
            itemList = filteredItemList,
            onItemClick ={ navigateToItemUpdate(it.id)},
            onCheckedChange = { checked -> showDone =checked },
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize()
        )
    }
}


@Preview
@Composable
fun PreviewHomeScreen(){
    val mockObject = object : ItemsRepository {
        override fun getAllItemsStream(): Flow<List<Item>> = MutableStateFlow(
            listOf(
                Item(1,"タイトル","詳細",true),
                Item(2,"タイトル","詳細",false)
            )
        )
        override fun getItemStream(id:Int): Flow<Item> = MutableStateFlow(
            Item(1,"タイトル","詳細",true)
        )

        override suspend fun insertItem(item: Item) {
            TODO("Not yet implemented")
        }

        override suspend fun updateItem(item: Item) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteItem(item: Item) {
            TODO("Not yet implemented")
        }
    }
    HomeScreen(
        navigateToItemEntry = {},
        navigateToItemUpdate = {},
        viewModel = HomeViewModel(mockObject)
    )
}