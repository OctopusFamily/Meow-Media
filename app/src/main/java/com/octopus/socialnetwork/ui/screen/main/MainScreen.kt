package com.octopus.socialnetwork.ui.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.SocialNetworkNavGraph
import com.octopus.socialnetwork.ui.composable.navigation.BottomNavItem
import com.octopus.socialnetwork.ui.composable.navigation.BottomNavigation
import com.octopus.socialnetwork.ui.composable.navigation.FloatingActionButton
import com.octopus.socialnetwork.ui.theme.SocialNetworkTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun SocialNetworkApp() {
    SocialNetworkTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                BottomNavigation(
                    items = listOf(
                        BottomNavItem(
                            name = "Home",
                            route = "home",
                            icon = painterResource(id = R.drawable.home),
                        ),
                        BottomNavItem(
                            name = "Search",
                            route = "on_boarding",
                            icon = painterResource(id = R.drawable.search),
                        ),
                        BottomNavItem(
                            name = "Chat",
                            route = "login",
                            icon = painterResource(id = R.drawable.chat),
                        ),
                        BottomNavItem(
                            name = "Profile",
                            route = "profile",
                            icon = painterResource(id = R.drawable.profile),
                        ),
                    ),
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route)
                    })

            },
            floatingActionButton = {
                FloatingActionButton {}
            }
        ) {

            SocialNetworkNavGraph(navController = navController)

        }

    }

}


@Preview
@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun SocialNetworkAppPreview() {
    SocialNetworkTheme {

        SocialNetworkApp()

    }
}