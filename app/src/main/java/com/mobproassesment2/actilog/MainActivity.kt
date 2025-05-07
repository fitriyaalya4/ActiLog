package com.mobproassesment2.actilog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mobproassesment2.actilog.navigation.SetupNavGraph
import com.mobproassesment2.actilog.ui.theme.ActiLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActiLogTheme {
                SetupNavGraph()
            }
        }
    }
}

