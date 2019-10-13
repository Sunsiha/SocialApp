package com.moonlyte.loginmodule.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moonlyte.loginmodule.R;
import com.moonlyte.loginmodule.databinding.ActivityLoginBinding;
import com.moonlyte.loginmodule.model.LoginUser;
import com.moonlyte.loginmodule.viewModel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        activityLoginBinding.setLifecycleOwner(this);

        activityLoginBinding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {

                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                    activityLoginBinding.txtEmailAddress.setError("Enter an E-Mail Address");
                    activityLoginBinding.txtEmailAddress.requestFocus();
                } else if (!loginUser.isEmailValid()) {
                    activityLoginBinding.txtEmailAddress.setError("Enter a Valid E-mail Address");
                    activityLoginBinding.txtEmailAddress.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                    activityLoginBinding.txtPassword.setError("Enter a Password");
                    activityLoginBinding.txtPassword.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    activityLoginBinding.txtPassword.setError("Enter at least 6 Digit password");
                    activityLoginBinding.txtPassword.requestFocus();
                } else {
                    activityLoginBinding.lblEmailAnswer.setText(loginUser.getStrEmailAddress());
                    activityLoginBinding.lblPasswordAnswer.setText(loginUser.getStrPassword());

                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }
}
