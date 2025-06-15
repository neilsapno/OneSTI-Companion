package sapno.cedric.onesticompanion

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPage(
    context: Context,
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(Color.White)
            .shadow(1.dp, RoundedCornerShape(1.dp))
            .padding(16.dp),
        content = {
            Column {
                Text(text = "OneSTI Companion")
                Text(text = "Version 1.0")
                Text(text = "Author: Cedric Sapno")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        openUrl(context, "https://www.facebook.com/cdrcspn")
                    }) {
                        Icon(
                            modifier = Modifier.weight(1f),
                            painter = painterResource(R.drawable.outline_digital_wellbeing_24),
                            contentDescription = "Facebook"
                        )
                    }
                    IconButton(onClick = {
                        openUrl(context, "https://github.com/neilsapno")
                    }) {
                        Icon(
                            modifier = Modifier.weight(1f),
                            painter = painterResource(R.drawable.outline_code_24),
                            contentDescription = "Github"
                        )
                    }
                }
            }
        }
    )
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open URL", Toast.LENGTH_LONG).show()
        Log.e("OpenUrl", "Exception for URL: $url", e)
    }
}
