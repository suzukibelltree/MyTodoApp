package com.example.mytodoapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.mytodoapp.data.Item
import com.example.mytodoapp.data.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class HomeUiState(val itemList: Flow<List<Item>> = flowOf(listOf()))

class HomeViewModel(itemsRepository: ItemsRepository):ViewModel(){
    val homeUiState = HomeUiState(itemsRepository.getAllItemsStream())
}