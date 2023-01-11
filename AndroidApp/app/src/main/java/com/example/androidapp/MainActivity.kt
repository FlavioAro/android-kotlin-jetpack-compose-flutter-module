package com.example.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.androidapp.components.MyButton
import com.example.androidapp.ui.theme.AndroidAppTheme
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

private const val FLUTTER_ENGINE_ID = "module_flutter_engine"

class MainActivity : ComponentActivity() {
    lateinit var flutterEngine: FlutterEngine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAppTheme {
                // Instantiate a FlutterEngine
                flutterEngine = FlutterEngine(this)

                // Start executing Dart code to pre-warm the FlutterEngine
                flutterEngine.dartExecutor.executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
                )

                // Cache the FlutterEngine to be used by FlutterActivity
                FlutterEngineCache
                    .getInstance()
                    .put(FLUTTER_ENGINE_ID, flutterEngine)

                // Main
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = "Android App Jetpack Compose") },
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MyButton(
            onClick = {
                context.startActivity(
                    FlutterActivity
                        .withCachedEngine(FLUTTER_ENGINE_ID)
                        .build(context)
                )
            },
            text = "OPEN FLUTTER MODULE"
        )
    }
}