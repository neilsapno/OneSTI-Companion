package sapno.cedric.onesticompanion

import android.webkit.WebView

class WebViewController {
    private var webView: WebView? = null

    fun setWebView(webView: WebView) {
        this.webView = webView
    }

    fun reload() {
        webView?.reload()
    }
}