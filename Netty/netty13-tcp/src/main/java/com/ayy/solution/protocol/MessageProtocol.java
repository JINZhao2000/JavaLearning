package com.ayy.solution.protocol;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 26/07/2021
 * @ Version 1.0
 */

public class MessageProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public MessageProtocol setLength(int length) {
        this.length = length;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

    public MessageProtocol setContent(byte[] content) {
        this.content = content;
        return this;
    }
}
