package model.transfer;

public class TransferResult {

    private final boolean success;
    private final String message;

    private TransferResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static TransferResult success(String msg) {
        return new TransferResult(true, msg);
    }

    public static TransferResult failure(String msg) {
        return new TransferResult(false, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}


