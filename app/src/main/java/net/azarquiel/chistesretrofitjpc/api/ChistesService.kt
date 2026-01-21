package net.azarquiel.chistesretrofitjpc.api

import kotlinx.coroutines.Deferred
import net.azarquiel.chistesretrofitjpc.model.Chiste
import net.azarquiel.chistesretrofitjpc.model.Punto
import net.azarquiel.chistesretrofitjpc.model.Respuesta
import net.azarquiel.chistesretrofitjpc.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChistesService {
    @GET("categorias")
    fun getCategorias(): Deferred<Response<Respuesta>>

    @GET("categoria/{idcategoria}/chistes")
    fun getChistesByCategoria(@Path("idcategoria") idcategoria: Int): Deferred<Response<Respuesta>>

    @GET("chiste/{idchiste}/avgpuntos")
    fun getAvgChiste(@Path("idchiste") idchiste: Int): Deferred<Response<Respuesta>>
    // nick y pass variables sueltas en la url?nick=paco&pass=paco => @Query

    @GET("usuario")
    fun getDataUsuarioPorNickPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String): Deferred<Response<Respuesta>>

    // post con objeto => @Body
    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @POST("chiste/{idchiste}/punto")
    fun savePuntos(@Path("idchiste") idchiste: Int, @Body punto: Punto): Deferred<Response<Respuesta>>

    @POST("chiste")
    fun saveChiste(@Body chiste: Chiste): Deferred<Response<Respuesta>>

}