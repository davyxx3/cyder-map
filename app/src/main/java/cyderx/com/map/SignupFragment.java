package cyderx.com.map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import cyderx.com.map.component.SignupValidator;
import cyderx.com.map.data.User;
import cyderx.com.map.data.UserMapper;
import cyderx.com.map.utils.ProvinceCityUtil;

public class SignupFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 元素组件
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText = view.findViewById(R.id.username_edit_text);
        final TextInputLayout confirmPasswordTextInput = view.findViewById(R.id.confirm_password_text_input);
        final TextInputEditText confirmPasswordEditText = view.findViewById(R.id.confirm_password_edit_text);
        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);
        final TextInputLayout phoneTextInput = view.findViewById(R.id.phone_text_input);
        final TextInputEditText phoneEditText = view.findViewById(R.id.phone_edit_text);
        final TextInputEditText birthdayEditText = view.findViewById(R.id.birthday_edit_text);
        TextInputLayout cityDropdownLayout = view.findViewById(R.id.city_dropdown_layout);
        final TextInputEditText interestsEditText = view.findViewById(R.id.interest_edit_text);
        final TextInputLayout commentTextInput = view.findViewById(R.id.comment_text_input);
        final TextInputEditText commentEditText = view.findViewById(R.id.comment_edit_text);
        final MaterialButton nextButton = view.findViewById(R.id.next_button);
        final MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        final Button interestButton = view.findViewById(R.id.interest_button);
        final Button birthdayButton = view.findViewById(R.id.birthday_button);
        final RadioButton genderMale = view.findViewById(R.id.gender_male);
        final AutoCompleteTextView provinceDropDown = view.findViewById(R.id.province_dropdown_menu);
        final AutoCompleteTextView cityDropDown = view.findViewById(R.id.city_dropdown_menu);

        // 注册按钮
        nextButton.setOnClickListener(view12 -> {
            // 用户名验证
            if (!SignupValidator.checkUsername(usernameEditText.getText(), ((MapApplication) getActivity().getApplication()).getDb())) {
                usernameTextInput.setError(getString(R.string.error_username));
                return;
            } else {
                usernameTextInput.setError(null);
            }

            // 密码验证
            if (!SignupValidator.checkPassword(passwordEditText.getText())) {
                passwordTextInput.setError(getString(R.string.error_password));
                return;
            } else {
                passwordTextInput.setError(null);
            }

            // 确认密码验证
            if (!SignupValidator.checkConfirmPassword(confirmPasswordEditText.getText(), passwordEditText.getText())) {
                passwordTextInput.setError(getString(R.string.error_password));
                return;
            } else {
                passwordTextInput.setError(null);
            }

            // 手机验证
            if (!SignupValidator.checkPhone(phoneEditText.getText())) {
                phoneTextInput.setError("手机格式错误");
                return;
            } else {
                phoneTextInput.setError(null);
            }

            // 邮箱验证
            if (!SignupValidator.checkEmail(emailEditText.getText())) {
                emailTextInput.setError("邮箱格式错误");
                return;
            } else {
                emailTextInput.setError(null);
            }

            // 自我评价验证
            if (!SignupValidator.checkComment(commentEditText.getText())) {
                commentTextInput.setError("自我评价太长啦~");
                return;
            } else {
                commentTextInput.setError(null);
            }

            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("注册确认")
                    .setMessage("请确定你的个人信息无误哦~")
                    .setPositiveButton("我已确认", (dialogInterface, i) -> {
                        User user = new User();
                        user.setFullName(((TextInputEditText) view.findViewById(R.id.full_name_edit_text)).getText().toString());
                        user.setUsername(usernameEditText.getText().toString());
                        user.setPassword(passwordEditText.getText().toString());
                        if (genderMale.isChecked()) {
                            user.setGender("男");
                        } else {
                            user.setGender("女");
                        }
                        user.setPhone(phoneEditText.getText().toString());
                        user.setEmail(emailEditText.getText().toString());
                        user.setProvince(provinceDropDown.getText().toString());
                        user.setCity(cityDropDown.getText().toString());
                        user.setBirthday(birthdayEditText.getText().toString());
                        user.setInterests(interestsEditText.getText().toString());
                        user.setComment(commentEditText.getText().toString());
                        UserMapper.addUser(((MapApplication) getActivity().getApplication()).getDb(), user);
                        getActivity().finish();
                    })
                    .setNegativeButton("我再改改", (dialogInterface, i) -> {

                    })
                    .show();
        });

        // 取消按钮
        cancelButton.setOnClickListener((view1) -> getActivity().finish());

        // 用户名验证
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(usernameEditText.getText())) {
                    usernameTextInput.setError(null);
                    return;
                }

                if (!SignupValidator.checkUsername(usernameEditText.getText(), ((MapApplication) getActivity().getApplication()).getDb())) {
                    usernameTextInput.setError(getString(R.string.error_username));
                } else {
                    usernameTextInput.setError(null);
                }
            }
        });

        // 密码验证
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(passwordEditText.getText())) {
                    passwordTextInput.setError(null);
                    return;
                }

                if (!SignupValidator.checkPassword(passwordEditText.getText())) {
                    passwordTextInput.setError(getText(R.string.error_password));
                } else {
                    passwordTextInput.setError(null);
                }
            }
        });

        // 确认密码验证
        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(confirmPasswordEditText.getText())) {
                    confirmPasswordTextInput.setError(null);
                    return;
                }

                if (!SignupValidator.checkConfirmPassword(confirmPasswordEditText.getText(), passwordEditText.getText())) {
                    confirmPasswordTextInput.setError("两次输入的密码不一致");
                } else {
                    confirmPasswordTextInput.setError(null);
                }
            }
        });

        // 手机验证
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(phoneEditText.getText())) {
                    phoneTextInput.setError(null);
                    return;
                }

                if (!SignupValidator.checkPhone(phoneEditText.getText())) {
                    phoneTextInput.setError("手机格式错误");
                } else {
                    phoneTextInput.setError(null);
                }
            }
        });

        // 邮箱验证
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(emailEditText.getText())) {
                    emailTextInput.setError(null);
                    return;
                }

                if (!SignupValidator.checkEmail(emailEditText.getText())) {
                    emailTextInput.setError("邮箱格式错误");
                } else {
                    emailTextInput.setError(null);
                }
            }
        });

        // 省份下拉列表
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getActivity(), R.layout.province_item, ProvinceCityUtil.PROVINCE_ARRAY);
        provinceDropDown.setAdapter(provinceAdapter);
        provinceDropDown.setOnItemClickListener((parent, innerView, position, id) -> {
            // 城市下拉列表
            cityDropDown.setText(null);
            cityDropdownLayout.setVisibility(View.GONE);
            if (id > 5) {
                cityDropdownLayout.setVisibility(View.VISIBLE);
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getActivity(), R.layout.city_item, ProvinceCityUtil.CITY_ARRAY[(int) id]);
                cityDropDown.setAdapter(cityAdapter);
            }
        });

        // 设置生日
        birthdayButton.setOnClickListener(view1 -> {
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
            datePickerBuilder.setTitleText("选择生日");
            // 设置时间约束
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
            constraintsBuilder.setValidator(DateValidatorPointBackward.now());
            datePickerBuilder.setCalendarConstraints(constraintsBuilder.build());
            // 构建datePicker对象
            MaterialDatePicker<Long> datePicker = datePickerBuilder.build();
            datePicker.addOnPositiveButtonClickListener(selection -> birthdayEditText.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(selection))));
            datePicker.show(getActivity().getSupportFragmentManager(), "Date");
        });

        // 设置兴趣
        String[] interests = {"篮球", "唱跳", "Rap"};
        boolean[] selected = new boolean[interests.length];
        ArrayList<String> selectedList = new ArrayList<>();
        interestButton.setOnClickListener(view12 -> new MaterialAlertDialogBuilder(getActivity())
                .setTitle("选择兴趣")
                .setMultiChoiceItems(interests, selected, (dialog, which, isChecked) -> selected[which] = isChecked)
                .setPositiveButton("确定", (dialogInterface, btn) -> {
                    for (int i = 0; i < interests.length; i++) {
                        if (selected[i]) {
                            selectedList.add(interests[i]);
                        }
                    }
                    String[] selectedInterestArray = Arrays.copyOf(selectedList.toArray(), selectedList.size(), String[].class);
                    interestsEditText.setText(String.join(",", selectedInterestArray));
                    selectedList.clear();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> {

                })
                .show());

        // 自我评价验证
        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(commentEditText.getText())) {
                    commentTextInput.setError(null);
                    return;
                }
                if (!SignupValidator.checkComment(commentEditText.getText())) {
                    commentTextInput.setError("自我评价太长啦~");
                } else {
                    commentTextInput.setError(null);
                }
            }
        });

        return view;
    }
}
