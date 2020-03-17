package dog.snow.androidrecruittest.presentation.view.list.listener

import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem

interface VPItemClickedListener {

    fun onItemRowClicked(listItem: VPListItem)
}