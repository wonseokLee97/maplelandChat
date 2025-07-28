package com.mapleland.chatservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class DotenvTestListener implements TestExecutionListener {
    @Override
    public void beforeTestClass(TestContext testContext) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
