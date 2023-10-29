package com.tugaypamuk.app.onestask.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Locale


// yyyy-MM-dd'T'HH:mm:ss seklinde gelen date degerinin gun ay yil saat formatina convert edilmesi
// binding adapter kullanarak xml icerisinde kullanilmistir.
@BindingAdapter("android:convertDate")
fun TextView.convertDate(dateString : String){
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    val finalValue = date?.let { outputFormat.format(it) }
    this.text = "Log Time : $finalValue"
}