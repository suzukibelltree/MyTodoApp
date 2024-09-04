package com.example.mytodoapp.ui.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytodoapp.R
import com.example.mytodoapp.ui.navigation.NavigationDestination
import com.example.mytodoapp.ui.home.TodoAppBar
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import com.example.mytodoapp.ui.AppViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytodoapp.data.Item
import com.example.mytodoapp.data.ItemsRepository
import com.example.mytodoapp.ui.dialog.DeleteItemDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

object ItemEditDestination: NavigationDestination {
    override val route="itemEdit"
    override val titleRes= R.string.edit_item_title
    const val itemIdArg ="itemId"
    val routeWithArgs="$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    navigateBack:() ->Unit={},
    onNavigateUp:()->Unit={},
    modifier: Modifier = Modifier,
    viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope= rememberCoroutineScope()
    var showDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar= {
            TodoAppBar(
                title = stringResource(id = ItemEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    )
    { innerPadding ->
        ItemEntryBody(
            itemUiState=viewModel.itemUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
                }
                navigateBack()
            },
            showDelete = true,
            onDeleteClick = {showDialog=true},
            modifier=Modifier.padding(innerPadding)
        )

    }
    if(showDialog){
        DeleteItemDialog(
            onConfirm = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                }
                navigateBack()
            },
            onDismiss = {showDialog=false})

        }
    }

@Preview(showBackground = true)
@Composable
fun ItemEditScreenPreview(){
    val mockObject =object :ItemsRepository{
        override fun getAllItemsStream(): Flow<List<Item>> = emptyFlow()
        override fun getItemStream(id: Int): Flow<Item?> = MutableStateFlow(
            Item(1,"牛乳を買う","2カートン",false)
        )

        override suspend fun insertItem(item: Item) {
        }

        override suspend fun deleteItem(item: Item) {
        }

        override suspend fun updateItem(item: Item) {
        }
    }
    ItemEditScreen(
        viewModel = ItemEditViewModel(
            savedStateHandle = SavedStateHandle(mapOf("itemId" to 1)),
            itemsRepository = mockObject
        )
    )
}