package net.azarquiel.chistesretrofitjpc.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.chistesretrofitjpc.R
import net.azarquiel.chistesretrofitjpc.model.Categoria
import net.azarquiel.chistesretrofitjpc.model.Chiste
import net.azarquiel.chistesretrofitjpc.viewmodel.MainViewModel


@Composable
fun ChistesScreen(navController: NavHostController, viewModel: MainViewModel) {
    val categoria = viewModel.getCategoriaSelected()
    NewChisteDialog(viewModel)
    Scaffold(
        topBar = { ChistesTopBar(categoria) },
        floatingActionButton = { ChistesFAB(viewModel) },
        content = { padding ->
            ChistesContent(padding, viewModel, navController, categoria)
        },
    )
}

@Composable
fun ChistesFAB(viewModel: MainViewModel) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background,
        onClick = {
            viewModel.setDialogNewChiste(true)
        }) {
        Icon(Icons.Default.Edit, contentDescription = "Edit")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChistesTopBar(categoria: Categoria?) {
    TopAppBar(
        title = { Text(text = "Chistes de ${categoria!!.nombre}") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}
@Composable
fun ChistesContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    navController: NavHostController,
    categoria: Categoria?
) {
    viewModel.getChistes(categoria!!.id)
    val chistes = viewModel.chistes.observeAsState(listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),)
    {
        LazyColumn(
            Modifier.background(colorResource(R.color.azuloscuro)),
        ) {
            items(chistes.value ?: emptyList()) { chiste ->
                ChisteCard(chiste, viewModel, navController)
            }
        }
    }
}
@Composable
fun ChisteCard(
    chiste: Chiste,
    viewModel: MainViewModel,
    navController: NavHostController
){
    var contenido = AnnotatedString.fromHtml(chiste.contenido).toString()
    contenido = if (contenido.length>30) contenido.substring(0, 30) else contenido
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 3.dp)
            .clickable(onClick = {
                // pasar a la siguiente pantalla la tapa elegida
                viewModel.setChisteSelected(chiste)
                // Navegar a la siguiente pantalla
                navController.navigate("DetailScreen")
            }),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.azulclaro),
            contentColor = colorResource(R.color.azuloscuro)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apichistes/img/${chiste.idcategoria}.png",
                contentDescription = "categoria",
                modifier = Modifier.weight(1F)
            )
            Column( modifier = Modifier.weight(3F),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$contenido...",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center)
            }

        }
    }
}
@Composable
fun NewChisteDialog(viewModel: MainViewModel) {
    val openDialogNewChiste = viewModel.openDialogNewChiste.observeAsState(false)
    val contenido = remember { mutableStateOf("") }

    if (openDialogNewChiste.value) {
        AlertDialog(
            onDismissRequest = { viewModel.setDialogNewChiste(false) },
            title = { Text("New Chiste", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
            containerColor = colorResource(R.color.azulclaro),
            textContentColor = colorResource(R.color.azuloscuro),
            titleContentColor = colorResource(R.color.azuloscuro),
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = contenido.value,
                        onValueChange = { contenido.value = it },
                        label = { Text(text = "Contenido", color = colorResource(R.color.azuloscuro)) },
                        minLines = 4,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(R.color.azulmedio),
                            unfocusedContainerColor = colorResource(R.color.azulmediooscuro),
                            focusedTextColor = colorResource(R.color.azuloscuro),
                            unfocusedTextColor = colorResource(R.color.azulclaro)
                        ),
                    )
                }},
            confirmButton = {
                Button(onClick = {
                    viewModel.newChiste(contenido.value)
                    viewModel.setDialogNewChiste(false)
                    contenido.value=""

                },colors = ButtonDefaults.buttonColors
                    ( containerColor = colorResource(R.color.azuloscuro),
                    contentColor = colorResource(R.color.azulclaro)
                )) {
                    Text("New")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.setDialogNewChiste(false) },
                    colors = ButtonDefaults.buttonColors
                        ( containerColor = colorResource(R.color.azuloscuro),
                        contentColor = colorResource(R.color.azulclaro))) {
                    Text("Cancel")
                }
            }
        )
    }
}



