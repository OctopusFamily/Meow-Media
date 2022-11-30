package com.octopus.socialnetwork.ui.screen.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.octopus.socialnetwork.R

@Composable
fun PostCommentsContent(state: CommentsUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = AbsoluteRoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )

    ) {

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(.8f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.comments) {
                ItemComment(commentDetails = it)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                backgroundColor = Color.LightGray,
                shape = RoundedCornerShape(57.dp),
                elevation = 0.dp
            ) {
                Row(
                    Modifier
                        .height(IntrinsicSize.Min)
                ) {

                    val textState = remember { mutableStateOf(TextFieldValue()) }
                    BasicTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        value = textState.value,
                        onValueChange = { textState.value = it },
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }

    }

}
