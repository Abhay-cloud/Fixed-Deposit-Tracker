package dev.abhaycloud.fdtracker.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.presentation.navigation.BottomNavDestinations
import dev.abhaycloud.fdtracker.presentation.navigation.BottomNavGraph
import dev.abhaycloud.fdtracker.presentation.navigation.FixedDepositNavigationScreens
import dev.abhaycloud.fdtracker.presentation.ui.add.AddFixedDepositScreen
import dev.abhaycloud.fdtracker.presentation.ui.calculator.CalculatorScreen
import dev.abhaycloud.fdtracker.presentation.ui.home.HomeScreen
import dev.abhaycloud.fdtracker.presentation.ui.settings.SettingsScreen
import dev.abhaycloud.fdtracker.utils.Utils.fromJson


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FixedDepositApp(navigationId: String?) {
    val navController = rememberNavController()
    var hideBottomBar by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        navigationId?.let {
            when(it) {
                FixedDepositNavigationScreens.AddFixedDeposit.route -> hideBottomBar = !hideBottomBar
            }
            navController.navigate(it)
        }
    }

    RequestNotificationPermission {

    }

//    SharedTransitionLayout {
    Scaffold(
        floatingActionButton = {
            if (!hideBottomBar) {
            FloatingActionButton(
                onClick = {
                    hideBottomBar = !hideBottomBar
                    navController.navigate(FixedDepositNavigationScreens.AddFixedDeposit.route)
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add fd")
            }
            }
        },
        bottomBar = {
            if (
                !hideBottomBar
//            , enter = slideInVertically() + expandVertically() + fadeIn(),
//                exit = slideOutVertically(targetOffsetY = {
//                    (it / 2)
//                }) + fadeOut()
            ) {
                BottomNavGraph(navController = navController)
            }
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestinations.HomeScreen.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {

            composable(BottomNavDestinations.HomeScreen.route) {
                HomeScreen(navController = navController, animatedVisibilityScope = this)
            }
            composable(BottomNavDestinations.CalculatorScreen.route) {
                CalculatorScreen()
            }
            composable(BottomNavDestinations.SettingsScreen.route) {
                SettingsScreen()
            }
            composable(FixedDepositNavigationScreens.AddFixedDeposit.route) {
                AddFixedDepositScreen(navController = navController, onSaved = {
                    hideBottomBar = !hideBottomBar
                }) {
                    hideBottomBar = !hideBottomBar
                }
            }
            composable("${FixedDepositNavigationScreens.ViewFixedDeposit.route}/{fixedDepositData}") {
                val fixedDeposit = it.arguments?.getString("fixedDepositData")

                AddFixedDepositScreen(
                    navController = navController,
                    fixedDeposit = fixedDeposit!!.fromJson<FixedDeposit>(),
                    onSaved = {
                        hideBottomBar = !hideBottomBar
                    }) {
                    hideBottomBar = !hideBottomBar
                }

                LaunchedEffect(key1 = Unit) {
                    hideBottomBar = !hideBottomBar
                }
//                ViewFixedDepositScreen(
//                    navController = navController,
//                    fixedDeposit = fixedDeposit!!.fromJson<FixedDeposit>(),
//                    animatedVisibilityScope = this
//                ) {
//                    hideBottomBar = !hideBottomBar
//                }
            }
        }
    }
}
//    }

@Composable
fun RequestNotificationPermission(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current

    var showRationale by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            showRationale = context.shouldShowRequestPermissionRationaleCompat(Manifest.permission.POST_NOTIFICATIONS)
            if (!showRationale) {
                showSettingsDialog = true
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                    onPermissionGranted()
                }
                context.shouldShowRequestPermissionRationaleCompat(Manifest.permission.POST_NOTIFICATIONS) -> {
                    showRationale = true
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            onPermissionGranted()
        }
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text(text = "Notification Permission Required") },
            text = { Text(text = "This app needs notification permission.") },
            confirmButton = {
                Button(onClick = {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    showRationale = false
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(onClick = { showRationale = false }) {
                    Text("Deny")
                }
            }
        )
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text(text = "Notification Permission Required") },
            text = { Text(text = "You have denied the notification permission. Please enable it in the app settings.") },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                    showSettingsDialog = false
                }) {
                    Text("Go to Settings")
                }
            },
            dismissButton = {
                Button(onClick = { showSettingsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

fun Context.shouldShowRequestPermissionRationaleCompat(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, permission)
}