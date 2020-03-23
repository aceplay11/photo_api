package com.example.hsexercise.feature

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hsexercise.R
import com.example.hsexercise.common.BaseActivity
import com.example.hsexercise.feature.database.FeatureModel
import kotlinx.android.synthetic.main.activity_feature.*

class FeatureActivity : BaseActivity<FeatureViewModel>() {
    override val viewModelClass = FeatureViewModel::class.java
    override val layoutResId = R.layout.activity_feature
    lateinit var photoList: List<FeatureModel>
    lateinit var photoAdapter: PhotoAdapter

    override fun provideViewModelFactory() = FeatureViewModel.Factory()

    override fun onViewLoad(savedInstanceState: Bundle?) {
        setupViewModel()
        setupView()

    }

    private fun setupViewModel(){
        viewModel.getData()
        viewModel.getPhotosResults().observe(this, Observer {
            photoList = it
            photoAdapter.notifyDataSetChanged()
        } )

        viewModel.getLoading().observe(this, Observer {
            photoAdapter.visibleInt = it
        })

        viewModel.getErrorMessage().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }

    private fun setupView(){
        if (photoAdapter == null){
            photoAdapter = PhotoAdapter(photoList)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this,
        LinearLayoutManager.VERTICAL))
        recyclerView.adapter = photoAdapter
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
    }
}
