package dog.snow.androidrecruittest.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.presentation.view.list.listener.ItemClickedListener
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.tvPhotoTitle
import kotlinx.android.synthetic.main.list_item.view.tvAlbumTitle
import kotlinx.android.synthetic.main.list_item.view.ivThumb
import androidx.recyclerview.widget.ListAdapter

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView, itemsClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val view: View,
        private val itemsClickedListener: ItemClickedListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: ListItem) = with(view) {
            ivThumb.transitionName = "trans_image$adapterPosition"
            Picasso.get()
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivThumb)
            tvPhotoTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle
            setOnClickListener { itemsClickedListener.onItemRowClicked(item, ivThumb) }
        }
    }
}