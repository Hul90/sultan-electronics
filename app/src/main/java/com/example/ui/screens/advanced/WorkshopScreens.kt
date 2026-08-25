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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HandbookData
import com.example.model.HandbookEntry
import com.example.model.StockItemEntity
import com.example.model.WorkbenchProjectEntity
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel

private val WorkshopGreen = Color(0xFF00E676)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SultanStockManagerScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    val stock by viewModel.stockItems.collectAsState()
    var query by remember { mutableStateOf("") }
    var showAdd by remember { mutableStateOf(false) }
    val filtered = stock.filter { it.partNumber.contains(query, true) || it.name.contains(query, true) || it.category.contains(query, true) || it.location.contains(query, true) }
    val lowStock = stock.count { it.quantity <= it.minimumQuantity }

    Scaffold(
        topBar = { TopAppBar(title = { Text("SULTAN COMPONENT STOCK") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }
    ) { p ->
        LazyColumn(Modifier.fillMaxSize().padding(p).padding(horizontal = 14.dp), contentPadding = PaddingValues(vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF071A13)), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Inventory2, null, tint = WorkshopGreen, modifier = Modifier.size(30.dp))
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Workshop Inventory", fontWeight = FontWeight.Bold, color = Color.White)
                                Text("${stock.size} items • $lowStock low-stock alerts", color = if (lowStock > 0) TechAmber else PhosphorCyan, fontSize = 12.sp)
                            }
                            Button(onClick = { showAdd = true }) { Icon(Icons.Default.Add, null); Spacer(Modifier.width(4.dp)); Text("ADD") }
                        }
                        Spacer(Modifier.height(10.dp))
                        OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth(), singleLine = true, label = { Text("Search parts, category, location") }, leadingIcon = { Icon(Icons.Default.Search, null) })
                    }
                }
            }
            if (filtered.isEmpty()) item { Text("No stock items found. Add your first component.", color = PhosphorCyan, modifier = Modifier.padding(12.dp)) }
            items(filtered, key = { it.id }) { item ->
                StockCard(item, onPlus = { viewModel.adjustStock(item.id, 1) }, onMinus = { if (item.quantity > 0) viewModel.adjustStock(item.id, -1) }, onDelete = { viewModel.deleteStockItem(item.id) })
            }
        }
    }
    if (showAdd) StockAddDialog(onDismiss = { showAdd = false }, onSave = { viewModel.saveStockItem(it); showAdd = false })
}

@Composable
private fun StockCard(item: StockItemEntity, onPlus: () -> Unit, onMinus: () -> Unit, onDelete: () -> Unit) {
    val low = item.quantity <= item.minimumQuantity
    Card(colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated), modifier = Modifier.fillMaxWidth().border(1.dp, if (low) TechAmber.copy(.65f) else WorkshopGreen.copy(.2f), RoundedCornerShape(12.dp))) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(item.partNumber, color = WorkshopGreen, fontWeight = FontWeight.Bold)
                    Text(item.name, color = Color.White)
                    Text("${item.category} • ${item.location.ifBlank { "No location" }}", color = PhosphorCyan, fontSize = 12.sp)
                }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null, tint = Color(0xFFFF5252)) }
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(if (low) "LOW STOCK" else "IN STOCK", color = if (low) TechAmber else WorkshopGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.weight(1f))
                IconButton(onClick = onMinus) { Icon(Icons.Default.Remove, null) }
                Text(item.quantity.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                IconButton(onClick = onPlus) { Icon(Icons.Default.Add, null, tint = WorkshopGreen) }
            }
            if (item.unitCost > 0) Text("Unit cost: ${"%.2f".format(item.unitCost)}", color = TechAmber, fontSize = 12.sp)
            if (item.notes.isNotBlank()) Text(item.notes, color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockAddDialog(onDismiss: () -> Unit, onSave: (StockItemEntity) -> Unit) {
    var part by remember { mutableStateOf("") }; var name by remember { mutableStateOf("") }; var category by remember { mutableStateOf("General") }
    var qty by remember { mutableStateOf("0") }; var min by remember { mutableStateOf("0") }; var location by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("0") }; var notes by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Add Component Stock") }, text = {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            item { OutlinedTextField(part, { part = it }, Modifier.fillMaxWidth(), label = { Text("Part number *") }, singleLine = true) }
            item { OutlinedTextField(name, { name = it }, Modifier.fillMaxWidth(), label = { Text("Name *") }, singleLine = true) }
            item { OutlinedTextField(category, { category = it }, Modifier.fillMaxWidth(), label = { Text("Category") }, singleLine = true) }
            item { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { OutlinedTextField(qty, { qty = it.filter(Char::isDigit) }, Modifier.weight(1f), label = { Text("Qty") }, singleLine = true); OutlinedTextField(min, { min = it.filter(Char::isDigit) }, Modifier.weight(1f), label = { Text("Min") }, singleLine = true) } }
            item { OutlinedTextField(location, { location = it }, Modifier.fillMaxWidth(), label = { Text("Location / Box") }, singleLine = true) }
            item { OutlinedTextField(cost, { cost = it.filter { c -> c.isDigit() || c == '.' } }, Modifier.fillMaxWidth(), label = { Text("Unit cost") }, singleLine = true) }
            item { OutlinedTextField(notes, { notes = it }, Modifier.fillMaxWidth(), label = { Text("Notes") }) }
        }
    }, confirmButton = { Button(enabled = part.isNotBlank() && name.isNotBlank(), onClick = { onSave(StockItemEntity(partNumber = part.trim(), name = name.trim(), category = category.trim().ifBlank { "General" }, quantity = qty.toIntOrNull() ?: 0, minimumQuantity = min.toIntOrNull() ?: 0, location = location.trim(), unitCost = cost.toDoubleOrNull() ?: 0.0, notes = notes.trim())) }) { Text("SAVE") } }, dismissButton = { TextButton(onClick = onDismiss) { Text("CANCEL") } })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SultanWorkbenchScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    val projects by viewModel.workbenchProjects.collectAsState()
    var selectedId by remember { mutableStateOf(0L) }
    var name by remember { mutableStateOf("New Repair Project") }
    var model by remember { mutableStateOf("") }
    var fault by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var measurements by remember { mutableStateOf("") }
    var parts by remember { mutableStateOf("") }

    fun load(p: WorkbenchProjectEntity) { selectedId = p.id; name = p.name; model = p.deviceModel; fault = p.faultDescription; notes = p.notes; measurements = p.measurementsJson.removePrefix("[").removeSuffix("]").replace("\\n", "\n"); parts = p.partsJson.removePrefix("[").removeSuffix("]").replace("\\n", "\n") }
    Scaffold(topBar = { TopAppBar(title = { Text("SULTAN WORKBENCH") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }, actions = { IconButton(onClick = { viewModel.saveWorkbenchProject(WorkbenchProjectEntity(id = selectedId, name = name.ifBlank { "Repair Project" }, deviceModel = model, faultDescription = fault, notes = notes, measurementsJson = "[${measurements.replace("\n", "\\n")} ]", partsJson = "[${parts.replace("\n", "\\n")} ]")) }) { Icon(Icons.Default.Save, null, tint = WorkshopGreen) } }) }) { p ->
        Row(Modifier.fillMaxSize().padding(p)) {
            LazyColumn(Modifier.weight(1f).padding(horizontal = 12.dp), contentPadding = PaddingValues(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                item { Text("Repair / Bench Workspace", color = WorkshopGreen, fontWeight = FontWeight.Bold, fontSize = 20.sp) }
                item { OutlinedTextField(name, { name = it }, Modifier.fillMaxWidth(), label = { Text("Project name") }, singleLine = true) }
                item { OutlinedTextField(model, { model = it }, Modifier.fillMaxWidth(), label = { Text("Device / model") }, singleLine = true) }
                item { OutlinedTextField(fault, { fault = it }, Modifier.fillMaxWidth(), label = { Text("Fault / complaint") }) }
                item { OutlinedTextField(measurements, { measurements = it }, Modifier.fillMaxWidth().height(140.dp), label = { Text("Measurements (one per line)") }) }
                item { OutlinedTextField(parts, { parts = it }, Modifier.fillMaxWidth().height(120.dp), label = { Text("Parts used / replaced (one per line)") }) }
                item { OutlinedTextField(notes, { notes = it }, Modifier.fillMaxWidth().height(150.dp), label = { Text("Repair notes / diagnosis") }) }
                item { Button(onClick = { viewModel.saveWorkbenchProject(WorkbenchProjectEntity(id = selectedId, name = name.ifBlank { "Repair Project" }, deviceModel = model, faultDescription = fault, notes = notes, measurementsJson = "[${measurements.replace("\n", "\\n")}]", partsJson = "[${parts.replace("\n", "\\n")}]")) }, Modifier.fillMaxWidth()) { Icon(Icons.Default.Save, null); Spacer(Modifier.width(6.dp)); Text("SAVE WORKBENCH PROJECT") } }
            }
            if (projects.isNotEmpty()) {
                Column(Modifier.width(190.dp).padding(8.dp)) {
                    Text("SAVED JOBS", color = PhosphorCyan, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) { items(projects, key = { it.id }) { project -> Card(Modifier.fillMaxWidth().clickable { load(project) }, colors = CardDefaults.cardColors(containerColor = if (project.id == selectedId) Color(0xFF12392B) else DarkSurfaceElevated)) { Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text(project.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp); Text(project.deviceModel, color = PhosphorCyan, fontSize = 10.sp) }; IconButton(onClick = { viewModel.deleteWorkbenchProject(project.id) }) { Icon(Icons.Default.Delete, null, tint = Color(0xFFFF5252)) } } } } }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SultanHandbookScreen(onBack: () -> Unit, entryId: String? = null) {
    var query by remember { mutableStateOf("") }
    var selected by remember(entryId) { mutableStateOf(HandbookData.all.firstOrNull { it.id == entryId }) }
    val list = HandbookData.all.filter { it.title.contains(query, true) || it.summary.contains(query, true) || it.category.contains(query, true) || it.content.contains(query, true) }
    Scaffold(topBar = { TopAppBar(title = { Text(if (selected == null) "SULTAN HANDBOOK" else selected!!.title) }, navigationIcon = { IconButton(onClick = { if (selected != null) selected = null else onBack() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }) }) { p ->
        if (selected != null) {
            HandbookDetail(selected!!, Modifier.fillMaxSize().padding(p).padding(16.dp))
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(p).padding(horizontal = 14.dp), contentPadding = PaddingValues(vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF071A13))) { Column(Modifier.padding(14.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.MenuBook, null, tint = WorkshopGreen, modifier = Modifier.size(30.dp)); Spacer(Modifier.width(10.dp)); Column { Text("SULTAN ELECTRONICS HANDBOOK", color = Color.White, fontWeight = FontWeight.Bold); Text("Offline bench reference • ${HandbookData.all.size} guides", color = PhosphorCyan, fontSize = 12.sp) } }; Spacer(Modifier.height(10.dp)); OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth(), singleLine = true, label = { Text("Search handbook") }, leadingIcon = { Icon(Icons.Default.Search, null) }) } } }
                items(list, key = { it.id }) { h -> Card(Modifier.fillMaxWidth().clickable { selected = h }, colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated)) { Column(Modifier.padding(12.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.WorkspacePremium, null, tint = TechAmber); Spacer(Modifier.width(8.dp)); Column { Text(h.title, color = Color.White, fontWeight = FontWeight.Bold); Text(h.category, color = WorkshopGreen, fontSize = 11.sp) } }; Text(h.summary, color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 5.dp)); Text("READ GUIDE →", color = PhosphorCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp)) } } }
            }
        }
    }
}

@Composable
private fun HandbookDetail(entry: HandbookEntry, modifier: Modifier) {
    Column(modifier) {
        Text(entry.category.uppercase(), color = WorkshopGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        Text(entry.summary, color = PhosphorCyan, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(14.dp))
        Card(colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated), modifier = Modifier.fillMaxWidth()) { Text(entry.content, color = Color.White, lineHeight = 23.sp, modifier = Modifier.padding(16.dp)) }
        Spacer(Modifier.height(12.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF241D08))) { Text("REFERENCE NOTE\nAlways verify critical ratings and pinouts against the exact manufacturer's datasheet. For mains work, disconnect power and use qualified procedures.", color = TechAmber, fontSize = 12.sp, modifier = Modifier.padding(14.dp)) }
    }
}
