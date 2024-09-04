package com.example.mytodoapp.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mytodoapp.data.Item
import com.example.mytodoapp.data.ItemsRepository

class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel()
{
    var itemUiState by mutableStateOf(ItemUiState())
        private set
    fun updateUiState(itemDetails: ItemDetails){
        itemUiState = ItemUiState(
            itemDetails = itemDetails,
            isEntryValid = validateInput(itemDetails)
        )
    }
    suspend fun saveItem(){
        if(validateInput()){
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(
        uiState:ItemDetails=itemUiState.itemDetails
    ):Boolean {
        return with(uiState) {
            title.isNotBlank() && description.isNotBlank()
        }
    }
}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean =false
)

data class ItemDetails(
    val id: Int=0,
    val title: String ="",
    val description: String="",
    val done: Boolean = false
)

fun ItemDetails.toItem(): Item =Item(
    id=id,
    title=title,
    description=description,
    done=done
)

fun Item.toItemUiState(
    isEntryValid:Boolean=false
): ItemUiState =ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid=isEntryValid
)

fun Item.toItemDetails():ItemDetails=ItemDetails(
    id=id,title=title,description=description,done=done
)