package util

import android.content.Context
import android.content.Intent

const val EXTRA_MESSAGE_ID = "com.example.investigacion02.Id"

class Util {
    companion object {
        fun openActivity(context: Context, objclass: Class<*>, extraName: String? = null, value: String? = null) {
            val intent = Intent(context, objclass).apply {
                if (!value.isNullOrEmpty() && extraName != null) {
                    putExtra(extraName, value)
                }
            }
            context.startActivity(intent)
        }
    }
}
