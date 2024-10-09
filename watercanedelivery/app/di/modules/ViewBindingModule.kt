package com.watercanedeilvery.di.modules


import com.watercanedelivery.app.BaseActivity
import com.watercanedelivery.app.BaseFragment
import com.watercanedelivery.app.ui.activity.*
import com.watercanedelivery.app.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Karthikeyan on 27-01-2021.
 */
@Module
abstract class ViewBindingModule {
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun loginActivityBinding(): LoginActivity

    @ContributesAndroidInjector
    abstract fun mainActivityBinding(): MainActivity


    @ContributesAndroidInjector
    abstract fun baseActivity(): BaseActivity

    @ContributesAndroidInjector
    abstract fun customerListFragment(): CustomerListFragment

    @ContributesAndroidInjector
    abstract fun expensesListFragment(): ExpenseListFragment

    @ContributesAndroidInjector
    abstract fun baseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun addExpenseFragment(): AddNewExpenseFragment


    @ContributesAndroidInjector
    abstract fun vechicleListFragment(): VechicleAllowanceFragment

    @ContributesAndroidInjector
    abstract fun customerViewFragment(): CustomerViewFragment

    @ContributesAndroidInjector
    abstract fun vechAddAllow(): VechicleAllowanceAddFragment

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun otpRecievedActivity(): OTPRecievedActivity

    @ContributesAndroidInjector
    abstract fun forgotPasswordActivity(): ForgotPasswordActivity

    @ContributesAndroidInjector
    abstract fun changePassword(): ChangePasswordActivity
}