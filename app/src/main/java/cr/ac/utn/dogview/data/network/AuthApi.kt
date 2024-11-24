package cr.ac.utn.dogview.data.network

import cr.ac.utn.dogview.data.models.LoginRequest
import cr.ac.utn.dogview.data.models.LoginResponse
import cr.ac.utn.dogview.data.models.RegisterRequest
import cr.ac.utn.dogview.data.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface para manejar las operaciones de red relacionadas con autenticación y técnicos.
 */
interface AuthApi {

    /**
     * Valida la autenticación de un técnico.
     * @param loginRequest Datos del técnico para autenticar.
     * @return Respuesta del servidor con los datos del técnico autenticado o un error.
     */
    @POST("technicians/validateAuth")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    /**
     * Obtiene la lista completa de técnicos registrados.
     * @return Respuesta con la lista de técnicos.
     */
    @GET("technicians")
    suspend fun getTechnicians(): Response<List<LoginResponse>>

    /**
     * Registra un nuevo técnico en el sistema.
     * @param newTechnician Datos del técnico a registrar.
     * @return Respuesta del servidor confirmando el registro.
     */
    @POST("technicians")
    suspend fun registerTechnician(
        @Body newTechnician: RegisterRequest
    ): Response<RegisterResponse>
}
