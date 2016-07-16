package com.folkol.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FUnitRunner {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName(args[0]);
        Object target = clazz.newInstance();
        FUnitRunner runner = new FUnitRunner();
        List<TestResult> results = runner.runTests(target);

        if (results.isEmpty()) {
            System.out.println("No test methods were found.");
        } else if (results.stream().allMatch(x -> x.passed)) {
            System.out.printf("All tests passed!");
        } else {
            System.out.println("Test results:");
            for (TestResult result : results) {
                if (result.isPassed()) {
                    System.out.println("PASS: " + result.getMethod().getName());
                } else {
                    System.out.println("FAIL: " + result.getMethod().getName());
                    result.getCause().printStackTrace(System.out);
                }
            }
        }
    }

    public List<TestResult> runTests(Object target) throws Exception {
        List<TestResult> results = new ArrayList<>();

        for (Method method : target.getClass().getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                boolean success;
                Throwable cause = null;
                try {
                    method.invoke(target);
                    success = true;
                } catch (Throwable throwable) {
                    cause = throwable;
                    success = false;
                }
                results.add(new TestResult(method, success, cause));
            }
        }

        return results;
    }

    public static class TestResult {
        public final Method method;

        public Method getMethod() {
            return method;
        }

        public boolean isPassed() {
            return passed;
        }

        public Throwable getCause() {
            return cause;
        }

        public final boolean passed;
        public final Throwable cause;

        public TestResult(Method method, boolean passed, Throwable cause) {
            this.method = method;
            this.passed = passed;
            this.cause = cause;
        }
    }
}
