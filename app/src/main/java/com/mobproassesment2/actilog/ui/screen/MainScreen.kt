package com.mobproassesment2.actilog.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mobproassesment2.actilog.R
import com.mobproassesment2.actilog.model.Kegiatan
import com.mobproassesment2.actilog.navigation.Screen
import com.mobproassesment2.actilog.ui.theme.ActiLogTheme
import com.mobproassesment2.actilog.util.SettingsDataStore
import com.mobproassesment2.actilog.util.ViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = remember { SettingsDataStore(context) }
    val layoutFlow by dataStore.layoutFlow.collectAsState(initial = true)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        scope.launch { dataStore.saveLayout(!layoutFlow) }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (layoutFlow) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (layoutFlow) R.string.grid else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Screen.TempatKegiatanDibuang.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_restore_from_trash_24),
                            contentDescription = stringResource(R.string.tempat_sampah),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.FormBaru.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.tambah_actilogkegiatan),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        ScreenContent(
            showList = layoutFlow,
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(factory = ViewModelFactory(context))
    val detailViewModel: DetailViewModel = viewModel(factory = ViewModelFactory(context))
    val data by viewModel.data.collectAsState()
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    val selectedKegiatan = remember { mutableStateOf<Kegiatan?>(null) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(stringResource(R.string.konfirmasi_hapus)) },
            text = { Text(stringResource(R.string.yakin_hapus)) },
            confirmButton = {
                TextButton(onClick = {
                    selectedKegiatan.value?.let { kegiatan ->
                        detailViewModel.recentlyDeletedKegiatan = kegiatan
                        detailViewModel.delete(kegiatan.id)
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = context.getString(R.string.data_dihapus),
                                actionLabel = context.getString(R.string.undo)
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                detailViewModel.restoreDeletedKegiatan()
                            }
                        }
                    }
                    openDialog.value = false
                }) {
                    Text(stringResource(android.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                    Text(stringResource(android.R.string.cancel))
                }
            }
        )
    }

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.list_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) { kegiatan ->
                    ListItem(
                        kegiatan = kegiatan,
                        onClick = { navController.navigate(Screen.FormUbah.withId(kegiatan.id)) },
                        onDelete = {
                            selectedKegiatan.value = it
                            openDialog.value = true
                        }
                    )
                    HorizontalDivider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) { kegiatan ->
                    GridItem(
                        kegiatan = kegiatan,
                        onClick = { navController.navigate(Screen.FormUbah.withId(kegiatan.id)) },
                        onDelete = {
                            selectedKegiatan.value = it
                            openDialog.value = true
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun ListItem(kegiatan: Kegiatan, onClick: () -> Unit, onDelete: (Kegiatan) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
            .heightIn(min = 120.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = kegiatan.judul,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = kegiatan.catatan,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = kegiatan.tanggal,
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = { onDelete(kegiatan) }) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus")
        }
    }
}

@Composable
fun GridItem(kegiatan: Kegiatan, onClick: () -> Unit, onDelete: (Kegiatan) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() }
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, DividerDefaults.color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = kegiatan.judul,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = kegiatan.catatan,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = kegiatan.tanggal,
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDelete(kegiatan) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    ActiLogTheme {
        MainScreen(rememberNavController())
    }
}
