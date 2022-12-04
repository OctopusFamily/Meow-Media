package com.octopus.socialnetwork.ui.screen.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.octopus.socialnetwork.ui.screen.edit_profile.EditProfileScreen
import com.octopus.socialnetwork.ui.theme.SocialNetworkTheme

@ExperimentalPagerApi
@Preview(showSystemUi = true)
@Composable
fun SocialNetworkApp() {
    SocialNetworkTheme {
        Scaffold {
//            ProfileScreen()
           EditProfileScreen()
            // RegisterScreen()
//            LoginScreen()
        }
    }

}