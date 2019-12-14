package com.example.demo.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 将来的大拿:徐林飞
 */
public class ResponseResult<T> implements Serializable {
    /**
     * 通用返回类型;
     */
    private static final long serialVersionUID = 6478792553269347420L;
    private Integer state;
    private String[] message;
    private T data;

    public ResponseResult() {
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseResult)) return false;
        ResponseResult<?> that = (ResponseResult<?>) o;
        return Objects.equals(state, that.state) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(state, message, data);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
