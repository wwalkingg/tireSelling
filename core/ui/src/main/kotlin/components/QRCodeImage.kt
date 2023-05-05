package components

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import kotlin.math.min

@Composable
fun QRCodeImage(modifier: Modifier = Modifier, content: String, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    BoxWithConstraints(modifier.layout { measurable, constraints ->
        val squareSize = min(constraints.maxHeight, constraints.maxWidth)
        val placeable = measurable.measure(constraints.copy(maxWidth = squareSize, maxHeight = squareSize))
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }) {
        val density = LocalDensity.current
        val size = with(density) {
            IntSize(this@BoxWithConstraints.maxWidth.roundToPx(), this@BoxWithConstraints.maxHeight.roundToPx())
        }
        val qrCodeBitmap by remember(content) { derivedStateOf { createQRCode(content, size = size) } }
        if (qrCodeBitmap == null) {
            QRCodeImageFail(Modifier.fillMaxSize())
        } else {
            Image(
                bitmap = qrCodeBitmap!!.asImageBitmap(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(contentPadding),
                contentDescription = content,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun QRCodeImageFail(modifier: Modifier = Modifier) {
    Surface(modifier, color = MaterialTheme.colorScheme.errorContainer) {
        Box(Modifier.fillMaxSize()) {
            Text("Create QRCode Failed", modifier = Modifier.align(Alignment.Center))
        }
    }
}


private fun createQRCode(content: String, size: IntSize = IntSize(300, 300)): Bitmap? {
    val hints = mapOf(EncodeHintType.CHARACTER_SET to "UTF-8", EncodeHintType.MARGIN to 0) // 编码方式
    try {
        val bitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE, size.width, size.height, hints
        )
        val pixels = IntArray(size.width * size.height)
        for (y in 0 until size.height) {
            for (x in 0 until size.width) {
                if (bitMatrix[x, y]) {
                    pixels[y * size.width + x] = Color.BLACK
                } else {
                    pixels[y * size.width + x] = Color.TRANSPARENT
                }
            }
        }
        val bitmap = Bitmap.createBitmap(
            size.width, size.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(pixels, 0, size.width, 0, 0, size.width, size.height)
        return bitmap
    } catch (e: Exception) {
        Log.e("QRCodeImage", "createQRCode: ${e.message}")
    }
    return null
}