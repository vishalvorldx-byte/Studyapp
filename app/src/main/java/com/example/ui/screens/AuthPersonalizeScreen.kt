package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AuthPersonalizeScreen(
    onContinue: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.HOME) },
    modifier: Modifier = Modifier
) {
    val isDemoMode by EduDxRepository.isDemoMode.collectAsState()
    val selectedGrade by EduDxRepository.selectedGrade.collectAsState()
    val selectedBoard by EduDxRepository.selectedBoard.collectAsState()
    val selectedExams by EduDxRepository.selectedExams.collectAsState()
    val dailyPace by EduDxRepository.dailyPaceMinutes.collectAsState()

    var contactInput by remember { mutableStateOf(if (isDemoMode) "demo.student@edudx.audio" else "") }
    var selectedCountryCode by remember { mutableStateOf("+91") }
    var isCountryMenuOpen by remember { mutableStateOf(false) }
    var otpSent by remember { mutableStateOf(false) }

    val isInputValid = contactInput.length >= 8

    val grades = listOf("Class 9", "Class 10", "Class 11", "Class 12", "College / Competitive")
    val boards = listOf("CBSE", "ICSE", "State Board", "International")
    val exams = listOf("JEE Main/Adv", "NEET-UG", "CUET", "Boards Prep", "Tech & Coding")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // 1. Header Branding & Demo Mode Trigger
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(OrangePrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Headphones, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "EduDX", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(CyanSecondary))
                        }
                        Text(text = "AUDIO LEARNING", color = DarkOnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }

                // Demo Mode Button
                Surface(
                    shape = RoundedCornerShape(50),
                    color = DarkSurfaceContainerHigh,
                    modifier = Modifier.clickable {
                        val next = !isDemoMode
                        EduDxRepository.setDemoMode(next)
                        if (next) {
                            contactInput = "demo.student@edudx.audio"
                            EduDxRepository.showToast("Demo Mode enabled: Simulated student credentials")
                        } else {
                            contactInput = ""
                            EduDxRepository.showToast("Demo Mode reset")
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                        Text(text = "Demo Mode", color = CyanSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (isDemoMode) CyanSecondary else CyanSecondary.copy(alpha = 0.3f))
                        )
                    }
                }
            }
        }

        // 2. Title & Subtitle
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = "Welcome to EduDX", color = DarkOnSurface, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Sign in or set up your personalized learning path", color = DarkOnSurfaceVariant, fontSize = 13.sp, modifier = Modifier.padding(top = 2.dp))
            }
        }

        // Demo Notice
        item {
            AnimatedVisibility(visible = isDemoMode) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = DarkSurfaceContainerHigh,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Trial Profile Loaded", color = CyanSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Exploring with simulated student credentials", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                            }
                        }
                        Text(
                            text = "Reset",
                            color = OrangePrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                EduDxRepository.setDemoMode(false)
                                contactInput = ""
                            }
                        )
                    }
                }
            }
        }

        // 3. Auth Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Phone or Email Address", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        if (isInputValid) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                                Text(text = "Valid", color = CyanSecondary, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Country dropdown button
                        Box {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = DarkSurfaceContainerHighest,
                                modifier = Modifier
                                    .clickable { isCountryMenuOpen = true }
                                    .padding(vertical = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(text = selectedCountryCode, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                                }
                            }

                            DropdownMenu(
                                expanded = isCountryMenuOpen,
                                onDismissRequest = { isCountryMenuOpen = false }
                            ) {
                                listOf("+91 (India)", "+1 (USA)", "+44 (UK)", "+971 (UAE)").forEach { c ->
                                    val code = c.split(" ")[0]
                                    DropdownMenuItem(
                                        text = { Text(c) },
                                        onClick = {
                                            selectedCountryCode = code
                                            isCountryMenuOpen = false
                                        }
                                    )
                                }
                            }
                        }

                        // Text input
                        TextField(
                            value = contactInput,
                            onValueChange = { contactInput = it },
                            placeholder = { Text("98765 43210 or email", color = DarkOnSurfaceVariant.copy(alpha = 0.6f), fontSize = 13.sp) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = DarkSurfaceContainerHighest,
                                unfocusedContainerColor = DarkSurfaceContainerHighest,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = DarkOnSurface,
                                unfocusedTextColor = DarkOnSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("auth_contact_input")
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            otpSent = true
                            EduDxRepository.showToast("Instant 6-digit verification code sent!")
                        },
                        enabled = isInputValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (otpSent) CyanSecondaryContainer else OrangePrimaryContainer
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("send_otp_btn")
                    ) {
                        Icon(imageVector = if (otpSent) Icons.Default.Check else Icons.Default.Sms, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = if (otpSent) "Code Dispatched!" else "Send Verification OTP", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    if (otpSent) {
                        Text(
                            text = "We sent an instant 6-digit verification code. Enter directly or proceed with trial profile.",
                            color = DarkOnSurfaceVariant,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Social Logins
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = DarkSurfaceContainerHighest)
                        Text(text = "OR SIGN IN WITH", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                        HorizontalDivider(modifier = Modifier.weight(1f), color = DarkSurfaceContainerHighest)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                EduDxRepository.showToast("Signed in via Google account")
                                onContinue()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHighest),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Google", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = {
                                EduDxRepository.showToast("Signed in via Apple ID")
                                onContinue()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHighest),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Apple", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // 4. Personalize Your Track Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                Text(text = "Personalize Your Track", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Target Grade
        item {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "TARGET GRADE / STAGE", color = DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = selectedGrade, color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    grades.forEach { grade ->
                        val isSelected = grade == selectedGrade
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isSelected) OrangePrimaryContainer else DarkSurfaceContainer,
                            modifier = Modifier.clickable { EduDxRepository.setGrade(grade) }
                        ) {
                            Text(
                                text = grade,
                                color = if (isSelected) Color.White else DarkOnSurface,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Board / Curriculum
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "BOARD / CURRICULUM", color = DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = selectedBoard, color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    boards.take(2).forEach { board ->
                        val isSelected = board == selectedBoard
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) OrangePrimaryContainer else DarkSurfaceContainer,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { EduDxRepository.setBoard(board) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = board, color = if (isSelected) Color.White else DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                if (isSelected) {
                                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    boards.drop(2).forEach { board ->
                        val isSelected = board == selectedBoard
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) OrangePrimaryContainer else DarkSurfaceContainer,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { EduDxRepository.setBoard(board) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = board, color = if (isSelected) Color.White else DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                if (isSelected) {
                                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Exam & Goal Focus
        item {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "EXAM & GOAL FOCUS", color = DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Multi-select", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    exams.forEach { exam ->
                        val isSelected = selectedExams.contains(exam)
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isSelected) OrangePrimaryContainer else DarkSurfaceContainer,
                            modifier = Modifier.clickable { EduDxRepository.toggleExam(exam) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = when (exam) {
                                        "JEE Main/Adv" -> Icons.Default.Science
                                        "NEET-UG" -> Icons.Default.MedicalServices
                                        "CUET" -> Icons.Default.School
                                        "Boards Prep" -> Icons.Default.MenuBook
                                        else -> Icons.Default.Terminal
                                    },
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = exam,
                                    color = if (isSelected) Color.White else DarkOnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Daily Audio Sprint Target
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "DAILY AUDIO SPRINT TARGET", color = DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Icon(imageVector = Icons.Default.LocalFireDepartment, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                        Text(text = "Streak Boost", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        Triple(15, "15 min", "Casual Pace" to Icons.Default.Bedtime),
                        Triple(30, "30 min", "Steady Pace" to Icons.Default.Timelapse),
                        Triple(60, "60 min", "Intensive" to Icons.Default.RocketLaunch)
                    ).forEach { (minutes, timeStr, meta) ->
                        val (paceLabel, paceIcon) = meta
                        val isSelected = dailyPace == minutes
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) DarkSurfaceContainerHigh else DarkSurfaceContainer,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { EduDxRepository.setDailyPace(minutes) }
                                .then(
                                    if (isSelected) Modifier.border(1.dp, CyanSecondary.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                    else Modifier
                                )
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    imageVector = paceIcon,
                                    contentDescription = null,
                                    tint = if (isSelected) CyanSecondary else DarkOnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = timeStr,
                                    color = if (isSelected) CyanSecondary else DarkOnSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = paceLabel, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }

        // 5. Continue to EduDX CTA
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("auth_continue_btn")
                ) {
                    Text("Continue to EduDX", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "By proceeding, you agree to EduDX Learning Terms and verify that sound settings will be tuned for immersive audio delivery.",
                    color = DarkOnSurfaceVariant,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
