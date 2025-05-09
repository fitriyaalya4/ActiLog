package com.mobproassesment2.actilog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
<<<<<<< HEAD
import com.mobproassesment2.actilog.navigation.SetupNavGraph
=======
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
>>>>>>> b94639762f65e6dc734d2165cfd1f333c57a71b0
import com.mobproassesment2.actilog.ui.theme.ActiLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActiLogTheme {
<<<<<<< HEAD
                SetupNavGraph()
=======
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
>>>>>>> b94639762f65e6dc734d2165cfd1f333c57a71b0
            }
        }
    }
}

<<<<<<< HEAD
=======
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActiLogTheme {
        Greeting("Android")
    }
}
>>>>>>> b94639762f65e6dc734d2165cfd1f333c57a71b0
