package com.mobproassesment2.actilog.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobproassesment2.actilog.R
import com.mobproassesment2.actilog.ui.theme.ActiLogTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val  KEY_ID_KEGIATAN ="idKegiatan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, id: Long? = null){
    val  viewModel: MainViewModel = viewModel()

    var judul by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf("") }

    val context = LocalContext.current
    val formatTanggal = stringResource(R.string.format_tanggal)

    fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(context, { _, year, month, dayOfMonth ->
            TimePickerDialog(context, { _, hour, minute ->
                calendar.set(year, month, dayOfMonth, hour, minute)
                val formatter = SimpleDateFormat(formatTanggal, Locale.getDefault())
                selectedDateTime = formatter.format(calendar.time)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getCatatan(id) ?: return@LaunchedEffect
        judul = data.judul
        catatan = data.catatan
        selectedDateTime = data.tanggal
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(R.string.tambah_actilogkegiatan))
                    else
                        Text(text = stringResource(id = R.string.edit_actilogkegiatan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary

                ),
                actions = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormCatatan(
            title = judul,
            onTitleChange = { judul = it },
            desc = catatan,
            onDescChange = { catatan = it },
            dateTime = selectedDateTime,
            onDateTimeChange = { selectedDateTime = it },
            onPickDateTime = { showDateTimePicker() },
            modifier = Modifier.padding(padding)
        )
    }
}
@Composable
fun FormCatatan(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDescChange: (String) -> Unit,
    dateTime: String, onDateTimeChange: (String) -> Unit,
    onPickDateTime: () -> Unit,
    modifier: Modifier
){
    val labelTanggal = stringResource(R.string.label_tanggal)
    val descTanggal = stringResource(R.string.desc_pilih_tanggal)

    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedTextField(
            value = title,
            onValueChange = {onTitleChange(it)},
            label = { Text(text = stringResource(R.string.title))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = desc,
            onValueChange = {onDescChange(it)},
            label = { Text(text = stringResource(R.string.isi_kegiatan))},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dateTime,
            onValueChange = {onDateTimeChange(it)},
            label = { Text(labelTanggal) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = onPickDateTime) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = descTanggal
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
   ActiLogTheme {
        DetailScreen(rememberNavController())
    }
}
