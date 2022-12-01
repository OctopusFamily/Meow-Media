package com.octopus.socialnetwork.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.composable.*
import com.octopus.socialnetwork.ui.theme.PoppinsTypography
import com.octopus.socialnetwork.ui.theme.Red600
import com.octopus.socialnetwork.ui.theme.White50


@Preview(showSystemUi = true)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    ProfileContent(
        state = state,
        follow = viewModel::onClickFollow,
        message = viewModel::onClickMessage
    )
}

@Composable
fun ProfileContent(
    state: ProfileUiState,
    follow:() -> Unit,
    message: () -> Unit
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ) {

        ProfileImages(
            backImageProfile = rememberAsyncImagePainter(model = state.profileCover),
            profileImage = rememberAsyncImagePainter(model = state.profileAvatar)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(136.dp)
        ) {
            Text(
                text = state.fullName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsTypography.subtitle1.fontFamily,
                fontStyle = PoppinsTypography.subtitle1.fontStyle,
                fontSize = PoppinsTypography.subtitle1.fontSize
            )
            Text(
                text = state.username,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Light,
                fontFamily = PoppinsTypography.caption.fontFamily,
                fontStyle = PoppinsTypography.caption.fontStyle,
                fontSize = PoppinsTypography.caption.fontSize

            )

            SpaceVertically10dp()
            Row(modifier = Modifier.align(Alignment.CenterHorizontally))
            {

                Text(
                    text = state.friendsCount,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsTypography.caption.fontFamily,
                    fontStyle = PoppinsTypography.caption.fontStyle,
                    fontSize = PoppinsTypography.caption.fontSize
                )
                SpaceHorizontally4dp()
                Text(
                    text = stringResource(R.string.friends),
                    fontWeight = FontWeight.W400,
                    fontFamily = PoppinsTypography.caption.fontFamily,
                    fontStyle = PoppinsTypography.caption.fontStyle,
                    fontSize = PoppinsTypography.caption.fontSize
                )
                SpaceHorizontally16dp()
                Text(
                    text = state.postCount,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsTypography.caption.fontFamily,
                    fontStyle = PoppinsTypography.caption.fontStyle,
                    fontSize = PoppinsTypography.caption.fontSize
                )
                SpaceHorizontally4dp()
                Text(
                    text = stringResource(R.string.posts),
                    fontWeight = FontWeight.W400,
                    fontFamily = PoppinsTypography.caption.fontFamily,
                    fontStyle = PoppinsTypography.caption.fontStyle,
                    fontSize = PoppinsTypography.caption.fontSize
                )


            }
            SpaceVertically8dp()
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                Button(
                    onClick = follow,
                    modifier = Modifier
                        .height(25.dp)
                        .width(87.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red600)
                ) {
                    Image(
                        painterResource(id = R.drawable.icon_person_add),
                        contentDescription = "add icon",
                        modifier = Modifier.size(14.dp)
                    )

                    Text(
                        text = stringResource(R.string.follow_icon_name),
                        Modifier.padding(start = 5.dp),
                        fontWeight = FontWeight.W400,
                        fontFamily = PoppinsTypography.overline.fontFamily,
                        fontStyle = PoppinsTypography.overline.fontStyle,
                        fontSize = PoppinsTypography.overline.fontSize,
                        color = White50,
                    )
                }
                SpaceHorizontally8dp()
                Button(
                    onClick = message,
                    modifier = Modifier
                        .height(25.dp)
                        .width(29.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red600),
                    contentPadding = PaddingValues(0.dp),


                    ) {
                    Image(
                        painterResource(id = R.drawable.massage),
                        contentDescription = stringResource(R.string.add_icon_name)
                    )
                }
            }

        }
        Divider(
            color = Color(color = 0xFFBCBCBC), modifier = Modifier
                .fillMaxWidth()
                .height(1.dp).alpha(1f)
        )


    }


}


