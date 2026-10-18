package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.common.BottomNavigationBar
import com.example.ui.common.MiniPlayerBar
import com.example.ui.common.TopHeaderBar
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduDXTheme {
                EduDXApp()
            }
        }
    }
}

@Composable
fun EduDXApp() {
    val currentRoute by EduDxRepository.currentScreen.collectAsState()
    val trackState by EduDxRepository.currentTrack.collectAsState()
    val toastMessage by EduDxRepository.toastMessage.collectAsState()

    // Handle Android system back button
    BackHandler(enabled = currentRoute != ScreenRoute.HOME) {
        when (currentRoute) {
            ScreenRoute.PLAYER -> EduDxRepository.navigateBack()
            ScreenRoute.SERIES_DETAIL -> EduDxRepository.navigateTo(ScreenRoute.HOME)
            ScreenRoute.ONBOARDING -> EduDxRepository.navigateTo(ScreenRoute.HOME)
            ScreenRoute.AUTH -> EduDxRepository.navigateTo(ScreenRoute.HOME)
            ScreenRoute.PDF_UPLOAD -> EduDxRepository.navigateBack()
            ScreenRoute.AUDIO_STUDIO -> EduDxRepository.navigateBack()
            ScreenRoute.AI_NOTE_SUMMARY -> EduDxRepository.navigateBack()
            ScreenRoute.STUDY_ROOM -> EduDxRepository.navigateBack()
            ScreenRoute.QUIZ_HUB -> EduDxRepository.navigateBack()
            ScreenRoute.ANALYTICS -> EduDxRepository.navigateBack()
            ScreenRoute.FLASHCARDS -> EduDxRepository.navigateBack()
            ScreenRoute.INBOX -> EduDxRepository.navigateBack()
            ScreenRoute.PREMIUM -> EduDxRepository.navigateBack()
            ScreenRoute.ACCOUNT_SETTINGS -> EduDxRepository.navigateTo(ScreenRoute.HOME)
            ScreenRoute.ADMIN_OVERVIEW -> EduDxRepository.navigateBack()
            ScreenRoute.ADMIN_CONTENT -> EduDxRepository.navigateBack()
            ScreenRoute.ADMIN_USERS -> EduDxRepository.navigateBack()
            ScreenRoute.FACULTY_ASSIGNMENTS -> EduDxRepository.navigateBack()
            ScreenRoute.SCHOOL_CLASSROOMS -> EduDxRepository.navigateBack()
            else -> EduDxRepository.navigateTo(ScreenRoute.HOME)
        }
    }

    // Auto-dismiss toast
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            delay(2400)
            EduDxRepository.clearToast()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        when (currentRoute) {
            ScreenRoute.ONBOARDING -> {
                OnboardingScreen(
                    onGetStarted = { EduDxRepository.navigateTo(ScreenRoute.AUTH) },
                    onSkip = { EduDxRepository.navigateTo(ScreenRoute.HOME) }
                )
            }

            ScreenRoute.AUTH -> {
                AuthPersonalizeScreen(
                    onContinue = { EduDxRepository.navigateTo(ScreenRoute.HOME) }
                )
            }

            ScreenRoute.PLAYER -> {
                PlayerScreen(
                    onBackClick = { EduDxRepository.navigateBack() }
                )
            }

            ScreenRoute.SERIES_DETAIL -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    SeriesDetailScreen(
                        onBackClick = { EduDxRepository.navigateTo(ScreenRoute.HOME) }
                    )
                    // Floating Mini-Player Bar
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.PDF_UPLOAD -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    PdfUploadScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.AUDIO_STUDIO -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AudioStudioScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.AI_NOTE_SUMMARY, ScreenRoute.FLASHCARDS -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AiNoteSummaryScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.STUDY_ROOM -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    StudyRoomScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.QUIZ_HUB, ScreenRoute.QUIZ_REVIEW -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    QuizHubScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.ANALYTICS -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AnalyticsScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.INBOX -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    InboxScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.PREMIUM -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    PremiumScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.ADMIN_OVERVIEW -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AdminOverviewScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.ADMIN_CONTENT -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AdminContentScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.ADMIN_USERS -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    AdminUsersScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.FACULTY_ASSIGNMENTS -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    SchoolAssignmentsScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            ScreenRoute.SCHOOL_CLASSROOMS -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    SchoolClassroomsScreen(
                        onBackClick = { EduDxRepository.navigateBack() }
                    )
                    MiniPlayerBar(
                        track = trackState,
                        onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                        onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 12.dp)
                    )
                }
            }

            // Main tabbed destinations (HOME, EXPLORE, AI_TUTOR, CATEGORIES, LIBRARY, ACCOUNT_SETTINGS)
            else -> {
                Scaffold(
                    topBar = {
                        TopHeaderBar(
                            onSearchClick = { EduDxRepository.navigateTo(ScreenRoute.EXPLORE) },
                            onProfileClick = { EduDxRepository.navigateTo(ScreenRoute.ACCOUNT_SETTINGS) }
                        )
                    },
                    bottomBar = {
                        Column {
                            // Persistent Mini-Player Bar above bottom nav
                            MiniPlayerBar(
                                track = trackState,
                                onPlayPauseClick = { EduDxRepository.togglePlayPause() },
                                onExpandClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                            )
                            BottomNavigationBar(
                                currentRoute = currentRoute,
                                onNavigate = { EduDxRepository.navigateTo(it) }
                            )
                        }
                    },
                    containerColor = DarkBackground,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentRoute) {
                            ScreenRoute.HOME -> HomeScreen()
                            ScreenRoute.EXPLORE -> ExploreScreen()
                            ScreenRoute.AI_TUTOR -> AiTutorScreen()
                            ScreenRoute.CATEGORIES -> CategoriesScreen()
                            ScreenRoute.LIBRARY -> LibraryScreen()
                            ScreenRoute.ACCOUNT_SETTINGS -> AccountSettingsScreen(
                                onBackClick = { EduDxRepository.navigateTo(ScreenRoute.HOME) }
                            )
                            else -> HomeScreen()
                        }
                    }
                }
            }
        }

        // Floating Toast Notification
        AnimatedVisibility(
            visible = toastMessage != null,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 60 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { 60 }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = DarkSurfaceContainerHighest,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .testTag("app_toast_bar")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = OrangePrimaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = toastMessage ?: "",
                        color = DarkOnSurface,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
