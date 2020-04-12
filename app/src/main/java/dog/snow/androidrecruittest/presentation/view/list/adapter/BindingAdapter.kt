package dog.snow.androidrecruittest.presentation.view.list.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R

class BindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("android:imageUrl")
        fun loadImage(
            imageView: ImageView,
            imageUrl: String?
        ) {
            if (imageUrl == null) return

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView)
        }
    }
}