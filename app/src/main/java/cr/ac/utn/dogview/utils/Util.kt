package cr.ac.utn.dogview.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

const val EXTRA_MESSAGE_ID = "cr.ac.utn.dogview"

class Util {
    companion object {
        /**
         * Navega a otra actividad con un extra opcional
         *
         * @param context Contexto actual de la actividad
         * @param objclass Clase de la actividad destino
         * @param extraName Nombre del extra (opcional)
         * @param value Valor del extra (opcional)
         */
        fun openActivity(context: Context, objclass: Class<*>, extraName: String = "", value: String? = null) {
            val intent = Intent(context, objclass).apply {
                if (extraName.isNotEmpty() && value != null) {
                    putExtra(extraName, value)
                }
            }
            startActivity(context, intent, null)
        }
    }
}
