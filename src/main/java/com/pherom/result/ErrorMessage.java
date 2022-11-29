package com.pherom.result;

public enum ErrorMessage {

    NO_SUCH_OPERATION("Error: unknown operation: %s"),
    NOT_ENOUGH_ARGUMENTS("Error: Not enough arguments to perform the operation %s"),
    TOO_MANY_ARGUMENTS("Error: Too many arguments to perform the operation %s"),
    DIVISION_BY_ZERO("Error while performing operation Divide: division by 0"),
    FACTORIAL_OF_NEGATIVE_NUMBER("Error while performing operation Factorial: not supported for the negative number");
    private final String format;

    ErrorMessage(String format) {
        this.format = format;
    };

    public String getFormat() {
        return format;
    }
}
