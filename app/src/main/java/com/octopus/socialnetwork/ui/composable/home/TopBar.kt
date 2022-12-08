package com.octopus.socialnetwork.ui.composable.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.Icons
import com.octopus.socialnetwork.ui.composable.shadowLightBlack
import com.octopus.socialnetwork.ui.theme.Gray900_2


@Composable
fun TopBar(
    onClickNotification: () -> Unit,
) {
fun TopBar(
    userId: Int,
    onClickNotifications: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadowLightBlack()
            .background(color = Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        AppName(text = stringResource(R.string.octopusyan))
        Icons(
            imageVector = Icons.Default.Notifications,
            tint = Gray900_2,
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickNotification() },
            modifier = Modifier.size(24.dp).clickable { onClickNotifications(userId) },
        )

    }
}