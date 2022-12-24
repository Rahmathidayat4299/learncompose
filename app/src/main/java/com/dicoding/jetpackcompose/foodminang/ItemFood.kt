package com.dicoding.jetpackcompose.foodminang

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dicoding.jetpackcompose.foodminang.ui.theme.FoodMinangTheme

/**
 * Created by Rahmat Hidayat on 26/11/2022.
 */
@Composable
fun CardFood(
    id:Long,
    name: String,
    photoUrl: String,
    onItemClicked: (id: Long) -> Unit,
    modifier: Modifier

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked.invoke(id)
            },
        shape = RoundedCornerShape(9.dp),
        elevation = 5.dp,
        backgroundColor = Color.DarkGray
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "aboutpage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(name, style = TextStyle(color = Color.White, fontSize = 16.sp))
        }
    }

}

@Composable
fun Loading(){
    LinearProgressIndicator(progress = 0.3f)
}
@Preview(showBackground = true)
@Composable
fun FoodListItemPreview() {
    FoodMinangTheme {
        CardFood(
            id =2,
            name = "dayat",
            photoUrl = "String",
            onItemClicked= {},
            modifier = Modifier
        )
    }
}