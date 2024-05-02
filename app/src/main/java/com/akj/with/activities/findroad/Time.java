package com.akj.with.activities.findroad;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM. dd.");
    Date date = new Date();
    public String set = simpleDateFormat.format(date);
}