package me.cloudcat.develop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtils {

	static MessageSource messageSource;

	@Autowired
	@Qualifier("messageSource")
	public void setMessageSource(MessageSource messageSource) {
		PropertyUtils.messageSource = messageSource;
	}

	public static String getString(String key) {
		return messageSource.getMessage(key, null,
				LocaleContextHolder.getLocale());
	}
	
	public static String getString(String key, String...value){
		return messageSource.getMessage(key,value, LocaleContextHolder.getLocale());
	}
}
