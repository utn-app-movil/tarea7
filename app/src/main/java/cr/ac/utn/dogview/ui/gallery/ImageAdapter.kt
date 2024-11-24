package cr.ac.utn.dogview.ui.gallery

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageAdapter(private val context: Context, private val images: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView = convertView as? ImageView ?: ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(600, 600) // Tamaño de las imágenes
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        // Cargar la imagen con Glide
        Glide.with(context)
            .load(images[position])
            .into(imageView)

        return imageView
    }
}
