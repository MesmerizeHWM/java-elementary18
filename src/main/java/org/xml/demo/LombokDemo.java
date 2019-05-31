package org.xml.demo;

import lombok.*;

/**
 * Documentation
 */
@Data
public class LombokDemo {

    private int var1;
    //some comment
    @EqualsAndHashCode.Exclude
    private final String var2;

    public static void main(String[] args) {
        LombokDemo d = new LombokDemo("111");
    }
}
