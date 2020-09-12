package com.glance.guolindev.ui.data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.glance.guolindev.R
import com.glance.guolindev.logic.model.Resource

class DataActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this, DataViewModelFactory()).get(DataViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        val table = intent.getStringExtra(TABLE_NAME)
        if (table == null) {
            Toast.makeText(this, "Table name is null", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        viewModel.columnsLiveData.observe(this) {
            when (it.status) {
                Resource.SUCCESS -> {
                    val columns = it.data!!
                    for (column in columns) {
                        println("column ${column.name} type is ${column.type}")
                    }
                }
            }
        }
        viewModel.getColumnsInTable(table)
    }

    companion object {

        const val TABLE_NAME = "table_name"

        fun actionOpenTable(context: Context, tableName: String) {
            val intent = Intent(context, DataActivity::class.java)
            intent.putExtra(TABLE_NAME, tableName)
            context.startActivity(intent)
        }

    }

}