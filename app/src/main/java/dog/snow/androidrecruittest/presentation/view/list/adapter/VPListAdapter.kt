package dog.snow.androidrecruittest.presentation.view.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.presentation.view.VPListFragment
import dog.snow.androidrecruittest.presentation.view.list.listener.VPItemClickedListener
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import kotlinx.android.synthetic.main.list_item.view.tvPhotoTitle
import kotlinx.android.synthetic.main.list_item.view.tvAlbumTitle
import kotlinx.android.synthetic.main.list_item.view.ivThumb

class VPListAdapter(
    private var items: List<VPListItem>,
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

    fun updateList(listItems: List<VPListItem>) {
        items = listItems
        notifyDataSetChanged()
    }

    inner class VPViewHolder(
        private val view: View,
        private val itemsClickedListener: VPItemClickedListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: VPListItem) = with(view) {
            val header = LazyHeaders.Builder().addHeader("user-agent", "").build()
            val glideUrl = GlideUrl(item.thumbnailUrl, header)
            Glide.with(requireContext)
                .load(glideUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivThumb)
            tvPhotoTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle
            setOnClickListener { itemsClickedListener.onItemRowClicked(item) }
        }
    }
}