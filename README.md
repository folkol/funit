FUnit!
===

FUnit is a simple — or naive, rather — _JUnitesque_ test runner.

This recreational reflection hack will invoke all methods annotated with `@com.folkol.test.Test`.

- Methods that throw up are considered to have FAILED
- Methods that do not are considered to have PASSED.
