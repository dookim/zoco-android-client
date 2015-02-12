package com.zoco.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.zoco.obj.User;

import java.lang.reflect.Type;

public class ZocoConstants {
	public static final String NAVER_SEARCH_URL = "http://openapi.naver.com/search?key=e8136cb7ee6fa4510e24cd8a81cf056e&query=all&display=10&start=1&target=book_adv&d_isbn=";
    public static User user;



	
}
