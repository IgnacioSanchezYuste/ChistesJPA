package net.azarquiel.chistesretrofitjpc.model

data class Categoria(var id:Int=0, var nombre:String="")
data class Chiste(var id:Int=0, var idcategoria:Int=0, var contenido:String="")
data class Usuario(var id:Int=0, var nick:String="", var pass:String="")

data class Punto (var id: Int, var idchiste: Int, var puntos: Int)

data class Respuesta(
    val categorias:List<Categoria>,
    val chistes: List<Chiste>,
    val usuario: Usuario,
    val avg: String,
    val punto: Punto,
    val chiste: Chiste
)