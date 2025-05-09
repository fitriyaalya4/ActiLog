package com.mobproassesment2.actilog.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobproassesment2.actilog.R
import com.mobproassesment2.actilog.model.Kegiatan
import com.mobproassesment2.actilog.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: RecycleBinViewModel = viewModel(factory = factory)
    val deletedData by viewModel.deletedData.collectAsState()
    var showEmptyConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.recycle_bin_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    if (deletedData.isNotEmpty()) {
                        IconButton(onClick = { showEmptyConfirmDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.recycle_bin_empty_confirmation)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (deletedData.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.recycle_bin_empty_message))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(deletedData) { kegiatan ->
                    RecycleBinItem(
                        kegiatan = kegiatan,
                        onRestore = { viewModel.restoreKegiatan(kegiatan.id) },
                        onDelete = { viewModel.permanentDeleteKegiatan(kegiatan.id) }
                    )
                    HorizontalDivider()
                }
            }
        }

        if (showEmptyConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showEmptyConfirmDialog = false },
                title = { Text(stringResource(R.string.recycle_bin_title)) },
                text = { Text(stringResource(R.string.recycle_bin_empty_confirmation_message)) },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.emptyRecycleBin()
                        showEmptyConfirmDialog = false
                    }) {
                        Text(
                            stringResource(R.string.recycle_bin_empty_confirmation),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEmptyConfirmDialog = false }) {
                        Text(stringResource(R.string.recycle_bin_empty_cancel))
                    }
                }
            )
        }
    }
}
@Composable
fun RecycleBinItem(
    kegiatan: Kegiatan,
    onRestore: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 120.dp), // Consistent height with MainScreen
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = kegiatan.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = kegiatan.catatan,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = kegiatan.tanggal,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row {
            IconButton(onClick = onRestore) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.recycle_bin_restore),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.recycle_bin_delete_permanent),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.recycle_bin_delete_permanent)) },
            text = { Text(stringResource(R.string.recycle_bin_delete_confirmation_message)) },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text(stringResource(R.string.recycle_bin_delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.recycle_bin_cancel))
                }
            }
        )
    }
}
