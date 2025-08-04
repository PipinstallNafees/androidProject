package com.example.cinemaxApp.feature.admin.verifyTicket.view

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
//import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.cinemaxApp.core.navigation.Screen
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun VerifyTicketScreen(nav: NavHostController) {
    QRPermissionWrapper(nav)
}

@Composable
fun QRPermissionWrapper(nav: NavHostController) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val permissionState = remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionState.value = granted
    }

    LaunchedEffect(Unit) {
        if (!permissionState.value) {
            launcher.launch(cameraPermission)
        }
    }

    if (permissionState.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            QRScannerScreen { result ->
                val ticketJson = URLEncoder.encode(result, StandardCharsets.UTF_8.toString())
                nav.navigate(Screen.TicketDetails.createRoute(ticketJson))
                Log.d("Scanned", result)
            }
            QRScannerOverlay(nav = nav)
        }
    } else {
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text("Camera permission required")
        }
    }
}


@OptIn(ExperimentalGetImage::class)
@Composable
fun QRScannerScreen(onResult: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
        val previewView = PreviewView(ctx)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val barcodeScanner = BarcodeScanning.getClient()

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(android.util.Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(ctx), { imageProxy ->
                val mediaImage = imageProxy.image
                if (mediaImage != null) {
                    val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                    barcodeScanner.process(inputImage)
                        .addOnSuccessListener { barcodes ->
                            for (barcode in barcodes) {
                                barcode.rawValue?.let {
                                    onResult(it)
                                    cameraProvider.unbindAll()
                                }
                            }
                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }
                }
            })

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageAnalysis
            )

        }, ContextCompat.getMainExecutor(ctx))

        previewView
    })
}


@Composable
fun QRScannerOverlay(
    modifier: Modifier = Modifier,
    scanBoxSize: Dp = 250.dp,
    nav: NavHostController
) {
    val boxSizePx = with(LocalDensity.current) { scanBoxSize.toPx() }
    val infiniteTransition = rememberInfiniteTransition(label = "scan_line")

    // Animated scan line offset
    val scanLineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = boxSizePx,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan_offset"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Dimmed background with transparent cutout
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            val scanRectLeft = centerX - boxSizePx / 2
            val scanRectTop = centerY - boxSizePx / 2
            val scanRectRight = centerX + boxSizePx / 2
            val scanRectBottom = centerY + boxSizePx / 2

            drawRect(
                color = Color(0xAA000000)
            )

            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(scanRectLeft, scanRectTop),
                size = Size(boxSizePx, boxSizePx),
                cornerRadius = CornerRadius(20f, 20f),
                blendMode = BlendMode.Clear
            )
        }

        // Framed white border box
        Box(
            modifier = Modifier
                .size(scanBoxSize)
                .align(Alignment.Center)
                .border(2.dp, Color.White, RoundedCornerShape(16.dp))
        )

//        // Scanning line
//        Box(
//            modifier = Modifier
//                .size(scanBoxSize)
//                .align(Alignment.Center)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(2.dp)
//                    .background(Color.Cyan)
//                    .offset { IntOffset(0, scanLineOffset.toInt()) }
//            )
//        }

        // Top bar with back button and title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Scan QR Code",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )
//            Spacer(modifier = Modifier.width(12.dp))
        }

        // Helper text below scan box
        Text(
            text = "Align QR code within frame",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = scanBoxSize + 50.dp) // Before 20.dp
        )
    }
}
