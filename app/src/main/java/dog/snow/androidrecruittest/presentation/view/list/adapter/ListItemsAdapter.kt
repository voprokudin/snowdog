package dog.snow.androidrecruittest.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.presentation.view.list.listener.ItemClickedListener
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import androidx.recyclerview.widget.ListAdapter
import dog.snow.androidrecruittest.databinding.ListItemBinding

class ListItemsAdapter : ListAdapter<ListItem, ListItemsAdapter.ViewHolder>(DIFFER) {

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem
        }
    }

    lateinit var itemsClickedListener: ItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, itemsClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ListItemBinding,
        private val itemsClickedListener: ItemClickedListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListItem) = with(binding) {
            ivThumb.transitionName = "trans_image$adapterPosition"
            photoItem = item
            root.setOnClickListener { itemsClickedListener.onItemRowClicked(item, ivThumb) }
        }
    }
}