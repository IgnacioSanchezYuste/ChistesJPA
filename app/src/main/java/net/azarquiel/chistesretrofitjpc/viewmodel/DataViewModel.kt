package net.azarquiel.chistesretrofitjpc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.chistesretrofitjpc.api.MainRepository
import net.azarquiel.chistesretrofitjpc.model.Categoria
import net.azarquiel.chistesretrofitjpc.model.Chiste
import net.azarquiel.chistesretrofitjpc.model.Punto
import net.azarquiel.chistesretrofitjpc.model.Usuario

// ……

/**
 * Created by Paco Pulido.
 */
class DataViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getCategorias(): MutableLiveData<List<Categoria>> {
        val categorias = MutableLiveData<List<Categoria>>()
        GlobalScope.launch(Main) {
            categorias.value = repository.getCategorias()
        }
        return categorias
    }

    fun getChistesByCategoria(idcategoria: Int):MutableLiveData<List<Chiste>> {
        val chistes= MutableLiveData<List<Chiste>>()
        GlobalScope.launch(Main) {
            chistes.value = repository.getChistesByCategoria(idcategoria)
        }
        return chistes
    }

    fun getDataUsuarioPorNickPass(nick: String, pass: String):MutableLiveData<Usuario> {
        val usuario= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuario.value = repository.getDataUsuarioPorNickPass(nick, pass)
        }
        return usuario
    }

    fun getAvgChiste(idchiste: Int):MutableLiveData<String> {
        val avg= MutableLiveData<String>()
        GlobalScope.launch(Main) {
            avg.value = repository.getAvgChiste(idchiste)
        }
        return avg
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioRespuesta= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioRespuesta.value = repository.saveUsuario(usuario)
        }
        return usuarioRespuesta
    }

    fun saveChiste(chiste: Chiste):MutableLiveData<Chiste> {
        val chisteRespuesta= MutableLiveData<Chiste>()
        GlobalScope.launch(Main) {
            chisteRespuesta.value = repository.saveChiste(chiste)
        }
        return chisteRespuesta
    }

    fun savePuntos(idchiste:Int, punto: Punto):MutableLiveData<Punto> {
        val puntoRespuesta= MutableLiveData<Punto>()
        GlobalScope.launch(Main) {
            puntoRespuesta.value = repository.savePuntos(idchiste, punto)
        }
        return puntoRespuesta
    }


}

