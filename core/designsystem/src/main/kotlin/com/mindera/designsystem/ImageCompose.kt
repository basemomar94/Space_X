package com.mindera.designsystem

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageCompose(imageUrl: String?, modifier: Modifier,contentDescription: String? = null) {
    GlideSubcomposition(
        model = imageUrl,
        modifier = modifier,
    ) {
        when (state) {
            is RequestState.Failure -> {}
            is RequestState.Loading -> CircularProgressIndicator()
            is RequestState.Success -> Image(
                painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
        }
    }
}