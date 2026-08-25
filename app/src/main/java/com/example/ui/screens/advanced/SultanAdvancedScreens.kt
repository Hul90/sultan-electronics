package com.example.ui.screens.advanced

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SultanAdvancedData
import com.example.model.AdvancedCategoryItem
import com.example.model.AdvancedComponent
import com.example.model.Component3DType
import com.example.model.FanMotorGuide
import com.example.model.PracticalCircuitGuide
import com.example.model.SchematicSymbol
import com.example.ui.components.RealisticComponentRenderer
import com.example.ui.components.Sultan3DComponentViewer
import com.example.ui.components.SultanTopAppBar
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.ElectricGreenDark
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechPurple
import com.example.ui.viewmodel.SultanViewModel

@Composable
fun SultanAdvancedMainScreen(
    viewModel: SultanViewModel,
    onNavigate: (String) -> Unit,
    initialCategoryFilter: String = "ALL"
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember(initialCategoryFilter) { mutableStateOf(initialCategoryFilter) }

    val categories = SultanAdvancedData.categories
    val allComponents = SultanAdvancedData.allComponents

    val selectedCategoryName = categories.firstOrNull { it.id == selectedCategoryFilter }?.name
    fun categoryMatches(componentCategory: String, categoryName: String): Boolean {
        val a = componentCategory.lowercase()
        val b = categoryName.lowercase()
        return a == b ||
            (b == "operational amplifiers" && a.contains("op-amp")) ||
            (b == "microcontrollers" && (a.contains("microcontroller") || a.contains("mcu"))) ||
            (b == "esp modules" && a.contains("esp")) ||
            (b == "arduino boards" && a.contains("arduino")) ||
            (b == "raspberry pi" && (a.contains("raspberry") || a.contains("rpi"))) ||
            (b == "timer ics" && a.contains("timer")) ||
            (b == "logic ics" && a.contains("logic")) ||
            (b == "voltage regulators" && a.contains("regulator")) ||
            (b == "mosfets" && a.contains("mosfet")) ||
            (b == "transistors" && a.contains("transistor")) ||
            (b == "diodes" && a.contains("diode")) ||
            (b == "capacitors" && a.contains("capacitor"))
    }

    val filteredCategories = if (searchQuery.isBlank()) {
        categories
    } else {
        categories.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.subtitle.contains(searchQuery, ignoreCase = true) ||
            allComponents.any { c -> c.partNumber.contains(searchQuery, true) || c.name.contains(searchQuery, true) }
        }
    }

    val filteredComponents = allComponents.filter { component ->
        val categoryOk = selectedCategoryName == null || categoryMatches(component.category, selectedCategoryName)
        val searchOk = searchQuery.isBlank() ||
            component.partNumber.contains(searchQuery, ignoreCase = true) ||
            component.name.contains(searchQuery, ignoreCase = true) ||
            component.category.contains(searchQuery, ignoreCase = true) ||
            component.description.contains(searchQuery, ignoreCase = true)
        categoryOk && searchOk
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "SULTAN ADVANCED",
                subtitle = "Professional Reference & 3D Component System",
                onSearchClick = { onNavigate("search") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Sultan Advanced Brand Hero Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFFFD700).copy(alpha = 0.6f), RoundedCornerShape(18.dp)),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF041812))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF00291D),
                                        Color(0xFF001A12),
                                        Color(0xFF02100B)
                                    )
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFF00382B))
                                            .border(1.5.dp, Color(0xFFFFD700), RoundedCornerShape(10.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "SA",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 20.sp,
                                            color = Color(0xFFFFD700),
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "SULTAN ADVANCED",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.Black,
                                                    letterSpacing = 1.sp,
                                                    color = Color(0xFFFFD700)
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = Color(0xFFFFD700).copy(alpha = 0.2f),
                                                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFFFD700))
                                            ) {
                                                Text(
                                                    text = "PRO",
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFFFFD700),
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "Electronics Toolkit & Pinout Reference",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = PhosphorCyan,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.Black.copy(alpha = 0.5f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen)
                                ) {
                                    Text(
                                        text = "25 CATEGORIES",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ElectricGreen,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Search bar in Hero Card
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                                color = Color.Transparent
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = PhosphorCyan,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    TextField(
                                        value = searchQuery,
                                        onValueChange = { searchQuery = it },
                                        placeholder = {
                                            Text(
                                                text = "Search 25 categories, parts, pinouts...",
                                                fontSize = 12.sp,
                                                color = Color.White.copy(alpha = 0.6f)
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. 4 Quick Action Cards (Pinout Library, 3D Viewer, Wiring & Fan Guides, Compare & Identify)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickAdvancedCard(
                        title = "Pinouts",
                        icon = Icons.Default.Memory,
                        color = PhosphorCyan,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("pinouts") }
                    )
                    QuickAdvancedCard(
                        title = "3D View",
                        icon = Icons.Default.Widgets,
                        color = Color(0xFFFFD700),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("sa_3d_showcase") }
                    )
                    QuickAdvancedCard(
                        title = "Fan & Motor",
                        icon = Icons.Default.ElectricBolt,
                        color = NeonRed,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("sa_fan_motors") }
                    )
                    QuickAdvancedCard(
                        title = "Compare",
                        icon = Icons.Default.CompareArrows,
                        color = ElectricGreen,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("sa_compare") }
                    )
                }
            }

            // 3. Workshop Utilities
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    QuickAdvancedCard(title = "Stock Manager", icon = Icons.Default.Inventory2, color = ElectricGreen, modifier = Modifier.weight(1f), onClick = { onNavigate("sa_stock") })
                    QuickAdvancedCard(title = "Workbench", icon = Icons.Default.WorkspacePremium, color = TechAmber, modifier = Modifier.weight(1f), onClick = { onNavigate("sa_workbench") })
                    QuickAdvancedCard(title = "Handbook", icon = Icons.Default.MenuBook, color = PhosphorCyan, modifier = Modifier.weight(1f), onClick = { onNavigate("sa_handbook") })
                }
            }

            // 4. Featured 3D Components Showcase (Interactive Horizontal Carousel)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PhosphorCyan)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "3D COMPONENT WORKSTATION",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    Text(
                        text = "VIEW ALL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PhosphorCyan,
                        modifier = Modifier.clickable { onNavigate("sa_3d_showcase") }
                    )
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredComponents.take(if (searchQuery.isBlank()) 12 else 50)) { comp ->
                        Card(
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { onNavigate("sa_detail/${comp.id}") }
                                .border(1.dp, ElectricGreen.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF06100D)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    RealisticComponentRenderer(
                                        type = comp.threeDType,
                                        label = comp.partNumber,
                                        modifier = Modifier.size(100.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = comp.partNumber,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Black,
                                            color = PhosphorCyan,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    )
                                    Text(
                                        text = comp.packageType.split(" ").firstOrNull() ?: "",
                                        fontSize = 10.sp,
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                }
                                Text(
                                    text = comp.name,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 11.sp
                                    ),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            if (searchQuery.isNotBlank()) {
                item {
                    Text(
                        text = "${filteredComponents.size} COMPONENT / PINOUT MATCHES",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PhosphorCyan
                    )
                }
            }

            // 4. Featured Fan & Motor Wiring Reference Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate("sa_fan_motors") }
                        .border(1.dp, NeonRed.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1F0808))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(NeonRed.copy(alpha = 0.2f))
                                    .border(1.dp, NeonRed, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ElectricBolt,
                                    contentDescription = "Fan & Motor",
                                    tint = NeonRed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Fan & Motor Wiring Guides",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = "Ceiling Fan, Table Fan, 2/3/4/5-Wire, Caps",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFFFF8A80),
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open",
                            tint = NeonRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 5. 25 Categories Grid
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ElectricGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "25 PROFESSIONAL CATEGORIES",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    Text(
                        text = "${filteredCategories.size} ITEMS",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = ElectricGreen
                    )
                }
            }

            items(filteredCategories) { cat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (cat.id) {
                                "cat_fan_motors" -> onNavigate("sa_fan_motors")
                                "cat_wiring_guides" -> onNavigate("sa_practical_guides")
                                else -> onNavigate("sa_category/${cat.id}")
                            }
                        }
                        .border(1.dp, Color(cat.accentColor).copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF081410)),
                                contentAlignment = Alignment.Center
                            ) {
                                RealisticComponentRenderer(
                                    type = cat.iconType,
                                    label = "",
                                    modifier = Modifier.size(46.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = cat.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = cat.subtitle,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 11.sp
                                    ),
                                    maxLines = 1
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(cat.accentColor).copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${cat.count}+",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(cat.accentColor),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Open",
                                tint = Color.White.copy(alpha = 0.5f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickAdvancedCard(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SultanAdvancedDetailScreen(
    compId: String,
    viewModel: SultanViewModel,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val comp = SultanAdvancedData.allComponents.find { it.id == compId } ?: SultanAdvancedData.allComponents[0]
    val isFav = viewModel.isFavorite(comp.id)

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("PINOUT & 3D", "RATINGS", "CIRCUITS", "APPLICATIONS", "COMPARE")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = comp.partNumber,
                subtitle = comp.name,
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite(comp.id, "COMPONENT", "${comp.partNumber} - ${comp.name}", comp.description, "sa_detail/${comp.id}")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = DarkSurface,
                contentColor = ElectricGreen,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = ElectricGreen
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTabIndex == index) ElectricGreen else PhosphorCyan.copy(alpha = 0.7f)
                            )
                        }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 14.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> {
                        // 3D Viewport & Pinout
                        item {
                            Sultan3DComponentViewer(component = comp)
                        }

                        if (comp.pinoutWarning != null) {
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, NeonRed, RoundedCornerShape(12.dp)),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2B0808))
                                ) {
                                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning", tint = NeonRed)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = comp.pinoutWarning,
                                            fontSize = 12.sp,
                                            color = Color(0xFFFFCDD2),
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // Absolute Max & Electrical Ratings
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "ABSOLUTE MAXIMUM RATINGS (Ta = 25°C)",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = NeonRed
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    comp.absoluteMaxRatings.forEach { (label, value) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = label, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                                            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = Color(0xFFFF8A80))
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "ELECTRICAL CHARACTERISTICS",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PhosphorCyan
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    comp.electricalRatings.forEach { r ->
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 6.dp)
                                                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                                .padding(8.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(text = r.parameter, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                                Text(text = r.symbol, fontSize = 12.sp, fontFamily = FontFamily.Monospace, color = PhosphorCyan)
                                            }
                                            Text(text = "Condition: ${r.testCondition}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f))
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                                Text(text = "Min: ${r.min}", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                                                Text(text = "Typ: ${r.typ} ${r.unit}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ElectricGreen)
                                                Text(text = "Max: ${r.max}", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        // Typical Circuit Diagrams
                        items(comp.typicalCircuits) { circ ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen.copy(alpha = 0.3f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = circ.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = ElectricGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = circ.description,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color.White.copy(alpha = 0.9f),
                                            lineHeight = 18.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Components Needed:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PhosphorCyan
                                    )
                                    circ.componentsNeeded.forEach { item ->
                                        Text(text = "• $item", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                    }

                                    if (circ.formulas.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        circ.formulas.forEach { f ->
                                            Surface(
                                                color = Color.Black.copy(alpha = 0.5f),
                                                shape = RoundedCornerShape(6.dp),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = f,
                                                    fontSize = 11.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                    color = TechAmber,
                                                    modifier = Modifier.padding(6.dp)
                                                )
                                            }
                                        }
                                    }

                                    if (circ.practicalNotes.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Practical Note: ${circ.practicalNotes}",
                                            fontSize = 11.sp,
                                            color = Color(0xFFFFD54F)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // Applications & Equivalents
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "COMMON PRACTICAL APPLICATIONS",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = ElectricGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    comp.commonApplications.forEach { app ->
                                        Row(
                                            modifier = Modifier.padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ElectricGreen))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = app, fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "EQUIVALENTS & REPLACEMENTS",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PhosphorCyan
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    comp.equivalents.forEach { eq ->
                                        Text(text = "• $eq", fontSize = 12.sp, fontFamily = FontFamily.Monospace, color = Color.White.copy(alpha = 0.8f))
                                    }
                                }
                            }
                        }
                    }

                    4 -> {
                        // Compare Action Screen
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "SULTAN PINOUT COMPARISON",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = ElectricGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Compare ${comp.partNumber} against alternatives (pinouts, voltage, current, pin-to-pin differences).",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { onNavigate("sa_compare") }
                                            .background(ElectricGreenDark)
                                            .padding(vertical = 12.dp),
                                        color = Color.Transparent
                                    ) {
                                        Text(
                                            text = "LAUNCH PINOUT COMPARISON TOOL",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            fontFamily = FontFamily.Monospace,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FanMotorConnectionsScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    val guides = SultanAdvancedData.fanMotorGuides

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Fan & Motor Wiring",
                subtitle = "Capacitor Connections & AC Safety Guide",
                onBackClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // High Voltage Danger Warning Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, NeonRed, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF280505))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Mains Warning",
                            tint = NeonRed,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "DANGER — 220V/240V MAINS VOLTAGE",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = NeonRed
                                )
                            )
                            Text(
                                text = "Always isolate circuit breaker before opening fan canopies or touching terminals. Always discharge motor run capacitors before touching leads.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFFFCDD2),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            items(guides) { guide ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, ElectricGreen.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = guide.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ElectricGreen
                                    )
                                )
                                Text(
                                    text = guide.subtitle,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PhosphorCyan,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ElectricGreenDark
                            ) {
                                Text(
                                    text = guide.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Visual Diagram Preview
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF040E0A)),
                            contentAlignment = Alignment.Center
                        ) {
                            RealisticComponentRenderer(
                                type = if (guide.category.contains("Table")) Component3DType.TABLE_FAN_MOTOR else Component3DType.CEILING_FAN_MOTOR,
                                label = guide.category,
                                modifier = Modifier.size(150.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Capacitor Rating: ${guide.capacitorRating}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = TechAmber
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "WIRE COLOR CODE & TERMINAL CONNECTIONS:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        guide.wires.forEach { wire ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(Color(wire.hexColor))
                                        .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "${wire.colorName} Wire: ${wire.terminalName}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Target: ${wire.connectionTarget}",
                                        fontSize = 10.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Step-by-Step Wiring Sequence:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricGreen
                        )
                        guide.stepByStepWiring.forEach { step ->
                            Text(
                                text = step,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Troubleshooting & Solutions:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PhosphorCyan
                        )
                        guide.troubleshooting.forEach { (symptom, fix) ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                    .padding(8.dp)
                            ) {
                                Text(text = "❌ Symptom: $symptom", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF8A80))
                                Text(text = "✓ Fix: $fix", fontSize = 11.sp, color = Color(0xFFA7FFEB))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PracticalConnectionsScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    val guides = SultanAdvancedData.practicalGuides

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Practical Wiring Guides",
                subtitle = "22+ Bench Circuits & Working Principles",
                onBackClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(guides) { guide ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, PhosphorCyan.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = guide.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PhosphorCyan
                                    )
                                )
                                Text(
                                    text = guide.subtitle,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 11.sp
                                    )
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PhosphorCyan.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = guide.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PhosphorCyan,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = guide.workingPrinciple,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Connections Step-by-Step:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricGreen
                        )
                        guide.stepByStepConnections.forEach { step ->
                            Text(
                                text = step,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        if (guide.keyFormulas.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            guide.keyFormulas.forEach { (name, formula) ->
                                Surface(
                                    color = Color.Black.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = name, fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                                        Text(text = formula, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = TechAmber)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SultanPinoutCompareScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    val all = SultanAdvancedData.allComponents
    var part1 by remember { mutableStateOf(all[0]) } // BC547
    var part2 by remember { mutableStateOf(if (all.size > 1) all[1] else all[0]) } // 2N2222

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Pinout Comparison Tool",
                subtitle = "Side-by-side component differentials",
                onBackClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Selectors Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "PART A", fontSize = 10.sp, color = ElectricGreen, fontWeight = FontWeight.Bold)
                            Text(text = part1.partNumber, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = Color.White))
                            Text(text = part1.packageType, fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f))
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PhosphorCyan)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "PART B", fontSize = 10.sp, color = PhosphorCyan, fontWeight = FontWeight.Bold)
                            Text(text = part2.partNumber, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = Color.White))
                            Text(text = part2.packageType, fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f))
                        }
                    }
                }
            }

            // Quick Swap Presets
            item {
                Text(text = "POPULAR COMPARISON PAIRS:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.8f))
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val pairs = listOf(
                        "BC547 vs 2N2222" to (0 to 1),
                        "IRFZ44N vs IRLZ44N" to (3 to 4)
                    )
                    items(pairs) { (label, idx) ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, ElectricGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                .clickable {
                                    if (idx.first < all.size) part1 = all[idx.first]
                                    if (idx.second < all.size) part2 = all[idx.second]
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            color = DarkSurfaceElevated
                        ) {
                            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ElectricGreen)
                        }
                    }
                }
            }

            // Visual 3D Side-by-Side
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF040E0A)),
                        contentAlignment = Alignment.Center
                    ) {
                        RealisticComponentRenderer(type = part1.threeDType, label = part1.partNumber, modifier = Modifier.size(110.dp))
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF040E0A)),
                        contentAlignment = Alignment.Center
                    ) {
                        RealisticComponentRenderer(type = part2.threeDType, label = part2.partNumber, modifier = Modifier.size(110.dp))
                    }
                }
            }

            // Pin Comparison Grid
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "PIN-BY-PIN CONFIGURATION:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PhosphorCyan)
                        Spacer(modifier = Modifier.height(10.dp))

                        val maxPins = maxOf(part1.pins.size, part2.pins.size)
                        for (i in 0 until maxPins) {
                            val pinA = part1.pins.getOrNull(i)
                            val pinB = part2.pins.getOrNull(i)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = pinA?.let { "Pin ${it.pinNumber}: ${it.pinName}" } ?: "—",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricGreen,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = pinB?.let { "Pin ${it.pinNumber}: ${it.pinName}" } ?: "—",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PhosphorCyan,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Sultan3DShowcaseScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val all = SultanAdvancedData.allComponents

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "3D Component Showcase",
                subtitle = "Laboratory-grade technical models",
                onBackClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(all) { comp ->
                Sultan3DComponentViewer(component = comp)
            }
        }
    }
}
