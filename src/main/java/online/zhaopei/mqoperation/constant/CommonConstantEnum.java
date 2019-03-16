package online.zhaopei.mqoperation.constant;

public enum CommonConstantEnum {
    ;

    private String value;

    private CommonConstantEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
