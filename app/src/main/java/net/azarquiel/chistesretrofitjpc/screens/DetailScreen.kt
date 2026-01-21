package net.azarquiel.chistesretrofitjpc.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.chistesretrofitjpc.R
import net.azarquiel.chistesretrofitjpc.viewmodel.MainViewModel


@Composable
fun DetailScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { DetailTopBar() },
        content = { padding ->
            DetailContent(padding, viewModel)
        },
        bottomBar = { DetailBottomBar(viewModel) }
    )
}

@Composable
fun DetailBottomBar(viewModel: MainViewModel) {
    var rating by remember { mutableStateOf(0) }
    val context = LocalContext.current
    BottomAppBar(
        containerColor = Color.Gray,
        contentColor = colorResource(R.color.orange)
    ) {
        RatingBar(
            rating = rating,
            onRatingSelected = { selectedRating ->
                rating = selectedRating
                viewModel.savePuntos(selectedRating)
                viewModel.getAvgChiste()
                Toast.makeText(
                    context,
                    "Anotadas ${selectedRating} estrellas...",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar() {
    TopAppBar(
        title = { Text(text = "Chiste completo") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}
@Composable
fun DetailContent(padding: PaddingValues, viewModel: MainViewModel) {
    val chiste = viewModel.getChisteSelected()
    val categoria = viewModel.getCategoriaSelected()
    viewModel.getAvgChiste()
    val contenido = AnnotatedString.fromHtml(chiste!!.contenido).toString()
    val avg = viewModel.avg.observeAsState("0")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apichistes/img/${chiste.idcategoria}.png",
                contentDescription = "categoria",
                modifier = Modifier.size(200.dp)
            )
        }
        Text(text = categoria!!.nombre, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textAlign = TextAlign.Center, fontSize = 22.sp)
        RatingBar(
            rating = avg.value.toInt(),
        )
        Text(text = contenido, modifier = Modifier.padding(6.dp), fontSize = 20.sp)
    }
}
@Composable
fun RatingBar(
    rating: Int,
    onRatingSelected: ((Int) -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Favorito",
                tint = if (i <= rating) colorResource(R.color.orange) else colorResource(R.color.black),
                modifier = Modifier
                    .size(40.dp)
                    .let { baseModifier ->
                        if (onRatingSelected != null) {
                            baseModifier.clickable { onRatingSelected(i) }
                        } else {
                            baseModifier
                        }
                    }
            )
        }
    }
}
