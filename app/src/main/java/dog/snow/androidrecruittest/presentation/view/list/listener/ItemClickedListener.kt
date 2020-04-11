package dog.snow.androidrecruittest.presentation.view.list.listener

import android.widget.ImageView
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem

interface ItemClickedListener {

    fun onItemRowClicked(listItem: ListItem, imageView: ImageView)
}