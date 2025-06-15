package sapno.cedric.onesticompanion

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.web.MiCode.MiCode


@Composable
fun WebViewPage(
    webViewController: WebViewController,
    initialUrl: String,
    injectedJavaScript: String? = null,
    modifier: Modifier = Modifier,
    onPageFinished: ((String) -> Unit)? = null
) {
    var progress by remember { mutableStateOf(0) }
    Column(modifier = modifier) {
        if (progress in 1..99) {
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            )
        }
        AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context).apply {
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            progress = newProgress
                            super.onProgressChanged(view, newProgress)
                        }
                    }
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            url?.let {
                                onPageFinished?.invoke(it)
                                if (url.endsWith("ViewGrades")) {
                                    injectedJavaScript?.let { js ->
                                        view?.evaluateJavascript(js, null)
                                    } ?: run {
                                        view?.evaluateJavascript(MiCode, null)
                                    }
                                }
                            }
                        }
                    }
                    settings.javaScriptEnabled = true
                    loadUrl(initialUrl)
                    webViewController.setWebView(this)
                }
            },
            update = {
                // Optionally update could reload etc.
            }
        )
    }
}