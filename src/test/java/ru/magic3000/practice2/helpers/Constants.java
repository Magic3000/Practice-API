package ru.magic3000.practice2.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.util.Random;

public class Constants {
    public static final Random rnd = new Random();
    public static final Faker faker = new Faker();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final String TEST_TITLE = "Test title";
    public static final String TEST_INFO = "Test info";
}
