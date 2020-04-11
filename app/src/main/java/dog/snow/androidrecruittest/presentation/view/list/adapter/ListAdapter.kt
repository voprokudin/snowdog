package dog.snow.androidrecruittest.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.presentation.view.list.listener.ItemClickedListener
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.tvPhotoTitle
import kotlinx.android.synthetic.main.list_item.view.tvAlbumTitle
import kotlinx.android.synthetic.main.list_item.view.ivThumb

class ListAdapter(
    private var items: List<ListItem>,
    private val itemsClickedListener: ItemClickedListener
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView, itemsClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(listItems: List<ListItem>) {
        items = listItems
        notifyDataSetChanged()
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