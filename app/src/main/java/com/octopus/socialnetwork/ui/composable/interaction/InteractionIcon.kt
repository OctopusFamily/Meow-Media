package com.octopus.socialnetwork.ui.composable.interaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.theme.LightBlack_65

@Composable
@Preview(showBackground = true, backgroundColor = 0xffff0000)
fun InteractionIcon(count: Int? = null, icon: Int = R.drawable.ic_like, ) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(56.dp))
            .background(color = LightBlack_65),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(icon),
                contentDescription = stringResource(R.string.like_icon),
                tint = Color.White
            )
            count?.let {
                Text(text = count.toString(), color = Color.White, fontSize = 12.sp)
            }


        }
    }

}