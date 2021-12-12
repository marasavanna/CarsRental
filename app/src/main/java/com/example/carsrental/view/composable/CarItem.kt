package com.example.carsrental.view.composable

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.carsrental.R
import com.example.carsrental.model.Car
import com.example.carsrental.utils.toBitmap
import com.example.carsrental.viewmodel.CarsListViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CarItemsList(viewModel: CarsListViewModel) {
    val cars by viewModel.cars.observeAsState(mutableListOf())
    LazyColumn {
        itemsIndexed(items = cars, key = { _, item ->
            item.id.toString()
        }) { _, item ->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        viewModel.onCarSwipedToDelete(item)
                    } else if (it == DismissValue.DismissedToEnd) {
                        viewModel.onEditCar(item)
                    }
                    false
                }
            )
            SwipeToDismiss(state = state, background = {
                var color: Color = Color.Transparent
                var horizontalAlignment: Alignment.Horizontal = Alignment.End
                var imageVector: ImageVector = Icons.Default.Delete
                var actionText = ""
                var modifier = Modifier
                    .size(56.dp)
                    .padding(end = 30.dp)
                when (state.dismissDirection) {
                    DismissDirection.EndToStart -> {
                        color = Color.Red
                        horizontalAlignment = Alignment.End
                        imageVector = Icons.Default.Delete
                        actionText = "Delete"
                    }
                    DismissDirection.StartToEnd -> {
                        color = Color.Magenta
                        horizontalAlignment = Alignment.Start
                        imageVector = Icons.Default.Edit
                        actionText = "Edit"
                        modifier = Modifier
                            .size(56.dp)
                            .padding(start = 20.dp)
                    }
                    else -> {
                        color = Color.White
                    }
                }

                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(260.dp)
                        .align(Alignment.CenterVertically)
                        .background(color = color)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = color
                ) {
                    SwipeContent(
                        horizontalAlignment = horizontalAlignment,
                        imageVector = imageVector,
                        actionText = actionText,
                        modifier
                    )
                }
            }, dismissContent = {
                CarItem(car = item)
            })
        }
    }
}

@Composable
fun SwipeContent(
    horizontalAlignment: Alignment.Horizontal,
    imageVector: ImageVector,
    actionText: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Icon(
                modifier = modifier,
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.White
            )
        }
        Row {
            Text(
                text = actionText,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CarItem(car: Car) {
    Box(
        modifier = Modifier
            .height(280.dp)
            .padding(8.dp)
            .clip( shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .fillMaxWidth()
    ) {

        val context = LocalContext.current
        CarImage(car.image.toBitmap(context))
        val rectangleModifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .alpha(0.7f)
            .background(Color.White)
        CarDataRectangle(rectangleModifier, car)
    }
}

@Composable
fun CarImage(image: Bitmap) {
    val painter = rememberImagePainter(
        data = image,
        builder = {
            placeholder(R.drawable.image_placeholder)
            crossfade(1500)
        })
    Image(
        painter = painter, contentDescription = "Car Image",   contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CarDataRectangle(modifier: Modifier, car: Car) {
    Box(
        modifier = modifier
    ) {
        val modifierLabel = Modifier
            .align(
                Alignment.TopStart
            )
            .padding(8.dp, 2.dp)
        val modifierValue = Modifier
            .align(
                Alignment.TopStart
            )
            .padding(0.dp, 2.dp)
        Row {
            Column {
                CarDataLabel(
                    modifier = modifierLabel,
                    modifierValue = modifierValue,
                    label = "Brand",
                    value = car.brand ?: ""
                )
                CarDataLabel(
                    modifier = modifierLabel,
                    modifierValue = modifierValue,
                    label = "Id",
                    value = car.id?.toString() ?: ""
                )
                CarDataLabel(
                    modifier = modifierLabel,
                    modifierValue = modifierValue,
                    label = "Fabrication Year",
                    value = car.fabricationYear?.toString() ?: ""
                )
            }

            Column(modifier = Modifier.padding(32.dp, 0.dp)) {
                CarDataLabel(
                    modifier = modifierLabel,
                    modifierValue = modifierValue,
                    label = "Model",
                    value = car.model ?: ""
                )
                CarDataLabel(
                    modifier = modifierLabel,
                    modifierValue = modifierValue,
                    label = "Color",
                    value = car.color ?: ""
                )
                CarDataLabel(
                    modifier = modifierLabel,
                    modifierValue = modifierValue,
                    label = "Automatic",
                    value = if (car.isAutomatic == true) "Yes" else "No"
                )
            }
        }
    }
}

@Composable
fun CarDataLabel(
    modifier: Modifier,
    modifierValue: Modifier,
    label: String,
    value: String
) {
    Row {
        Text(
            text = "$label :",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier
        )
        Text(
            text = value,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = modifierValue
        )
    }
}
