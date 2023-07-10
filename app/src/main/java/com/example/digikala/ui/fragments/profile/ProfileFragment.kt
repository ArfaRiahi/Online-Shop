package com.example.digikala.ui.fragments.profile

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.digikala.R
import com.example.digikala.databinding.FragmentProfileBinding
import com.example.digikala.util.Const.ORDERS_ID
import com.example.digikala.util.Const.STATUS
import com.example.digikala.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        navController = findNavController()
        setUi()
    }

    private fun setUi() {
        btnClickListener()
        setImageAsGallery()
    }

    private fun btnClickListener() {
        binding.btnSave.setOnClickListener {
            STATUS = false
            if (!viewModel.checkCustomerEntries()) {
                Toast.makeText(
                    requireContext(),
                    "لطفا تمام اطلاعات رو کامل کنید",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                STATUS = true
                viewModel.getCustomersByEmail()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.getEmailCustomer.observe(viewLifecycleOwner) {
                            when (it) {
                                is Resources.Error -> {
                                    errorToast()
                                }

                                is Resources.Loading -> loadingVisibility()

                                is Resources.Success -> {
                                    succeedVisibility()
                                    if (it.data?.isEmpty() == true) {
                                        viewModel.createCustomer()
                                    } else {
                                        viewModel.getOrdersByEmail()
                                        getOrders()
                                    }
                                }


                            }
                        }
                    }
                }
            }
        }
    }

    private fun getOrders() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchByEmail.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resources.Error -> {
                            errorToast()
                        }

                        is Resources.Loading -> {
                            loadingVisibility()
                        }

                        is Resources.Success -> {
                            succeedVisibility()
                            val o = it.data
                            if (it.data?.isEmpty() == true) {
                                viewModel.createOrders()
                            } else {
                                ORDERS_ID = it.data?.get(0)?.id!!
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val imageUri = data?.data
            Glide.with(binding.root)
                .load(imageUri)
                .into(binding.profileIv)
        }
    }

    private fun setImageAsGallery() {
        binding.profileIv.setOnClickListener { setImage() }
    }

    private fun succeedVisibility() {
        binding.layoutBackground.visibility = View.VISIBLE
        binding.progressProfile.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), "ثبت نام با موفقیت انجام شد", Toast.LENGTH_LONG).show()
    }

    private fun loadingVisibility() {
        binding.layoutBackground.visibility = View.INVISIBLE
        binding.progressProfile.visibility = View.VISIBLE
    }

    private fun errorToast() {
        binding.layoutBackground.visibility = View.VISIBLE
        binding.progressProfile.visibility = View.INVISIBLE
        Toast.makeText(
            requireContext(),
            "اطلاعات تکراریست، لطفا اطلاعات جدید وارد کنید",
            Toast.LENGTH_LONG
        ).show()
    }
}