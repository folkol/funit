package com.folkol.test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FUnitRunnerTest {

    private FUnitRunner runner = new FUnitRunner();

    @org.junit.Test
    public void runTestsSuccess() throws Exception {
        PassingTest target = new PassingTest();

        List<FUnitRunner.TestResult> testResults = runner.runTests(target);

        assertEquals(3, testResults.size());
        assertEquals(3, testResults.stream().filter(FUnitRunner.TestResult::isPassed).count());
    }

    @org.junit.Test
    public void runTestsFail() throws Exception {
        FailingTest target = new FailingTest();

        List<FUnitRunner.TestResult> testResults = runner.runTests(target);

        assertEquals(3, testResults.size());
        assertEquals(1, testResults.stream().filter(FUnitRunner.TestResult::isPassed).count());
    }

    public static class PassingTest {
        @com.folkol.test.Test
        public void test() {
        }

        @com.folkol.test.Test
        public void test2() {
        }

        @com.folkol.test.Test
        public void test3() {
        }
    }

    public static class FailingTest {
        @com.folkol.test.Test
        public void test() {
            int x = 1 / 0;
        }

        @com.folkol.test.Test
        public void test2() {
            throw new RuntimeException("Oh noes!");
        }

        @com.folkol.test.Test
        public void test3() {
        }
    }
}
