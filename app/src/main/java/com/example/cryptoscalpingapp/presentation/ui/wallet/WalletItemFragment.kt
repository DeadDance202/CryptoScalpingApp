package com.example.cryptoscalpingapp.presentation.ui.wallet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.database.local.WalletItem
import com.example.cryptoscalpingapp.databinding.FragmentWalletItemBinding
import com.example.cryptoscalpingapp.presentation.viewmodel.WalletItemViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletItemFragment : Fragment() {

//    private lateinit var viewModel: WalletItemViewModel
    private val viewModel: WalletItemViewModel by viewModels()
    private lateinit var binding: FragmentWalletItemBinding
    private lateinit var tilName: TextInputLayout
    private lateinit var tilAddress: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnSave: Button
    private var screenMode: String = MODE_UNKNOWN
    private var walletItemId: Int = WalletItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWalletItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this)[WalletItemViewModel::class.java]
        initViews(binding)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputAddress.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_address)
            } else {
                null
            }
            tilAddress.error = message
        }
        viewModel.invalidInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.invalid_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchModeEdit()
            MODE_ADD -> launchModeAdd()
        }
    }

    private fun addTextChangeListeners() {
        etAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputAddress()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInvalidInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchModeAdd() {
        btnSave.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addWalletItem(etName.text?.toString(), etAddress.text?.toString())
            }
        }
    }

    private fun launchModeEdit() {
        lifecycleScope.launch {
            viewModel.getWalletItem(walletItemId)
        }
        viewModel.walletItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etAddress.setText(it.address)
        }
        btnSave.setOnClickListener {
            lifecycleScope.launch {
                viewModel.editWalletItem(etName.text?.toString(), etAddress.text?.toString())
            }
        }

    }

    private fun initViews(binding: FragmentWalletItemBinding) {
        tilName = binding.tilWalletName
        tilAddress = binding.tilWalletAddress
        etName = binding.etWalletName
        etAddress = binding.etWalletAddress
        btnSave = binding.btnSaveWallet
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode: $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(WALLET_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            walletItemId = args.getInt(WALLET_ITEM_ID, WalletItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val WALLET_ITEM_ID = "extra_wallet_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): WalletItemFragment {
            return WalletItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): WalletItemFragment {
            return WalletItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(WALLET_ITEM_ID, shopItemId)
                }
            }
        }
    }
}