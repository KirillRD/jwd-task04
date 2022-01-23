package com.epam.library.constant;

public enum IssuanceOperation {
    RETURN("return"),
    EXTEND("extend"),
    LOST("lost");

    private final String operation;

    IssuanceOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public static boolean containsOperation(String operation) {
        for (IssuanceOperation issuanceOperation : IssuanceOperation.values()) {
            if (issuanceOperation.operation.equals(operation)) {
                return true;
            }
        }
        return false;
    }
}
