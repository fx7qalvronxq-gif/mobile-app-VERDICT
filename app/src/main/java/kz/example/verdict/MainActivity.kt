package kz.example.verdict

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kz.example.verdict.ui.navigation.AppNavigation
import kz.example.verdict.ui.theme.VerdictTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VerdictTheme {
                AppNavigation()
            }
        }
    }
}
