package com.urvesh.android_arch_mvp.domain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private Pattern mPattern;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        mPattern = Pattern.compile(EMAIL_PATTERN);
    }

    public static EmailValidator getInstance() {
        return new EmailValidator();
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        Matcher mMatcher = mPattern.matcher(hex);
        return mMatcher.matches();

    }
}
