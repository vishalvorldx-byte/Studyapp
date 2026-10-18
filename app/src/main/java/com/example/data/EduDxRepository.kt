package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object EduDxRepository {

    // Navigation & Screen Stack
    private val _currentScreen = MutableStateFlow(ScreenRoute.HOME)
    val currentScreen: StateFlow<ScreenRoute> = _currentScreen.asStateFlow()

    private val _previousScreen = MutableStateFlow<ScreenRoute?>(null)

    // Audio Playback State
    private val _currentTrack = MutableStateFlow(AudioTrackState())
    val currentTrack: StateFlow<AudioTrackState> = _currentTrack.asStateFlow()

    // Streak State
    private val _streakClaimed = MutableStateFlow(false)
    val streakClaimed: StateFlow<Boolean> = _streakClaimed.asStateFlow()

    // User Language Preference
    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    // Auth & Personalization Settings
    private val _isDemoMode = MutableStateFlow(false)
    val isDemoMode: StateFlow<Boolean> = _isDemoMode.asStateFlow()

    private val _selectedGrade = MutableStateFlow("Class 12")
    val selectedGrade: StateFlow<String> = _selectedGrade.asStateFlow()

    private val _selectedBoard = MutableStateFlow("CBSE")
    val selectedBoard: StateFlow<String> = _selectedBoard.asStateFlow()

    private val _selectedExams = MutableStateFlow(setOf("JEE Main/Adv", "Boards Prep"))
    val selectedExams: StateFlow<Set<String>> = _selectedExams.asStateFlow()

    private val _dailyPaceMinutes = MutableStateFlow(30)
    val dailyPaceMinutes: StateFlow<Int> = _dailyPaceMinutes.asStateFlow()

    // Library Tab Filter
    private val _libraryFilterTab = MutableStateFlow("series") // series, offline, bookmarks, history
    val libraryFilterTab: StateFlow<String> = _libraryFilterTab.asStateFlow()

    // AI Tutor State
    private val _aiTutorPersona = MutableStateFlow("aisha") // aisha or vikram
    val aiTutorPersona: StateFlow<String> = _aiTutorPersona.asStateFlow()

    private val _aiTutorMode = MutableStateFlow("voice") // voice or text
    val aiTutorMode: StateFlow<String> = _aiTutorMode.asStateFlow()

    private val _isMicListening = MutableStateFlow(false)
    val isMicListening: StateFlow<Boolean> = _isMicListening.asStateFlow()

    private val _isAiExplanationPlaying = MutableStateFlow(false)
    val isAiExplanationPlaying: StateFlow<Boolean> = _isAiExplanationPlaying.asStateFlow()

    // Flashcard State
    val flashcards = listOf(
        FlashcardItem(
            id = "fc_1",
            subject = "#Physics • High-Yield",
            difficulty = "Medium",
            question = "How does the induced current in an AC generator vary as the coil rotates from θ = 0° to θ = 180° in a uniform magnetic field?",
            clue = "Think about sin(ωt) sinusoidal wave behavior. At θ = 0°, magnetic flux linkage is at its maximum, but rate of change is zero!",
            solutionFormula = "Ε(t) = Ε₀ ⋅ sin(ωt)",
            solutionDerivation = "where peak induced EMF is Ε₀ = N ⋅ B ⋅ A ⋅ ω",
            bulletPoints = listOf(
                "• At θ = 0° → I = 0",
                "• At θ = 90° → I reaches positive maximum (I₀)",
                "• At θ = 180° → I returns to 0 and reverses polarity."
            )
        )
    )

    private val _aiPlaybackSpeed = MutableStateFlow("1.0x")
    val aiPlaybackSpeed: StateFlow<String> = _aiPlaybackSpeed.asStateFlow()

    private val _aiInputText = MutableStateFlow("")
    val aiInputText: StateFlow<String> = _aiInputText.asStateFlow()

    // Explore / Search filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTimeFilter = MutableStateFlow("All Durations")
    val selectedTimeFilter: StateFlow<String> = _selectedTimeFilter.asStateFlow()

    private val _selectedTypeFilter = MutableStateFlow("Concept Bite")
    val selectedTypeFilter: StateFlow<String> = _selectedTypeFilter.asStateFlow()

    private val _selectedCategoryPill = MutableStateFlow("All")
    val selectedCategoryPill: StateFlow<String> = _selectedCategoryPill.asStateFlow()

    // Expanded chapters state for Categories screen
    private val _expandedChapterId = MutableStateFlow<String?>("ch_1")
    val expandedChapterId: StateFlow<String?> = _expandedChapterId.asStateFlow()

    // Toast message for user actions
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Navigation Methods
    fun navigateTo(route: ScreenRoute) {
        if (_currentScreen.value != route) {
            _previousScreen.value = _currentScreen.value
            _currentScreen.value = route
        }
    }

    fun navigateBack() {
        val prev = _previousScreen.value
        if (prev != null && prev != _currentScreen.value) {
            _currentScreen.value = prev
            _previousScreen.value = ScreenRoute.HOME
        } else {
            _currentScreen.value = ScreenRoute.HOME
        }
    }

    // Playback Controls
    fun togglePlayPause() {
        _currentTrack.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun seekRelative(seconds: Int) {
        _currentTrack.update { track ->
            val next = (track.currentSeconds + seconds).coerceIn(0, track.totalSeconds)
            track.copy(currentSeconds = next)
        }
    }

    fun seekTo(seconds: Int) {
        _currentTrack.update { track ->
            val next = seconds.coerceIn(0, track.totalSeconds)
            track.copy(currentSeconds = next)
        }
    }

    fun cycleSpeed() {
        val speeds = listOf("1.0x", "1.25x", "1.5x", "2.0x")
        val current = _currentTrack.value.speed
        val nextIdx = (speeds.indexOf(current) + 1) % speeds.size
        val next = speeds[nextIdx]
        _currentTrack.update { it.copy(speed = next) }
        showToast("Playback speed set to $next")
    }

    fun toggleAudioQuality() {
        _currentTrack.update {
            val next = if (it.quality == "128k") "48k" else "128k"
            it.copy(quality = next)
        }
        val q = _currentTrack.value.quality
        showToast(if (q == "128k") "Audio Quality: High (128kbps)" else "Audio Quality: Data Saver (48kbps)")
    }

    fun toggleBookmarkTrack() {
        _currentTrack.update {
            val next = !it.isBookmarked
            it.copy(isBookmarked = next)
        }
        val b = _currentTrack.value.isBookmarked
        showToast(if (b) "Episode saved to offline Library" else "Removed from saved library")
    }

    fun setSleepTimer(label: String) {
        _currentTrack.update { it.copy(sleepTimerLabel = label) }
        showToast(if (label == "Off") "Sleep timer turned off" else "Sleep timer active: $label")
    }

    fun claimStreak() {
        _streakClaimed.value = true
        showToast("Claimed +50 XP! Streak maintained.")
    }

    fun setLanguage(lang: String) {
        _selectedLanguage.value = lang
    }

    fun setDemoMode(active: Boolean) {
        _isDemoMode.value = active
    }

    fun setGrade(grade: String) {
        _selectedGrade.value = grade
    }

    fun setBoard(board: String) {
        _selectedBoard.value = board
    }

    fun toggleExam(exam: String) {
        val current = _selectedExams.value.toMutableSet()
        if (current.contains(exam)) current.remove(exam) else current.add(exam)
        _selectedExams.value = current
    }

    fun setDailyPace(minutes: Int) {
        _dailyPaceMinutes.value = minutes
    }

    fun setLibraryFilterTab(tab: String) {
        _libraryFilterTab.value = tab
    }

    fun setAiPersona(persona: String) {
        _aiTutorPersona.value = persona
    }

    fun setAiMode(mode: String) {
        _aiTutorMode.value = mode
    }

    fun toggleMicListening() {
        _isMicListening.update { !it }
    }

    fun toggleAiExplanationAudio() {
        _isAiExplanationPlaying.update { !it }
    }

    fun cycleAiPlaybackSpeed() {
        val speeds = listOf("1.0x", "1.2x", "0.8x")
        val current = _aiPlaybackSpeed.value
        val nextIdx = (speeds.indexOf(current) + 1) % speeds.size
        _aiPlaybackSpeed.value = speeds[nextIdx]
    }

    fun setAiInputText(text: String) {
        _aiInputText.value = text
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setTimeFilter(filter: String) {
        _selectedTimeFilter.value = filter
    }

    fun setTypeFilter(filter: String) {
        _selectedTypeFilter.value = filter
    }

    fun setCategoryPill(category: String) {
        _selectedCategoryPill.value = category
    }

    fun toggleChapterExpanded(id: String) {
        _expandedChapterId.update { if (it == id) null else id }
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    // Static Data Sets matching screenshots & HTML
    val voiceMentors = listOf(
        VoiceMentor("dr_rao", "Dr. Rao", "Physics", "142k plays", "https://lh3.googleusercontent.com/aida-public/AB6AXuDUlkpQQ5-YFBqzPguG1DiPTF1Y9Q90E6Wj-4lzXYUXp-vqq3vDMCMGGnG9CehmXlpmiVKSHgyDbxPg5FnbnF1HRPbPlynV8teCzw2AY7PbkFEKeRRjqgZABxiGjdpG4fB7k0tqYDJnThZjJTx-yxa3CeOkbBWMUIaQkqKiCYDdy9DwBh85u70_xeJz3NnEEALEncYXpZLFQdn3-866W_k8UUKFajBr-f3vuc2BNJhlmYKZkpV23IXwKw"),
        VoiceMentor("vikram_roy", "Vikram Roy", "Polity & GK", "98k plays", "https://lh3.googleusercontent.com/aida-public/AB6AXuBKrRagtXw2wofSpeYi24AdCMzx6-qYucuyoWc68T1ddk0qI_eEUPhZlp9S4M5Be1iex9YKUTYXnNZ2O-wd0uJ-X2f9z86EK3IhwE2zxxzEfkif5-u4QdABlhHGxTB5Sg3fg8AdoBHoDDpxhvvtoBJgBUW3-_nPjk5tnBnwtaqK3vBAnFrARatlfBjpMxjcLGayB3QZvh3gM-JIMGMnvTp-HrH4qmDIl7_X9uGInKltZr-cTt8sifbEMw"),
        VoiceMentor("aanya_sen", "Aanya Sen", "Organic Chem", "210k plays", "https://lh3.googleusercontent.com/aida-public/AB6AXuAC20UR7O1J1ZtKsRKJY0ZyIt-okbfJ1VFU3UkqLi2bNg1w-iKpbwiESfD_rloJdKQC7TMl2HRF2jGuZ9vbg0ToYFtB49DkD5YGr6s_XNL9EIGyJdrjioIvIlHqu-weiNUMLg_Cp310gNfGh2VR74lfbFd9sypPiEri5uhiteRVhwiM3HekIB57XOqDrCyHwT5Hv497DqlRneUd3lrMriAYwg3-fP-6khq9c3iEUVETRad46BnsGGSFBQ"),
        VoiceMentor("prof_joshi", "Prof. Joshi", "Calculus", "85k plays", "https://lh3.googleusercontent.com/aida-public/AB6AXuCum7Oqjx2AqtA_BefzRyp5SD8TDsniKfe28r1vdLNBkm4KoVyeJ7twheDFn7oLl3Gq60iDs2J76MAfRlTh5MI-Hji44meE5tQLP2P2TbdNHeoZe70XqZvFByP3WbIxiSCF2KKch2hu_840qnP54oVGnMUpQhQND1WUks_OB3z_S-34QdiGYgAjXMpLqX9J6uheetZyuVtoxygXv1uoq9CxgttH-p5YViB-Pr9yAdwjQ5KoFGAZ9NptWg"),
        VoiceMentor("meera_nair", "Meera Nair", "Biology", "170k plays", "https://lh3.googleusercontent.com/aida-public/AB6AXuAk6CGF009gOK1hrfoeKws7hFE_0t-ylpDLhHcH510fu9MtlRbzJFJkERQGAx2bd-jNWo-UwahrT1PqE1Fv0NSSd05IqybVbgYgkc-hlR9DcueoTBAcch_n4ETg-U6hhxxoBdWTQ6rGjOBFxOGGRh4t3TJtREpoFpmlL7B3UosGiC-3ihveDtWJgRnyUsXN0csE4oLmtw8ybnAmxmF0kVhf2T2ohF8oVyd8T5mLSvJTjW0GHAi44vE1mQ")
    )

    val subjects = listOf(
        SubjectItem("physics", "Physics", 14, 120, 48, "electric_bolt", activeListening = true),
        SubjectItem("chemistry", "Chemistry", 16, 145, 48, "science"),
        SubjectItem("mathematics", "Mathematics", 18, 160, 54, "functions"),
        SubjectItem("biology", "Biology", 22, 190, 62, "biotech"),
        SubjectItem("history", "History & Civics", 10, 85, 31, "account_balance"),
        SubjectItem("cs", "CS & AI", 8, 64, 22, "terminal")
    )

    val chapters = listOf(
        ChapterAccordion(
            id = "ch_1",
            number = "01",
            title = "Electrostatics & Electric Potential",
            modulesCount = 9,
            duration = "3h 45m",
            description = "Deep dive into Coulomb's law, electrostatic fields, dipole interactions, Gauss's Theorem applications, and dielectric capacitance with conceptual sound simulations.",
            subTracks = listOf(
                SubTrackItem("sub_1", "1.1 Electric Charges & Quantization", "Foundations & audio experiments", "24 mins", isLocked = false),
                SubTrackItem("sub_2", "1.2 Gauss Law & Flux Geometries", "3D spatial sound visualization", "32 mins", isLocked = true)
            )
        ),
        ChapterAccordion(
            id = "ch_2",
            number = "02",
            title = "Current Electricity & Drift Dynamics",
            modulesCount = 11,
            duration = "4h 10m",
            description = "Explore drift velocity, Ohm's law, temperature dependence of resistance, Kirchhoff’s laws, and potentiometer setups via narrative audio breakdowns.",
            subTracks = listOf(
                SubTrackItem("sub_3", "2.1 Drift Velocity & Microscopic Ohm's", "Electron kinetics explained", "28 mins", isLocked = false)
            )
        ),
        ChapterAccordion(
            id = "ch_3",
            number = "03",
            title = "Magnetic Effects & Magnetism",
            modulesCount = 8,
            duration = "3h 15m",
            description = "Biot-Savart law, Ampere's circuital law, cyclotrons, torque on current loops, and Earth's magnetic elements distilled for auditory recall.",
            subTracks = listOf(
                SubTrackItem("sub_4", "3.1 Biot-Savart & Circular Loops", "Right-hand rules in 3D soundscapes", "26 mins", isLocked = false)
            )
        )
    )

    val examCards = listOf(
        ExamCard(
            id = "neet_2025",
            title = "NEET High-Yield Audio Capsule",
            tag = "NEET 2025",
            description = "Complete rapid revision of NCERT Biology, Organic Chemistry reactions, and Physics formulae.",
            syllabusCoverage = 92,
            audioStemsCount = 180,
            coverUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAC4LUJc0psosVtOpP8Jx6LI4_iDagBSvqU2KW7IHKx92j8K7xLO3syct5fDKnbjS_8xemuxCqqYhKgQLahBfyafI2utrcBcfyzDhcuh1fwHm57jYCwkqnsjTzd6WcK3jGqkA91pTYftVKSr_NtQTaNY3Kx33sOY2WSI4bdPvC2J_sVhCiE1FMR4zVCEw-myeSpn1OMF9_YGSTo_FSs47KdsEjd3I2J7-MXBlISwQ8JkC5rgX2tf0Fcig"
        ),
        ExamCard(
            id = "jee_adv",
            title = "Physics & Calculus Concept Lab",
            tag = "JEE ADV",
            description = "Rigorous problem intuition, multi-concept linkages, and previous 15 years question acoustics.",
            syllabusCoverage = 78,
            audioStemsCount = 215,
            coverUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDVfeIC14td993D8jOj5PXM0L8AvOQxwAKt8aQQLqwHixatoue_5FzwXyfh449eM_H7yXzX13J3YcGqcckyljcOuo8mGJ6Priyob_KjV0DNxrrhixLCX2SMN6qhmbQ0HWQlK-LxntifMzGSviT3TT151nNLbX9-CtsOFk9vcwvpkRmPF7cTIEL0crwN80Hu6Dllb_K8Wm_YmYDL0xpYJN1HTM7jVunWPpGKuQjV7McLXvOgNmfnrzywUg"
        ),
        ExamCard(
            id = "upsc_gs1",
            title = "Polity, Economy & Modern History",
            tag = "UPSC GS-1",
            description = "Constitutional articles, economic survey highlights, and ecological maps narrated chronologically.",
            syllabusCoverage = 85,
            audioStemsCount = 320,
            coverUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCr4nWZn_tsjqQhtkW_fsv1z-VHzG72S2s6VyqBccW8-AAR0ZvG9m6qo3vrydh_W6nZw4VLieJ49dbc_fJD-efda2l4WVGUy16Omb03Lg_X7OcdB1_h-Y8EpDlW_caI4gSnCxx77zSWwbm7sZv3UfFlQndLn5do_9BsSevTq-rGAv_uuTUlobCeMlMbgUr_toDlKMMfiYsg70XL1EuppXUTM36rru9bHJUOU-nUXKV2NPrxwevgVhv1bQ"
        )
    )

    val seriesEpisodes = listOf(
        EpisodeItem("ep_1", 1, "Magnetic Fields & Biot-Savart Law", "Current loops, field superposition, and vector formulation.", "18:20", EpisodeStatus.COMPLETED, 100, true),
        EpisodeItem("ep_2", 2, "Ampere's Circuital Law & Solenoid Fields", "Cylindrical symmetry, toroids, and boundary permeability.", "22:15", EpisodeStatus.COMPLETED, 100, true),
        EpisodeItem("ep_3", 3, "Faraday's Law of Induction & Lenz's Rule", "Flux linkage change rates and conservation of energy dynamics.", "26:40", EpisodeStatus.PLAYING, 54, true),
        EpisodeItem("ep_4", 4, "Motional EMF & Eddy Currents", "Conducting rod dynamics in uniform B-fields and magnetic braking.", "19:50", EpisodeStatus.UP_NEXT, 0, false),
        EpisodeItem("ep_5", 5, "Self & Mutual Inductance Deep Dive", "Coupling factor coefficients and inductor energy densities.", "24:10", EpisodeStatus.READY, 0, false),
        EpisodeItem("ep_6", 6, "AC Circuits: Pure R, L, and C Elements", "Phase angles, capacitive reactance, and inductive lags explained.", "28:05", EpisodeStatus.READY, 0, false)
    )

    val downloadedCourses = listOf(
        DownloadedCourse("dl_1", "Physics XII", "Electromagnetism Deep Dive", "8 of 8 episodes", 324, "https://lh3.googleusercontent.com/aida-public/AB6AXuAbJl7W5876OcX3bYFHb5EWMJHdXhuQNlhilHIxq5pi8yol8AmdN5nY4PPj_Qu8QXaFy2ETr5KuFErsXKaFLrif8ZtUMe1eLg0sE9rj5ucogDzsr5AJ77QjOlt8vaCwzMEp1Y97lhDPKzWtUyjWESPQk429kYqeN_GuRIlbBYc_GgPUQFFTlxwSjUx0bADUww1QaNd_OgebfKIH8zrvQp1C9XYeQ9QDPXQqItloPp0g24K544pOSaBWbw"),
        DownloadedCourse("dl_2", "Calculus II", "Integral Calculus Mastery", "6 of 6 episodes", 412, "https://lh3.googleusercontent.com/aida-public/AB6AXuCP8UaTSXxhHq665fs9mKPfv5a6_I1DBeQfFMdZuvznuTAnE03g7uKedjo_1411D50t7KUydmUNU-0FAbLKvtQpENyY6C7jhvdITdecZUgBhnT2h8UDp81wOKwWwKPXsAyK1apX5VPruJL03_T-WM1lSNGGlZcGptnohfSqOFIw39Ev34ap4G4RpSXhkiv6a0E4zYVr5sOhRqywfdVtc33F9NuD5deGTYEJqwEXc2B5zSRiKuKJtvDcVA"),
        DownloadedCourse("dl_3", "Chemistry", "Reaction Mechanisms", "4 of 4 episodes", 265, "https://lh3.googleusercontent.com/aida-public/AB6AXuCHFc9uAI_TT0l8Qkpev_A149e3O_yT0iHQYq4A_y3E5pecwNwVN5iOT5RZhNuGcPv84nopN8xE_xZZ-m9AHB48-D93Qc41n_uZTZbeKZrYl2dgd4w1Aw4-XlC68HhQMfO8P8uYMM9JstT9BRDNhBvV12E9FR9KPelNPLkpqNTTp335_S3PtqYV0Efid8SuNEisdfYGtWx2SHjorwVtf2jh9fIaMpdG-rZOGO-iEdLXYTyckMPkCc604w")
    )

    val activeSavedSeries = listOf(
        SeriesItem("ser_1", "Electromagnetic Induction", "7/16 Episodes Completed", "Dr. Rao", 4.9f, "4.2k", "4h 45m", "2025", "https://lh3.googleusercontent.com/aida-public/AB6AXuBRmGlOHdgCQ6uDgPMkeatEKUdvkJonCUlIwLepAqyLiDITrwoT4GHiLt3X_bZFPlROh87pIfhp5qwvnStjIKmVa3xcbAJWzauYI8AuK3piJe5A-4sWZADvS-hNrdAyw3FhbWQjRNXnugq68YzDEPeb78VRQXJHuqldZCRuRm_Is4a7c9vReqiuJd5RkYD8qcEn1T_v52cawgZiaxjmxHVmdkFolj9vcuMfnYpWCYKOB4ZdcrZg-fZ_kA", 16, 45, 7, "Ep 7", "Physics • Class 12"),
        SeriesItem("ser_2", "Rotational Dynamics & Torque", "11/14 Episodes Completed", "Prof. Vikram", 4.8f, "3.1k", "3h 50m", "2025", "https://lh3.googleusercontent.com/aida-public/AB6AXuDISELbVOQem5gPcXUUxumQ1owzhDjrKlFj9jpZVZ_jBjP-ZEocNkP8Xxi_17XyaGmQR2HImgLiZpRjG6uO7ERuM0WCTl4MRltzz-Vin6h8jtmrnAQFVhIWDDgkyK59ZArVaVWyTdy36tILO09VVQsN0UBNh3NYLFkLRudxUtzJsiiT7rMOTLdFafEvGgdlDbUJ3r2FaELVHvf0Yp08zaS8UH_mPj5NSSdfzVTM-jU5O4ywuiiYt86OuA", 14, 78, 11, "Ep 11", "Mechanics • Advanced"),
        SeriesItem("ser_3", "Molecular Basis of Inheritance", "3/15 Episodes Completed", "Dr. Shweta", 4.7f, "2.9k", "4h 10m", "2025", "https://lh3.googleusercontent.com/aida-public/AB6AXuBrM-pxEGYPJ4MJNiq3OqzZTm28ZonvOQKK2172NQrFj4Gydh2hfyvp0KIyDih0C2JgTji2MOjlknnXX6fG2421FnU2dC7Ib_RPJZIfG4bL7dQDfwBrEy4ROxp6SMZUd0b8gAEG5S0AZDArp_ejU-tU4zeZ9MCleJ0iruxxHnJbPPxMso0Q7wypR290Wfz5DHGjqs-lTAZWLpr_uuEUFAwIXDJU6c0VHATXCW52D56vh0G6j7MtdaZppw", 15, 20, 3, "Ep 3", "Biology • Genetics")
    )

    val bookmarkedNotes = listOf(
        BookmarkNote(
            id = "bm_1",
            timestamp = "14:28",
            episodeMeta = "Physics • Ep 3",
            title = "Lenz's Law Negative Sign Explanation",
            noteQuote = "\"Remember: the minus sign comes from Faraday's law indicating opposing flux direction. Crucial for board exam Q4!\"",
            savedTimeAgo = "Saved 2 days ago"
        ),
        BookmarkNote(
            id = "bm_2",
            timestamp = "08:15",
            episodeMeta = "Calculus • Ep 5",
            title = "Integration by Substitution Shortcuts",
            noteQuote = "\"When derivative of denominator sits in numerator: instant ln|f(x)| + C. Skip long u-substitution.\"",
            savedTimeAgo = "Saved 5 days ago"
        )
    )

    val listeningHistory = listOf(
        ListeningHistoryItem("hist_1", "Ep 3: Faraday & Lenz's Experimental Proof", "Physics XII • 24 min completed", "24 min", "Today", "48 mins listened"),
        ListeningHistoryItem("hist_2", "Ep 2: Magnetic Flux & Induced EMF Basics", "Physics XII • 24 min completed", "24 min", "Today", "48 mins listened"),
        ListeningHistoryItem("hist_3", "Calculus: Trigonometric Integrals & Reductions", "Mathematics • 45 min completed", "45 min", "Yesterday", "1h 15m listened"),
        ListeningHistoryItem("hist_4", "AI Tutor Concept Check: Self Induction", "Interactive Audio Quiz • 30 min", "30 min", "Yesterday", "1h 15m listened"),
        ListeningHistoryItem("hist_5", "Organic Chemistry: Aldehydes & Ketones", "Completed on Oct 14 • 52 min", "52 min", "Last Week", "5h 40m total")
    )
}
