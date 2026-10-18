package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.model.SubjectItem
import com.example.ui.theme.*

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier
) {
    var selectedMainCategory by remember { mutableStateOf("K-12 School (6-12)") }
    val selectedGrade by EduDxRepository.selectedGrade.collectAsState()
    var selectedSubjectId by remember { mutableStateOf("physics") }
    val expandedChapterId by EduDxRepository.expandedChapterId.collectAsState()

    val mainCategories = listOf(
        "K-12 School (6-12)",
        "Competitive Exams",
        "Tech & Coding",
        "Skill Development",
        "General Knowledge"
    )

    val grades = listOf("Class 6", "Class 7", "Class 8", "Class 9", "Class 10", "Class 11", "Class 12")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // 1. Curriculum Explorer Header & Segmented Bar
        item {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(OrangePrimaryContainer)
                        )
                        Text(
                            text = "Curriculum Explorer",
                            color = DarkOnSurface,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "CBSE / NCERT 2024-25",
                        color = OrangePrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerHigh)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                // Category Ribbon
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    items(mainCategories) { cat ->
                        val isSelected = cat == selectedMainCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedMainCategory = cat },
                            label = { Text(cat, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = OrangePrimaryContainer,
                                selectedLabelColor = Color.White,
                                containerColor = DarkSurfaceContainerHigh,
                                labelColor = DarkOnSurfaceVariant
                            ),
                            shape = RoundedCornerShape(50),
                            border = null,
                            modifier = Modifier.testTag("main_cat_$cat")
                        )
                    }
                }

                // Grade Selector Pills
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = CyanSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "GRADE", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    items(grades) { grade ->
                        val isSelected = grade == selectedGrade
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) CyanSecondaryContainer else DarkSurfaceContainer)
                                .clickable { EduDxRepository.setGrade(grade) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("grade_chip_$grade")
                        ) {
                            Text(
                                text = grade,
                                color = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // 2. Select Subject (2-Column Grid)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(text = "AUDIO LIBRARY", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Select Subject", color = DarkOnSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Headset, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                        Text(text = "6 Subjects Active", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Subject Grid (Row of pairs)
                val chunked = EduDxRepository.subjects.chunked(2)
                chunked.forEach { rowSubjects ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowSubjects.forEach { subj ->
                            val isSelected = subj.id == selectedSubjectId
                            SubjectCardItem(
                                subject = subj,
                                isSelected = isSelected,
                                onClick = {
                                    selectedSubjectId = subj.id
                                    if (subj.id == "physics") {
                                        EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL)
                                    } else {
                                        EduDxRepository.showToast("Loaded ${subj.name} syllabus")
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // 3. Detailed Chapter Directory (Physics Selected)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                // Curriculum Preview Banner
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkSurfaceContainerHigh,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(OrangePrimaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "CURRICULUM PREVIEW", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "Physics • Class 12 Chapters",
                                    color = DarkOnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { EduDxRepository.showToast("Downloading Physics Syllabus PDF...") }
                                .padding(4.dp)
                        ) {
                            Text(text = "Syllabus PDF", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Chapter Accordions
                EduDxRepository.chapters.forEach { chapter ->
                    val isExpanded = expandedChapterId == chapter.id
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("chapter_accordion_${chapter.id}")
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { EduDxRepository.toggleChapterExpanded(chapter.id) }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isExpanded) OrangePrimaryContainer.copy(alpha = 0.2f)
                                                else DarkSurfaceContainerHigh
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = chapter.number,
                                            color = if (isExpanded) OrangePrimaryContainer else DarkOnSurfaceVariant,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = chapter.title,
                                            color = DarkOnSurface,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "${chapter.modulesCount} Audio Modules • ${chapter.duration}",
                                            color = DarkOnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    contentDescription = "Toggle",
                                    tint = DarkOnSurfaceVariant,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .rotate(if (isExpanded) 180f else 0f)
                                )
                            }

                            AnimatedVisibility(visible = isExpanded) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = chapter.description,
                                        color = DarkOnSurfaceVariant,
                                        fontSize = 12.sp,
                                        lineHeight = 18.sp
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    chapter.subTracks.forEach { sub ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = DarkSurfaceContainerHigh.copy(alpha = 0.6f),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp)
                                                .clickable {
                                                    if (!sub.isLocked) {
                                                        EduDxRepository.navigateTo(ScreenRoute.PLAYER)
                                                    } else {
                                                        EduDxRepository.showToast("Locked: Complete previous lesson to unlock")
                                                    }
                                                }
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    Icon(
                                                        imageVector = if (sub.isLocked) Icons.Default.Lock else Icons.Default.PlayCircle,
                                                        contentDescription = null,
                                                        tint = if (sub.isLocked) DarkOnSurfaceVariant else OrangePrimaryContainer,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                    Column {
                                                        Text(text = sub.title, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                                        Text(text = sub.subtitle, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                                    }
                                                }
                                                Text(text = sub.duration, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Exam Accelerators: Target 2025 Masterclasses (Horizontal Scroller)
        item {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            Text(text = "EXAM ACCELERATORS", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Target 2025 Masterclasses", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.EXPLORE) }
                            .padding(4.dp)
                    ) {
                        Text(text = "View All", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    items(EduDxRepository.examCards) { exam ->
                        ExamMasterclassCard(exam = exam, onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) })
                    }
                }
            }
        }

        // 5. Interactive AI Syllabus Assistant Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurfaceContainerLow)
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(CyanSecondaryContainer.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(24.dp))
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Can't locate your board syllabus?",
                            color = DarkOnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Let EduDX AI build a tailored audio roadmap based on your exam dates.",
                            color = DarkOnSurfaceVariant,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Button(
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR) },
                        colors = ButtonDefaults.buttonColors(containerColor = CyanSecondary),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("categories_ask_ai_btn")
                    ) {
                        Text("Ask AI", color = CyanOnSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectCardItem(
    subject: SubjectItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) DarkSurfaceContainerHigh else DarkSurfaceContainerLow
        ),
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("subject_card_${subject.id}")
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) DarkSurfaceContainer else DarkSurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (subject.icon) {
                            "electric_bolt" -> Icons.Default.Bolt
                            "science" -> Icons.Default.Science
                            "functions" -> Icons.Default.Functions
                            "biotech" -> Icons.Default.Biotech
                            "account_balance" -> Icons.Default.AccountBalance
                            else -> Icons.Default.Terminal
                        },
                        contentDescription = subject.name,
                        tint = if (isSelected) OrangePrimaryContainer else CyanSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = subject.name,
                color = DarkOnSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${subject.chaptersCount} Chapters • ${subject.episodesCount} Eps",
                color = DarkOnSurfaceVariant,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            if (isSelected) {
                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Listening Now", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                }
            } else {
                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(12.dp))
                    Text(text = "${subject.durationHours} hrs total", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun ExamMasterclassCard(
    exam: com.example.model.ExamCard,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
        modifier = Modifier
            .width(260.dp)
            .clickable(onClick = onClick)
            .testTag("exam_card_${exam.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = exam.coverUrl),
                    contentDescription = exam.title,
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = exam.tag,
                    color = CyanSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(CyanSecondaryContainer.copy(alpha = 0.25f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = exam.title,
                color = DarkOnSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = exam.description,
                color = DarkOnSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Syllabus Covered", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                Text(text = "${exam.syllabusCoverage}% Complete", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { exam.syllabusCoverage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50)),
                color = CyanSecondary,
                trackColor = DarkSurfaceContainerHighest,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                    Text(text = "${exam.audioStemsCount} Audio Stems", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(OrangePrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
