package net.azarquiel.chistesretrofitjpc.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.chistesretrofitjpc.MainActivity
import net.azarquiel.chistesretrofitjpc.model.Categoria
import net.azarquiel.chistesretrofitjpc.model.Chiste
import net.azarquiel.chistesretrofitjpc.model.Punto
import net.azarquiel.chistesretrofitjpc.model.Usuario

class MainViewModel(activity: MainActivity) : ViewModel() {
    private val activity: MainActivity = activity
    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias
    private val _chistes = MutableLiveData<List<Chiste>>()
    val chistes: LiveData<List<Chiste>> = _chistes

    private val _avg = MutableLiveData<String>()
    val avg: LiveData<String> = _avg
    private var categoriaSelected: Categoria? = null
    private var chisteSelected: Chiste? = null
    private var usuario: Usuario? = null
    private val _openDialogLogin = MutableLiveData(false)
    val openDialogLogin: LiveData<Boolean> = _openDialogLogin
    private val _openDialogNewChiste = MutableLiveData(false)
    val openDialogNewChiste: LiveData<Boolean> = _openDialogNewChiste
    val dataviewModel = ViewModelProvider(activity)[DataViewModel::class.java]

    init {
        getCategorias()
    }

    private fun getCategorias() {
        dataviewModel.getCategorias().observe(activity) {
            it?.let {
                _categorias.value = it
                it.forEach {
                    Log.d("paco" , it.toString())
                }
            }
        }

    }

    fun setCategoriaSelected(categoria: Categoria) {
        categoriaSelected = categoria
    }

    fun getCategoriaSelected(): Categoria? {
        return categoriaSelected
    }

    fun getChistes(id: Int) {
        dataviewModel.getChistesByCategoria(id).observe(activity) {
            it?.let {
                _chistes.value = it
            }
        }
    }

    fun login(nick: String, pass: String) {
        dataviewModel.getDataUsuarioPorNickPass(nick, pass).observe(activity) {
            if (it != null) {
                usuario = it
            }
            else {
                dataviewModel.saveUsuario(Usuario(0, nick, pass)).observe(activity) {
                    it?.let {
                       usuario = it
                    }
                }
            }
        }
    }
    fun getAvgChiste() {
        dataviewModel.getAvgChiste(chisteSelected!!.id).observe(activity) {
            it?.let {
                _avg.value = it
            }
        }
    }

    fun savePuntos(puntos: Int) {
        val punto = Punto(0, chisteSelected!!.id, puntos)
        dataviewModel.savePuntos(chisteSelected!!.id, punto).observe(activity) {
            it?.let {
                Toast.makeText(activity, "Anotadas ${puntos} estrellas...", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setChisteSelected(chiste: Chiste) {
        chisteSelected = chiste
    }

    fun getChisteSelected(): Chiste? {
        return chisteSelected
    }

    fun setDialogLogin(open: Boolean) {
        _openDialogLogin.value = open
    }

    fun setDialogNewChiste(open: Boolean) {
        _openDialogNewChiste.value = open
    }

    fun newChiste(contenido: String) {
        val chiste = Chiste(0, categoriaSelected!!.id, contenido)

        dataviewModel.saveChiste(chiste).observe(activity) {
            if (it != null) {
                Toast.makeText(activity, "Almacenado chiste...", Toast.LENGTH_LONG).show()
                val chistes = _chistes.value
                val newchistes = ArrayList(chistes!!)
                newchistes.add(0, it)
                _chistes.value = newchistes
            }
            else {
                Toast.makeText(activity, "Error al almacenar chiste...", Toast.LENGTH_LONG).show()
            }
        }
    }
}