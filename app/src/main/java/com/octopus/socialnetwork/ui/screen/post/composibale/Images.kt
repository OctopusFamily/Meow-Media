package com.octopus.socialnetwork.ui.screen.post.composibale

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.octopus.socialnetwork.R

@Composable
fun BackgroundImage() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.login_background),
        contentScale = ContentScale.FillBounds,
        contentDescription = stringResource(R.string.description)
    )
}