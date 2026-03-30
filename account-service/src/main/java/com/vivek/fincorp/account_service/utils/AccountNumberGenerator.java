package com.vivek.fincorp.account_service.utils;

import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {
    public String generate() {
        String year = String.valueOf(Year.now().getValue());
        int random = ThreadLocalRandom.current().nextInt(100000, 999999);
        return "ACC" + year + random;
    }
}
