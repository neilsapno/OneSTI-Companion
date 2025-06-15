package sapno.cedric.onesticompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import sapno.cedric.onesticompanion.ui.theme.OneSTICompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webViewController = WebViewController()
        setContent {
            OneSTICompanionTheme {
                val openAboutDialog = remember { mutableStateOf(false) }
                when {
                    openAboutDialog.value -> {
                        AboutPage(
                            context = this,
                            onDismissRequest = { openAboutDialog.value = false },
                        )
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBar(webViewController, openAboutDialog)
                    }
                )
                { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        WebViewPage(
                            initialUrl = "https://one.sti.edu/ViewGrades",
                            modifier = Modifier.fillMaxSize(),
                            webViewController = webViewController,
                            injectedJavaScript = null
                        )
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar(
        webViewController: WebViewController,
        openAboutPage: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(text = "OneSTI Companion")
            },
            actions = {
                IconButton(onClick = { webViewController.reload() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
                IconButton(onClick = { openAboutPage.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "Info"
                    )
                }
            }
        )
    }
}