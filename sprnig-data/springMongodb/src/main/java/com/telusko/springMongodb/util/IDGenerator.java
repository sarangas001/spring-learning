package com.telusko.springMongodb.util;

import java.util.UUID;

public class IDGenerator
{
    public static String generateId()
    {
        return UUID.randomUUID().toString().replace("-", "").substring(0,10);
    }
}
