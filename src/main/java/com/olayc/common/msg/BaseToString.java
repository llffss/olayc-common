package com.olayc.common.msg;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * TODO desc file
 *
 * @author liuyan
 * @date 2018-09-11
 */
public abstract class BaseToString implements java.io.Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
