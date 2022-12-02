package com.pherom.operation;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.OptionalInt;
import java.util.function.Function;

public enum Operation {
    ADDITION("Plus", 2, 2, (args) -> OptionalInt.of(args[0] + args[1])),
    SUBTRACTION("Minus", 2, 2, (args) -> OptionalInt.of(args[0] - args[1])),
    MULTIPLICATION("Times", 2, 2, (args) -> OptionalInt.of(args[0] * args[1])),
    DIVISION("Divide", 2, 2, (args) -> args[1] != 0 ? OptionalInt.of(args[0] / args[1]) : OptionalInt.empty()),
    EXPONENT("Pow", 2, 2, (args) -> OptionalInt.of((int)Math.pow(args[0], args[1]))),
    ABSOLUTE_VALUE("Abs", 1, 1, (args) -> OptionalInt.of(Math.abs(args[0]))),
    FACTORIAL("Fact", 1, 1, (args) -> OptionalInt.of((int) CombinatoricsUtils.factorial(args[0])));

    private final String capitalizedName;
    private final int minArgs;
    private final int maxArgs;
    private final Function<int[], OptionalInt> function;

    Operation(String uppercaseName, int minArgs, int maxArgs, Function<int[], OptionalInt> method) {
        this.capitalizedName = uppercaseName;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.function = method;
    }

    public String getCapitalizedName() {
        return capitalizedName;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public OptionalInt calculate(int[] arguments) {
        return function.apply(arguments);
    }
}
