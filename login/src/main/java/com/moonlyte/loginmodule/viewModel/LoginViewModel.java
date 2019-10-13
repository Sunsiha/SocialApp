package com.moonlyte.loginmodule.viewModel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moonlyte.loginmodule.model.LoginUser;


public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
//        LoginUser loginUser = new LoginUser("s@gmail.com", "123456");
        userMutableLiveData.setValue(loginUser);

    }

}
