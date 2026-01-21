package net.azarquiel.chistesretrofitjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import net.azarquiel.chistesretrofitjpc.navigation.AppNavigation
import net.azarquiel.chistesretrofitjpc.ui.theme.ChistesRetrofitJPCTheme
import net.azarquiel.chistesretrofitjpc.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = remember { MainViewModel(this@MainActivity) }
            ChistesRetrofitJPCTheme {
                AppNavigation(mainViewModel)
            }
        }
    }
}