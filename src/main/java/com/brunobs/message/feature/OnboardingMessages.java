package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class OnboardingMessages extends MessageAbstract {

    private final String NOT_FOUND = "onboarding.not.found";

    public OnboardingMessages(MessageSource messageSource) {
        super(messageSource);
    }


    public String notFound() {
        return getMessage(NOT_FOUND);
    }
}