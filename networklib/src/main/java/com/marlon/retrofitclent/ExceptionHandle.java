package com.marlon.retrofitclent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author Marlon
 * @desc
 * @date 2019/5/14
 */
public class ExceptionHandle {
    /**
     * 定义网络异常码
     */
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    public static ResponeException handleException(Throwable e) {
        ResponeException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeException(e, INTERNAL_SERVER_ERROR);
            Response<?> response = httpException.response();
            try {
                String string = response.errorBody().string();
                JSONObject jsonObject = new JSONObject(string);
                String error = (String) jsonObject.get("error_description");
                ex.message = error;
                return ex;
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            switch (httpException.code()) {
                case UNAUTHORIZED:
                    break;
                case FORBIDDEN:
                    break;
                case NOT_FOUND:
                    break;
                case GATEWAY_TIMEOUT:
                    break;
                case INTERNAL_SERVER_ERROR:
                    break;
                case BAD_GATEWAY:
                    break;
                case SERVICE_UNAVAILABLE:
                    break;
                default:
                    ex.code = httpException.code();
                    ex.message = "无网络,请重试!";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else {
            ex = new ResponeException(e, INTERNAL_SERVER_ERROR);
            ex.message = "未知错误";
            return ex;
        }
    }
}
