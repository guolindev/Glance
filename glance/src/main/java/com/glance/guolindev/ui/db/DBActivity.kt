package com.glance.guolindev.ui.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.glance.guolindev.R
import com.glance.guolindev.logic.model.DBFile
import kotlinx.android.synthetic.main.activity_db.*

class DBActivity : AppCompatActivity() {

    private val dbViewModel by lazy { ViewModelProvider(this).get(DBViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        val dbList = ArrayList<DBFile>()
        val adapter = DBAdapter(dbList)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        dbViewModel.dbFileLiveData.observe(this) {
            dbList.add(it)
            adapter.notifyItemInserted(adapter.itemCount)
        }
        dbViewModel.scanAllDBFiles()
    }

}