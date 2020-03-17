package dog.snow.androidrecruittest.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.presentation.view.VPListFragment
import dog.snow.androidrecruittest.presentation.view.list.listener.VPItemClickedListener
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import kotlinx.android.synthetic.main.list_item.view.*

class VPListAdapter(
    private val items: List<VPListItem>,
    private val itemsClickedListener: VPItemClickedListener,
    private val requireContext: VPListFragment
) : RecyclerView.Adapter<VPListAdapter.VPViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return VPViewHolder(itemView, itemsClickedListener)
    }

    override fun onBindViewHolder(holder: VPViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VPViewHolder(
        private val view: View,
        private val itemsClickedListener: VPItemClickedListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: VPListItem) = with(view) {
            Glide.with(requireContext)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivThumb)
            tvPhotoTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle
            setOnClickListener { itemsClickedListener.onItemRowClicked(item) }
        }
    }
}