package com.example.model

enum class ScreenRoute {
    ONBOARDING,
    AUTH,
    HOME,
    EXPLORE,
    AI_TUTOR,
    CATEGORIES,
    LIBRARY,
    SERIES_DETAIL,
    PLAYER,
    STUDY_ROOM,
    PDF_UPLOAD,
    AI_NOTE_SUMMARY,
    AUDIO_STUDIO,
    FLASHCARDS,
    QUIZ_HUB,
    QUIZ_REVIEW,
    ANALYTICS,
    INBOX,
    PREMIUM,
    ACCOUNT_SETTINGS,
    ADMIN_OVERVIEW,
    ADMIN_CONTENT,
    ADMIN_USERS,
    FACULTY_ASSIGNMENTS,
    SCHOOL_CLASSROOMS
}

enum class EpisodeStatus {
    COMPLETED,
    PLAYING,
    UP_NEXT,
    LOCKED,
    READY
}

data class AudioTrackState(
    val id: String = "faraday_law",
    val title: String = "Faraday's Law of Induction & Lenz's Rule",
    val subjectSubtitle: String = "Class 12 Physics: Electromagnetism",
    val instructor: String = "Prof. Rajesh Sharma",
    val affiliation: String = "IIT Madras",
    val currentSeconds: Int = 868, // 14:28
    val totalSeconds: Int = 1600,  // 26:40
    val isPlaying: Boolean = true,
    val speed: String = "1.25x",
    val quality: String = "128k",
    val sleepTimerLabel: String = "Off",
    val isBookmarked: Boolean = false,
    val currentChapterName: String = "Ch 3: Lenz's Opposing EMF",
    val coverUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuBJ1cWnO8zS3-6YpDhtKDJTfIyjnESFJn38oGg2b8Mu2rb5iIf1FMLstbYgTzOSaNWRk8FrgDOSWvMKE2TdRk-MWFKg60Q8oVei4qFj9fhyG_U5AUh-llUdUtB-q0OZDBSnQiKexvXJ0DyOo3z9QQHN4d7_NTFMTjWM168DD0PTVzKSoTlmlZZm9FV5YEVRHKvL-zmQqnEUSkPXpdaehvp3F274yHffTyB8BqWJG4lzB36LhYhGadrn_w",
    val unitTag: String = "NCERT UNIT IV"
)

data class SeriesItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val instructor: String,
    val rating: Float,
    val reviewsCount: String,
    val totalDuration: String,
    val syllabusYear: String,
    val coverUrl: String,
    val episodesCount: Int,
    val progressPercent: Int,
    val completedEpisodes: Int,
    val badgeText: String,
    val category: String,
    val playsCount: String = ""
)

data class EpisodeItem(
    val id: String,
    val epNumber: Int,
    val title: String,
    val description: String,
    val duration: String,
    val status: EpisodeStatus,
    val progressPercent: Int = 0,
    val isDownloaded: Boolean = false
)

data class VoiceMentor(
    val id: String,
    val name: String,
    val subject: String,
    val plays: String,
    val avatarUrl: String,
    val verified: Boolean = true
)

data class SubjectItem(
    val id: String,
    val name: String,
    val chaptersCount: Int,
    val episodesCount: Int,
    val durationHours: Int,
    val icon: String,
    val activeListening: Boolean = false
)

data class ChapterAccordion(
    val id: String,
    val number: String,
    val title: String,
    val modulesCount: Int,
    val duration: String,
    val description: String,
    val isExpanded: Boolean = false,
    val subTracks: List<SubTrackItem> = emptyList()
)

data class SubTrackItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val duration: String,
    val isLocked: Boolean
)

data class ExamCard(
    val id: String,
    val title: String,
    val tag: String,
    val description: String,
    val syllabusCoverage: Int,
    val audioStemsCount: Int,
    val coverUrl: String
)

data class BookmarkNote(
    val id: String,
    val timestamp: String,
    val episodeMeta: String,
    val title: String,
    val noteQuote: String,
    val savedTimeAgo: String
)

data class ListeningHistoryItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val durationCompleted: String,
    val timeGroup: String, // "Today", "Yesterday", "Last Week"
    val groupListeningTime: String = ""
)

data class DownloadedCourse(
    val id: String,
    val subjectTag: String,
    val title: String,
    val episodesText: String,
    val sizeMb: Int,
    val coverUrl: String
)

data class FlashcardItem(
    val id: String,
    val subject: String,
    val difficulty: String,
    val question: String,
    val clue: String,
    val solutionFormula: String,
    val solutionDerivation: String,
    val bulletPoints: List<String>
)
