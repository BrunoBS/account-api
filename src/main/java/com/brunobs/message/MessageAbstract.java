package com.brunobs.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageAbstract {

    protected final MessageSource messageSource;

    public MessageAbstract(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }
}
