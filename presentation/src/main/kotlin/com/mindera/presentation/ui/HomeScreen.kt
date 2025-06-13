package com.mindera.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mindera.base.utils.Logger
import com.mindera.base.utils.getErrorMessage
import com.mindera.designsystem.BaseSnackBar
import com.mindera.designsystem.CircularProgress
import com.mindera.presentation.HomeEffect
import com.mindera.presentation.HomeIntent
import com.mindera.presentation.HomeState
import com.mindera.presentation.HomeViewModel
import com.mindera.presentation.R
import com.mindera.presentation.ui.composes.HomeCompose
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.viewState.collectAsState().value
    val logger = Logger("-- HomeScreen")
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect("Unit") {
        viewModel.effect.onEach { effect ->
            logger.d("effect is $effect")
            when (effect) {
                is HomeEffect.Error -> {
                    coroutineScope.launch {
                        val errorText = context.getErrorMessage(effect.errorTypes)
                        snackBarHostState.showSnackbar(errorText)
                    }
                }
            }
        }.collectLatest { }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            HomeState.Loading -> CircularProgress()
            is HomeState.Success -> {
                HomeCompose(
                    company = state.companyUi,
                    launches = state.launch
                )

            }
        }
        BaseSnackBar(
            snackBarState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            actionText = stringResource(R.string.try_again)
        ) {
            viewModel.setEvent(HomeIntent.LoadData)
        }

    }


}