package cyderx.com.map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import cyderx.com.map.component.LoginValidator;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // 元素对象
        final TextInputLayout passwordTextInput = view.findViewById(R.id.login_password_layout);
        final TextInputEditText passwordEditText = view.findViewById(R.id.login_password_text);
        final TextInputEditText usernameEditText = view.findViewById(R.id.login_username_text);
        final CheckBox rememberUsername = view.findViewById(R.id.remember_username);
        final CheckBox rememberPassword = view.findViewById(R.id.remember_password);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        MaterialButton signupButton = view.findViewById(R.id.signup_button);

        // 填入之前的账号和密码
        SharedPreferences sp = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        if (sp.getString("lastLoginUsername", null) != null) {
            usernameEditText.setText(sp.getString("lastLoginUsername", null));
            rememberUsername.setChecked(true);
        }
        if (sp.getString("lastLoginPassword", null) != null) {
            passwordEditText.setText(sp.getString("lastLoginPassword", null));
            rememberPassword.setChecked(true);
        }

        // 当清空输入时，消除错误提示
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordTextInput.setError(null);
            }
        });

        // 当清空输入时，消除错误提示
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordTextInput.setError(null);
            }
        });

        // 勾选记住密码时，必须也勾选上记住用户名
        rememberPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rememberUsername.setChecked(true);
            }
        });

        // 取消勾选记住用户名时，把记住密码也取消勾选
        rememberUsername.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                rememberPassword.setChecked(false);
            }
        });

        // 登陆时，检查用户名和密码是否正确
        nextButton.setOnClickListener(view1 -> {
            if (!LoginValidator.checkUser(usernameEditText.getText(), passwordEditText.getText(), ((MapApplication) getActivity().getApplication()).getDb(), (MapApplication) (getActivity().getApplication()))) {
                passwordTextInput.setError("密码错误");
            } else {
                passwordTextInput.setError(null);
                sp.edit().putString("lastLoginUsername", null).apply();
                sp.edit().putString("lastLoginPassword", null).apply();
                if (rememberUsername.isChecked()) {
                    String username = usernameEditText.getText().toString();
                    sp.edit().putString("lastLoginUsername", username).apply();
                }
                if (rememberPassword.isChecked()) {
                    String password = passwordEditText.getText().toString();
                    sp.edit().putString("lastLoginPassword", password).apply();
                }
                startActivity(new Intent(getActivity().getApplicationContext(), MapActivity.class));
                getActivity().finish();
            }
        });

        // 点击注册按钮，跳转至注册页面
        signupButton.setOnClickListener(view1 -> startActivity(new Intent(getActivity().getApplicationContext(), SignupActivity.class)));
        return view;
    }
}
