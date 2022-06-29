package com.example.gardenerassistant.presentation.screens

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun datePicker(block: (String) -> Unit): DatePickerDialog {
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
        LocalContext.current,
        { _, year, month, day ->
            block(LocalDate.of(year, month, day).toString())
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun imagePicker(callback: (Context, Uri) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        callback(context, uri)
    }
}