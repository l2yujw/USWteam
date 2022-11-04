package com.akj.helpyou.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM. dd.");
    Date date = new Date();
    String set = simpleDateFormat.format(date);
}