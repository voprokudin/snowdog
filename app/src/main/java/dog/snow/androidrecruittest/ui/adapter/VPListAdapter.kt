package dog.snow.androidrecruittest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.presentation.view.list.listener.VPItemClickedListener
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.ArrayList

class VPListAdapter(
    private val items: ArrayList<String>,
    private val itemsClickedListener: VPItemClickedListener
) : RecyclerView.Adapter<VPListAdapter.VPViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return VPViewHolder(itemView, itemsClickedListener)
    }

    override fun onBindViewHolder(holder: VPViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VPViewHolder(
        private val view: View,
        private val productClickedListener: VPItemClickedListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: String) = with(view) {
//            val ivThumb: ImageView = findViewById(R.id.iv_thumb)
            tvPhotoTitle.text = item
            tvAlbumTitle.text = item
            //TODO: display item.thumbnailUrl in ivThumb
            setOnClickListener { productClickedListener.onItemRowClicked() }
        }
    }
}