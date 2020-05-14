package com.example.goodfood.ui.user


import androidx.lifecycle.ViewModel
import com.example.goodfood.data.repositorys.account.AccountRepository
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val repository: AccountRepository
):ViewModel() {

}