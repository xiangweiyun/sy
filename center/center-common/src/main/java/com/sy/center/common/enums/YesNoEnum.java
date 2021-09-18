package com.sy.center.common.enums;

/**
 * 有效标识枚举.
 *
 * @author xiangwy
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company:
 * @Version: V1.0
 */
public enum YesNoEnum {
    /** 是 */
    YES("1", "是"),
    /** 否 */
    NO("0", "否");

    private String code;

    private String text;

    private YesNoEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
