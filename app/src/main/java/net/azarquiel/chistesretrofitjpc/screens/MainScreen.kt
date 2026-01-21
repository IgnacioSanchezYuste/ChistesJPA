package net.azarquiel.chistesretrofitjpc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.chistesretrofitjpc.R
import net.azarquiel.chistesretrofitjpc.model.Categoria
import net.azarquiel.chistesretrofitjpc.viewmodel.MainViewModel


@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        topBar = { MainTopBar(textState, viewModel) },
        content = { padding ->
            MainContent(padding, viewModel, navController, textState)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(textState: MutableState<TextFieldValue>, viewModel: MainViewModel) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categorias", Modifier
                        .padding(end = 4.dp)
                )


                Box(modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)) {
                    SearchView(textState)
                }
                Image(
                    painter = painterResource(R.drawable.pacochistes),
                    contentDescription = "pacochistes",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 4.dp)
                        .clip(CircleShape)
                        .background(colorResource(R.color.azulclaro))
                        .clickable(onClick = {
                            viewModel.setDialogLogin(true)
                        })
                )
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun MainContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    navController: NavHostController,
    textState: MutableState<TextFieldValue>
) {
    LoginDialog(viewModel)
    val categorias = viewModel.categorias.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    )
    {

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "header",
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentScale = ContentScale.FillWidth
        )

        LazyColumn(
            Modifier.background(colorResource(R.color.azuloscuro)),
        ) {
            items(categorias.value.filter {
                it.nombre.contains(textState.value.text, ignoreCase = true)
            }
                ?: emptyList()) { categoria ->
                CategoriaCard(categoria, viewModel, navController)
            }
        }
    }
}

@Composable
fun CategoriaCard(
    categoria: Categoria,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 3.dp)
            .clickable(onClick = {
                // pasar a la siguiente pantalla la tapa elegida
                viewModel.setCategoriaSelected(categoria)
                // Navegar a la siguiente pantalla
                navController.navigate("ChistesScreen")
            }),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.azulclaro),
            contentColor = colorResource(R.color.azuloscuro)
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apichistes/img/${categoria.id}.png",
                contentDescription = "categoria",
                modifier = Modifier.weight(1F)
            )
            Column(
                modifier = Modifier.weight(3F),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${categoria.nombre}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
    }

}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    BasicTextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(colorResource(R.color.azulclaro))
            .padding(16.dp, 3.dp),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = colorResource(R.color.azuloscuro), fontSize = 20.sp,
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.value.text.isEmpty()) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon",
                        Modifier.wrapContentSize(),
                        tint = colorResource(R.color.azuloscuro)
                    )
                    Text(
                        text = "Search here...",
                        color = colorResource(R.color.azuloscuro)
                    )
                }
            }
            innerTextField()
        }
    )
}

@Composable
fun LoginDialog(viewModel: MainViewModel) {
    val openDialogLogin = viewModel.openDialogLogin.observeAsState(false)
    val nick = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }

    if (openDialogLogin.value) {
        AlertDialog(
            onDismissRequest = { viewModel.setDialogLogin(false) },
            title = {
                Text(
                    "Login/Register",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },

            containerColor = colorResource(R.color.azulclaro),
            textContentColor = colorResource(R.color.azuloscuro),
            titleContentColor = colorResource(R.color.azuloscuro),
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.pacochistes),
                        contentDescription = "imagen derecha",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )

                    // Campo usuario
                    TextField(
                        value = nick.value,
                        onValueChange = { nick.value = it },
                        label = { Text(text = "Nick", color = colorResource(R.color.azuloscuro)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(R.color.azulmedio),
                            unfocusedContainerColor = colorResource(R.color.azulmediooscuro),
                            focusedTextColor = colorResource(R.color.azuloscuro),
                            unfocusedTextColor = colorResource(R.color.azulclaro)
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = pass.value,
                        onValueChange = { pass.value = it },
                        label = {
                            Text(
                                text = "Password",
                                color = colorResource(R.color.azuloscuro)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(R.color.azulmedio),
                            unfocusedContainerColor = colorResource(R.color.azulmediooscuro),
                            focusedTextColor = colorResource(R.color.azuloscuro),
                            unfocusedTextColor = colorResource(R.color.azulclaro)
                        ),
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.login(nick.value, pass.value)
                        viewModel.setDialogLogin(false)
                        nick.value = ""
                        pass.value = ""

                    }, colors = ButtonDefaults.buttonColors
                        (
                        containerColor = colorResource(R.color.azuloscuro),
                        contentColor = colorResource(R.color.azulclaro)
                    )
                ) {
                    Text("Enter")
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.setDialogLogin(false) },
                    colors = ButtonDefaults.buttonColors
                        (
                        containerColor = colorResource(R.color.azuloscuro),
                        contentColor = colorResource(R.color.azulclaro)
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}



