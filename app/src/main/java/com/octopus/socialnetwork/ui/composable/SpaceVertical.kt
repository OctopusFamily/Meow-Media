package com.octopus.socialnetwork.ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.octopus.socialnetwork.ui.theme.*


@Composable
fun SpaceVertically4dp(){
    Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpaceVertically8dp(){
    Spacer(modifier = Modifier.height(spacingSmall))
}

@Composable
fun SpaceVertically10dp(){
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun SpaceVertically24dp(){
    Spacer(modifier = Modifier.height(spacingLarge))
}

@Composable
fun SpacerVertical16(){
    Spacer(modifier = Modifier.height(spacingMedium))
}

@Composable
fun SpacerVertical32() {
    Spacer(modifier = Modifier.height(spacingExtraLarge))
}
