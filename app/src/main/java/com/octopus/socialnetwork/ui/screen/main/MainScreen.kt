package com.octopus.socialnetwork.ui.screen.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.octopus.socialnetwork.ui.screen.register.RegisterScreen
import com.octopus.socialnetwork.ui.theme.SocialNetworkTheme


@Preview(showSystemUi = true)
@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun SocialNetworkApp() {
    SocialNetworkTheme {
        Scaffold {
            //  HomeScreen()
            RegisterScreen()
//            LoginScreen()
        }
    }

}


@Preview
@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun SocialNetworkAppPreview() {
    SocialNetworkTheme {
        Scaffold {
            RegisterScreen()
        }
    }
}