package com.example

import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testInitialTrackState() {
        val track = EduDxRepository.currentTrack.value
        assertEquals("Faraday's Law of Induction & Lenz's Rule", track.title)
        assertTrue(track.totalSeconds > 0)
        assertTrue(track.currentSeconds in 0..track.totalSeconds)
    }

    @Test
    fun testPlayPauseToggle() {
        val initialPlaying = EduDxRepository.currentTrack.value.isPlaying
        EduDxRepository.togglePlayPause()
        assertEquals(!initialPlaying, EduDxRepository.currentTrack.value.isPlaying)
        EduDxRepository.togglePlayPause()
        assertEquals(initialPlaying, EduDxRepository.currentTrack.value.isPlaying)
    }

    @Test
    fun testSeekRelative() {
        EduDxRepository.seekTo(100)
        assertEquals(100, EduDxRepository.currentTrack.value.currentSeconds)
        EduDxRepository.seekRelative(15)
        assertEquals(115, EduDxRepository.currentTrack.value.currentSeconds)
        EduDxRepository.seekRelative(-30)
        assertEquals(85, EduDxRepository.currentTrack.value.currentSeconds)
    }

    @Test
    fun testSpeedCycle() {
        val initialSpeed = EduDxRepository.currentTrack.value.speed
        EduDxRepository.cycleSpeed()
        val nextSpeed = EduDxRepository.currentTrack.value.speed
        assertNotEquals(initialSpeed, nextSpeed)
    }

    @Test
    fun testSleepTimer() {
        EduDxRepository.setSleepTimer("15m")
        assertEquals("15m", EduDxRepository.currentTrack.value.sleepTimerLabel)
        EduDxRepository.setSleepTimer("Off")
        assertEquals("Off", EduDxRepository.currentTrack.value.sleepTimerLabel)
    }

    @Test
    fun testStreakClaim() {
        EduDxRepository.claimStreak()
        assertTrue(EduDxRepository.streakClaimed.value)
    }

    @Test
    fun testNavigation() {
        EduDxRepository.navigateTo(ScreenRoute.EXPLORE)
        assertEquals(ScreenRoute.EXPLORE, EduDxRepository.currentScreen.value)
        EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR)
        assertEquals(ScreenRoute.AI_TUTOR, EduDxRepository.currentScreen.value)
        EduDxRepository.navigateTo(ScreenRoute.CATEGORIES)
        assertEquals(ScreenRoute.CATEGORIES, EduDxRepository.currentScreen.value)
        EduDxRepository.navigateTo(ScreenRoute.LIBRARY)
        assertEquals(ScreenRoute.LIBRARY, EduDxRepository.currentScreen.value)
        EduDxRepository.navigateTo(ScreenRoute.HOME)
        assertEquals(ScreenRoute.HOME, EduDxRepository.currentScreen.value)
    }

    @Test
    fun testDataRepositoryLists() {
        assertTrue(EduDxRepository.voiceMentors.isNotEmpty())
        assertTrue(EduDxRepository.subjects.isNotEmpty())
        assertTrue(EduDxRepository.chapters.isNotEmpty())
        assertTrue(EduDxRepository.examCards.isNotEmpty())
        assertTrue(EduDxRepository.seriesEpisodes.isNotEmpty())
        assertTrue(EduDxRepository.downloadedCourses.isNotEmpty())
        assertTrue(EduDxRepository.activeSavedSeries.isNotEmpty())
        assertTrue(EduDxRepository.bookmarkedNotes.isNotEmpty())
        assertTrue(EduDxRepository.listeningHistory.isNotEmpty())
    }
}
