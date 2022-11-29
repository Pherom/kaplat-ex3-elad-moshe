package com.pherom.controller.independent;

import com.pherom.operation.Operation;
import com.pherom.request.independent.IndependentCalculationRequest;
import com.pherom.result.ErrorMessage;
import com.pherom.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
public class IndependentCalculationController {

    public static final String ENDPOINT = "/independent";

    @PostMapping(path = ENDPOINT + "/" + "calculate")
    @ResponseBody
    public Result calculate(@RequestBody IndependentCalculationRequest calculationRequest) {
        Result result;
        Optional<Operation> operation = Arrays.stream(Operation.values()).filter((op) -> op.getUppercaseName().equals(calculationRequest.operation().toUpperCase())).findFirst();

        if (operation.isPresent()) {
            if (calculationRequest.arguments().length < operation.get().getMinArgs()) {
                result = onInsufficientArguments(calculationRequest.operation());
            }
            else if (calculationRequest.arguments().length > operation.get().getMaxArgs()) {
                result = onExcessiveArguments(calculationRequest.operation());
            }
            else {
                result = onArgumentsInBound(calculationRequest.arguments(), operation.get());
            }
        }
        else {
            result = onUnsupportedOperation(calculationRequest.operation());
        }

        return result;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result onUnsupportedOperation(String attemptedOperation) {
        return new Result(OptionalInt.empty(), Optional.of(String.format(ErrorMessage.NO_SUCH_OPERATION.getFormat(), attemptedOperation)));
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result onInsufficientArguments(String attemptedOperation) {
        return new Result(OptionalInt.empty(), Optional.of(String.format(ErrorMessage.NOT_ENOUGH_ARGUMENTS.getFormat(), attemptedOperation)));
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result onExcessiveArguments(String attemptedOperation) {
        return new Result(OptionalInt.empty(), Optional.of(String.format(ErrorMessage.TOO_MANY_ARGUMENTS.getFormat(), attemptedOperation)));
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result onDivisionByZero() {
        return new Result(OptionalInt.empty(), Optional.of(ErrorMessage.DIVISION_BY_ZERO.getFormat()));
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result onNegativeNumberFactorial() {
        return new Result(OptionalInt.empty(), Optional.of(ErrorMessage.FACTORIAL_OF_NEGATIVE_NUMBER.getFormat()));
    }

    @ResponseStatus(code = HttpStatus.OK)
    public Result onValid(int[] arguments, Operation operation) {
        return new Result(operation.calculate(arguments), Optional.empty());
    }

    public Result onArgumentsInBound(int[] arguments, Operation operation) {
        Result result;
        if (operation == Operation.DIVISION && arguments[1] == 0) {
            result = onDivisionByZero();
        }
        else if (operation == Operation.FACTORIAL && arguments[0] < 0) {
            result = onNegativeNumberFactorial();
        }
        else {
            result = onValid(arguments, operation);
        }

        return result;
    }

}
