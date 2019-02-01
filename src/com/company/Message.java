package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Date;



public class Message {

    private int id;
    private Date timestamp;
    private String text;
    public ByteArrayOutputStream outputStream;

    public Message(int id, Date timestamp, String text) {
        this.id = id;
        this.timestamp = timestamp;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Message decode(ByteBuffer bytes) {

        if (bytes==null || bytes.remaining()< Integer.BYTES + Long.BYTES + Short.BYTES) {
            return null;
        }


        bytes.order(ByteOrder.BIG_ENDIAN);

        int id = decodeInt(bytes);
        Date timestamp = new Date(decodeLong(bytes));
        String text = decodeString(bytes);

        Message message = new Message(id, timestamp, text);

        return message;
    }

    public ByteBuffer encode() throws IOException {

        outputStream = new ByteArrayOutputStream();
        encodeInt(id);
        encodeLong(timestamp.getTime());
        encodeString(text);

        return ByteBuffer.wrap(outputStream.toByteArray());
    }

    public void encodeShort(short value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(value);
        outputStream.write(buffer.array());
    }

    public void encodeInt(int value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(value);
        outputStream.write(buffer.array());
    }

    public void encodeLong(long value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putLong(value);
        outputStream.write(buffer.array());
    }

    public void encodeString(String value) throws IOException {
        if (value==null)
            value="";

        byte[] textBytes = value.getBytes(Charset.forName("UTF-16BE"));
        encodeShort((short) (textBytes.length));
        outputStream.write(textBytes);
    }

    public static short decodeShort(ByteBuffer bytes) {
        return bytes.getShort();
    }

    public static int decodeInt(ByteBuffer bytes) {
        return bytes.getInt();
    }

    public static long decodeLong(ByteBuffer bytes) {
        return bytes.getLong();
    }

    public static String decodeString(ByteBuffer bytes) {
        short textLength = decodeShort(bytes);
        if (bytes.remaining() < textLength) {
            return null;
        }

        byte[] textBytes = new byte[textLength];
        bytes.get(textBytes, 0, textLength);
        return new String(textBytes, Charset.forName("UTF-16BE"));
    }

}