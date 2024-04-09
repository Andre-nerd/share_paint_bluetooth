package ru.share_paint.zoomparty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.share_paint.zoomparty.domain.model.WrapperDataContainer
import ru.share_paint.zoomparty.presentation.ServiceViewModel
import ru.share_paint.zoomparty.presentation.help.HelpScreen
import ru.share_paint.zoomparty.presentation.sreens.GameScreen
import ru.share_paint.zoomparty.presentation.sreens.SelectMasterOrSlave

@Composable
fun NavigateRoute(
    navController: NavHostController,
    serviceViewModel: ServiceViewModel = viewModel(),
    dataContainer: WrapperDataContainer
) {
    NavHost(
        navController = navController,
        startDestination = Route.Game.name
    ) {
        composable(route = Route.Setting.name) {
            SelectMasterOrSlave(serviceViewModel = serviceViewModel, navController = navController)
        }
        composable(route = Route.Game.name) {
            GameScreen(serviceViewModel = serviceViewModel, navController = navController, dataContainer = dataContainer)
        }
        composable(route = Route.Help.name) {
            HelpScreen(navController = navController)
        }
    }

}