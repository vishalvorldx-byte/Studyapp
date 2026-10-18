package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

data class QuizQuestion(
    val id: Int,
    val subject: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val hint: String,
    val audioExplanationSeconds: Int,
    val explanation: String
)

@Composable
fun QuizHubScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    val questions = remember {
        listOf(
            QuizQuestion(
                id = 1,
                subject = "Physics • Electromagnetism",
                question = "A metallic ring is held horizontally and a bar magnet is dropped along the axis of the ring with its North pole pointing down. What is the acceleration of the falling magnet?",
                options = listOf(
                    "Equal to g (acceleration due to gravity)",
                    "Less than g because induced current opposes the motion",
                    "More than g because induced magnetic field accelerates it",
                    "Zero, it reaches terminal velocity immediately"
                ),
                correctIndex = 1,
                hint = "Apply Lenz's rule: the induced current in the ring creates an opposing magnetic field.",
                audioExplanationSeconds = 42,
                explanation = "By Lenz's Law, when the North pole approaches, the upper face of the loop acquires North polarity. The upward repulsive magnetic force counters gravity, making net acceleration a < g."
            ),
            QuizQuestion(
                id = 2,
                subject = "Physics • AC Circuits",
                question = "In a pure inductive AC circuit, what is the phase relationship between the alternating voltage V and alternating current I?",
                options = listOf(
                    "Voltage lags current by π/2 radians",
                    "Voltage leads current by π/2 radians",
                    "Voltage and current are strictly in phase",
                    "Voltage and current are in phase opposition (π radians)"
                ),
                correctIndex = 1,
                hint = "Remember mnemonic 'ELI the ICE man': in an inductor (L), EMF (E) comes before current (I).",
                audioExplanationSeconds = 35,
                explanation = "In an inductor, the back-EMF opposes current rise, causing current to reach its peak 90° (π/2 radians) after the voltage waveform peaks."
            ),
            QuizQuestion(
                id = 3,
                subject = "Chemistry • Organic Chemistry",
                question = "Which reagent uniquely distinguishes an aldehyde from a ketone via the formation of a silver mirror on the test tube wall?",
                options = listOf(
                    "Fehling's Solution B (Sodium potassium tartrate)",
                    "Tollens' Reagent (Ammoniacal silver nitrate)",
                    "Lucas Reagent (Concentrated HCl + anhydrous ZnCl₂)",
                    "Benedict's qualitative solution"
                ),
                correctIndex = 1,
                hint = "Aldehydes are easily oxidizable compared to ketones.",
                audioExplanationSeconds = 38,
                explanation = "Tollens' reagent contains [Ag(NH₃)₂]⁺. Aldehydes reduce Ag⁺ ions into shiny elemental metallic silver that coats the container."
            ),
            QuizQuestion(
                id = 4,
                subject = "Mathematics • Calculus",
                question = "What is the value of ∫ e^(2x) · cos(3x) dx evaluated using integration by parts?",
                options = listOf(
                    "(e^(2x) / 13) · [2 cos(3x) + 3 sin(3x)] + C",
                    "(e^(2x) / 13) · [3 cos(3x) - 2 sin(3x)] + C",
                    "(e^(2x) / 5) · [cos(3x) + sin(3x)] + C",
                    "(e^(2x) / 6) · [2 sin(3x) - 3 cos(3x)] + C"
                ),
                correctIndex = 0,
                hint = "Standard cyclic integration formula: e^(ax)/(a²+b²) · (a cos bx + b sin bx).",
                audioExplanationSeconds = 50,
                explanation = "Applying tabular integration twice leads to the cyclic term, where a = 2, b = 3, and a² + b² = 4 + 9 = 13."
            ),
            QuizQuestion(
                id = 5,
                subject = "Biology • Genetics",
                question = "According to Chargaff's rules of DNA base pairing, what is the mathematical ratio between purines and pyrimidines in double-stranded DNA?",
                options = listOf(
                    "(A + C) / (G + T) = 2.0",
                    "(A + G) / (T + C) = 1.0",
                    "(A + T) / (G + C) = 1.0 constantly in all species",
                    "(G + C) / (A + T) = 0.5"
                ),
                correctIndex = 1,
                hint = "Adenine pairs with Thymine, and Guanine pairs with Cytosine.",
                audioExplanationSeconds = 36,
                explanation = "Because A = T and G = C, the total purines (A + G) equals total pyrimidines (T + C), giving a constant ratio of 1.0."
            )
        )
    }

    var currentQuestionIdx by remember { mutableIntStateOf(0) }
    var selectedOptionIdx by remember { mutableStateOf<Int?>(null) }
    var hasSubmittedAnswer by remember { mutableStateOf(false) }
    var isAudioPlaying by remember { mutableStateOf(false) }
    var showHint by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }

    val currentQ = questions[currentQuestionIdx]

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // 1. Top Header Bar
        item {
            Surface(
                color = DarkBackground.copy(alpha = 0.95f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainerHigh)
                                .testTag("quiz_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = DarkOnSurface,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "DAILY AUDIO DRILL",
                                color = OrangePrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Concept Master Quiz",
                                color = DarkOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = OrangePrimaryContainer.copy(alpha = 0.2f),
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = OrangePrimaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "2x XP",
                                    color = OrangePrimaryContainer,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        IconButton(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.ANALYTICS) },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainerHigh)
                        ) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Stats",
                                tint = DarkOnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Question Progress & Navigator Row
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${currentQuestionIdx + 1} of ${questions.size}",
                        color = DarkOnSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Score: $score / ${questions.size}",
                        color = CyanSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Linear indicator
                LinearProgressIndicator(
                    progress = { (currentQuestionIdx + 1f) / questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50)),
                    color = OrangePrimaryContainer,
                    trackColor = DarkSurfaceContainerHigh,
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Question selector pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    questions.forEachIndexed { idx, _ ->
                        val isCurrent = idx == currentQuestionIdx
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isCurrent) OrangePrimaryContainer
                                    else if (idx < currentQuestionIdx) CyanSecondary.copy(alpha = 0.3f)
                                    else DarkSurfaceContainerHigh
                                )
                                .clickable {
                                    currentQuestionIdx = idx
                                    selectedOptionIdx = null
                                    hasSubmittedAnswer = false
                                    isAudioPlaying = false
                                    showHint = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Q${idx + 1}",
                                color = if (isCurrent) Color.White else DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // 3. Question Card with Audio Prompt
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = DarkSurfaceContainerHighest
                        ) {
                            Text(
                                text = currentQ.subject,
                                color = CyanSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        // Audio Question Button
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isAudioPlaying) OrangePrimaryContainer else DarkSurfaceContainerHighest,
                            modifier = Modifier.clickable {
                                isAudioPlaying = !isAudioPlaying
                                EduDxRepository.showToast(if (isAudioPlaying) "Playing narrated question & clues" else "Audio paused")
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.VolumeUp,
                                    contentDescription = "Listen",
                                    tint = if (isAudioPlaying) Color.White else OrangePrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (isAudioPlaying) "Listening..." else "Audio Hint",
                                    color = if (isAudioPlaying) Color.White else DarkOnSurface,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentQ.question,
                        color = DarkOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )

                    if (showHint) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = DarkSurfaceContainerLowest,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = OrangePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = currentQ.hint,
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    } else {
                        TextButton(
                            onClick = { showHint = true },
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = null,
                                tint = DarkOnSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Need a hint?",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // 4. Options List
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentQ.options.forEachIndexed { optIdx, optionText ->
                    val isSelected = selectedOptionIdx == optIdx
                    val isCorrect = optIdx == currentQ.correctIndex

                    val borderColor = when {
                        hasSubmittedAnswer && isCorrect -> CyanSecondary
                        hasSubmittedAnswer && isSelected && !isCorrect -> Color(0xFFEF4444)
                        isSelected -> OrangePrimaryContainer
                        else -> BorderOutline.copy(alpha = 0.2f)
                    }

                    val bgColor = when {
                        hasSubmittedAnswer && isCorrect -> CyanSecondary.copy(alpha = 0.12f)
                        hasSubmittedAnswer && isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.12f)
                        isSelected -> OrangePrimaryContainer.copy(alpha = 0.15f)
                        else -> DarkSurfaceContainerHigh
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = bgColor,
                        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !hasSubmittedAnswer) {
                                selectedOptionIdx = optIdx
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            hasSubmittedAnswer && isCorrect -> CyanSecondary
                                            hasSubmittedAnswer && isSelected && !isCorrect -> Color(0xFFEF4444)
                                            isSelected -> OrangePrimaryContainer
                                            else -> DarkSurfaceContainerLowest
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                val label = ('A' + optIdx).toString()
                                Text(
                                    text = label,
                                    color = if (isSelected || (hasSubmittedAnswer && isCorrect)) Color.White else DarkOnSurfaceVariant,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = optionText,
                                color = DarkOnSurface,
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f),
                                lineHeight = 18.sp
                            )

                            if (hasSubmittedAnswer) {
                                if (isCorrect) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Correct",
                                        tint = CyanSecondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Incorrect",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Explanation Section (Post Submission)
        if (hasSubmittedAnswer) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = OrangePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Audio Mentor Explanation",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = "${currentQ.audioExplanationSeconds}s Breakdown",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentQ.explanation,
                            color = DarkOnSurfaceVariant,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHighest),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = CyanSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Ask AI Tutor to Clarify This Concept",
                                color = DarkOnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // 6. Action Button (Submit or Next Question)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                if (!hasSubmittedAnswer) {
                    Button(
                        onClick = {
                            if (selectedOptionIdx != null) {
                                hasSubmittedAnswer = true
                                if (selectedOptionIdx == currentQ.correctIndex) {
                                    score += 1
                                    EduDxRepository.showToast("Correct! +20 XP")
                                } else {
                                    EduDxRepository.showToast("Keep going! Learn from the explanation.")
                                }
                            }
                        },
                        enabled = selectedOptionIdx != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OrangePrimaryContainer,
                            disabledContainerColor = DarkSurfaceContainerHigh
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("quiz_submit_btn")
                    ) {
                        Text(
                            text = "Check Answer",
                            color = if (selectedOptionIdx != null) Color.White else DarkOnSurfaceVariant,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            if (currentQuestionIdx < questions.size - 1) {
                                currentQuestionIdx += 1
                                selectedOptionIdx = null
                                hasSubmittedAnswer = false
                                isAudioPlaying = false
                                showHint = false
                            } else {
                                quizCompleted = true
                                EduDxRepository.showToast("Quiz Complete! Perfect daily practice.")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CyanSecondary),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("quiz_next_btn")
                    ) {
                        Text(
                            text = if (currentQuestionIdx < questions.size - 1) "Next Question" else "View Score & Analytics",
                            color = Color(0xFF003632),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color(0xFF003632),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
