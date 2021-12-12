package com.example.carsrental.view.composable

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.carsrental.R
import com.example.carsrental.utils.ImageUtils.toBitmap

import com.example.carsrental.viewmodel.CarInputViewModel
import com.example.carsrental.viewmodel.EditCarViewModel
import java.util.*


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CarInputScreen(viewModel: CarInputViewModel, backAction: () -> Unit, title: String) {
    Column() {
        TopAppBar(title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }, navigationIcon = {
            IconButton(onClick = { backAction() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }, actions = {

        })
        CarForm(viewModel = viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CarForm(viewModel: CarInputViewModel) {
    val brand by viewModel.brand.observeAsState(viewModel.brand.value)
    val model by viewModel.model.observeAsState(viewModel.model.value)
    val fabricationYear by viewModel.year.observeAsState(viewModel.year.value)
    val carImage by viewModel.carImage.observeAsState(viewModel.carImage.value)
    val isAutomatic by viewModel.isAutomatic.observeAsState(false)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp)
    ) {
        CarImageUpload(
            image = carImage ?: "",
            viewModel::onCameraTriggered,
            viewModel::onGalleryTriggered
        )
        CarFieldInput(
            label = "Brand*",
            text = brand ?: "",
            onTextChange = viewModel::onBrandChange
        )
        CarFieldInput(
            label = "Model*",
            text = model ?: "",
            onTextChange = viewModel::onModelChange
        )
        CarFieldInput(
            label = "Fabrication Year*",
            text = fabricationYear ?: "",
            onTextChange = viewModel::onYearChange
        )
        CarCheckbox(isChecked = isAutomatic, onCheckChange = viewModel::onIsAutomaticChange)
        SubmitButton(viewModel::onSubmit)
    }
}

@Composable
fun CarFieldInput(label: String, text: String, onTextChange: (text: String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterVertically),
            value = text,
            onValueChange = {
                onTextChange(it)
            }, label = { Text(text = label) }
        )
    }
}

@Composable
fun CarCheckbox(isChecked: Boolean, onCheckChange: (checkStatus: Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier
                .padding(42.dp, 24.dp, 12.dp, 24.dp)
                .fillMaxWidth(0.1f),
            checked = isChecked,
            onCheckedChange = {
                onCheckChange(it)
            })
        Text(text = "Automatic", modifier = Modifier.fillMaxWidth(0.9f))
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CarImageUpload(
    image: String,
    onCameraAction: () -> Unit,
    onGalleryAction: (Uri?, Context) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val showCameraDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (showDialog.value) {
        GallerySelect(modifier = Modifier) {
            onGalleryAction(it, context)
            showDialog.value = false
        }
    }
    if (showCameraDialog.value) {
        CameraAction(modifier = Modifier)
        showCameraDialog.value = false
    }
    val painter = rememberImagePainter(
        data = image.toBitmap(context),
        builder = {
            placeholder(R.drawable.image_placeholder)
            crossfade(1500)
        })
    Box(
        modifier = Modifier
            .padding(bottom = 48.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(270.dp),
            painter = painter,
            contentDescription = "Car Image",
            contentScale = ContentScale.Crop,

            )
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
        ) {
            Row() {
                Button(
                    onClick = { showCameraDialog.value = true },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(44.dp)
                        .alpha(0.4f)
                        .padding(1.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(text = "Take Photo")
                }
                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .height(44.dp)
                        .alpha(0.4f)
                        .padding(1.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Row() {
                        Text(text = "Upload Photo")
                    }
                }
            }
        }
    }
}

@Composable
fun <I, O> registerForActivityResult(
    contract: ActivityResultContract<I, O>,
    onResult: (O) -> Unit
): ActivityResultLauncher<I> {
    val owner = LocalContext.current as ActivityResultRegistryOwner
    val activityResultRegistry = owner.activityResultRegistry
    val currentOnResult = rememberUpdatedState(onResult)
    val key = remember { UUID.randomUUID().toString() }
    val realLauncher = remember<ActivityResultLauncher<I>> {
        activityResultRegistry.register(key, contract) {
            currentOnResult.value(it)
        }
    }

    return realLauncher
}

@Composable
fun CameraAction(modifier: Modifier) {
    val context = LocalContext.current
    val launcher = registerForActivityResult(
        contract =
        ActivityResultContracts.TakePicturePreview()
    ) {

    }

    @Composable
    fun LaunchCamera() {
        SideEffect {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    (Manifest.permission.CAMERA)
                ) -> {
                    // Some works that require permission
                    Log.d("ExampleScreen", "Code requires permission")
                }
                else -> {
                    // Asking for permission
                    launcher.launch()
                }
            }

        }
    }
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "You want to read from photo gallery, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Photo Gallery!")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {
                            context.startActivity(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                            )
                        }
                    ) {
                        Text("Open Settings")
                    }
                    // If they don't want to grant permissions, this button will result in going back
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {

                        }
                    ) {
                        Text("Use Camera")
                    }
                }
            }
        },
    ) {
        LaunchCamera()
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GallerySelect(modifier: Modifier = Modifier, onImageUri: (Uri?) -> Unit = {}) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageUri(uri)
    }


    @Composable
    fun LaunchGallery() {
        SideEffect {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    (Manifest.permission.MEDIA_CONTENT_CONTROL)
                ) -> {
                    // Some works that require permission
                    Log.d("ExampleScreen", "Code requires permission")
                }
                else -> {
                    // Asking for permission
                    launcher.launch("image/*")
                }
            }

        }
    }

    Permission(
        permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
        rationale = "You want to read from photo gallery, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Photo Gallery!")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {

                            context.startActivity(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                            )
                        }
                    ) {
                        Text("Open Settings")
                    }
                    // If they don't want to grant permissions, this button will result in going back
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {
                            onImageUri(Uri.EMPTY)
                        }
                    ) {
                        Text("Use Camera")
                    }
                }
            }
        },
    ) {
        LaunchGallery()
    }
}


@Composable
fun SubmitButton(submitAction: () -> Unit) {
    Row() {
        Button(onClick = { submitAction() }) {
            Text(text = "Sumbit".uppercase())
        }
    }
}