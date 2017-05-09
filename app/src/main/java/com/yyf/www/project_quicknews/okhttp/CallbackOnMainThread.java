package com.yyf.www.project_quicknews.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class CallbackOnMainThread<T> implements okhttp3.Callback {

    //static 内部类，防止内存泄漏
    private static class UIHandler extends Handler {

        public static final int MSG_ON_FAILURE = 1;
        public static final int MSG_ON_RESPONSE_SUCCESS = 2;
        public static final int MSG_ON_RESPONSE_FAILURE = 3;

        private WeakReference mWeakReference;

        public UIHandler(CallbackOnMainThread callback) {
            super(Looper.getMainLooper());
            mWeakReference = new WeakReference(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            CallbackOnMainThread callback = (CallbackOnMainThread) mWeakReference.get();
            if(callback==null){
                return;
            }
            switch (msg.what) {
                case MSG_ON_FAILURE:
                    IOException e = (IOException) msg.obj;
                    callback.onFailure(e);
                    break;
                case MSG_ON_RESPONSE_SUCCESS:
                    callback.onResponse(msg.obj);
                    break;
                case MSG_ON_RESPONSE_FAILURE:
                    callback.onFailure(null);
                    break;
            }

        }
    }

    private UIHandler mUIHandler;
    private Parser<T> mParser;

    public CallbackOnMainThread(Parser<T> parser) {
        if (parser == null) {
            throw new IllegalArgumentException("parser can not be null");
        }
        mParser = parser;
        mUIHandler = new UIHandler(this);
    }

    /**
     * Called when the request could not be executed due to cancellation, a connectivity problem or
     * timeout.
     */
    @Override
    public void onFailure(Call call, IOException e) {

        Message message = Message.obtain();
        message.what = UIHandler.MSG_ON_FAILURE;
        message.obj = e;
        mUIHandler.sendMessage(message);
    }

    /**
     * Called when the HTTP response was successfully returned by the remote server. The callback may
     * proceed to read the response body with {@link Response#body}. The response is still live until
     * its response body is {@linkplain ResponseBody closed}. The recipient of the callback may
     * consume the response body on another thread.
     * <p>
     * <p>Note that transport-layer success (receiving a HTTP response code, headers and body) does
     * not necessarily indicate application-layer success: {@code response} may still indicate an
     * unhappy HTTP response code like 404 or 500.
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.isSuccessful()) {
            T parseResult = mParser.parse(response);
            Message message = Message.obtain();
            message.what = UIHandler.MSG_ON_RESPONSE_SUCCESS;
            message.obj = parseResult;
            mUIHandler.sendMessage(message);
        } else {
            Message message = Message.obtain();
            message.what = UIHandler.MSG_ON_RESPONSE_FAILURE;
            mUIHandler.sendMessage(message);
        }

    }

    /**
     * Called when the HTTP response was successfully returned by the remote server
     *
     * @param result if response code is not in [200..300),result is null
     */
    protected abstract void onResponse(T result);

    /**
     * Called when the request could not be executed due to cancellation, a connectivity problem or
     * timeout.
     *
     * @param e
     */
    protected abstract void onFailure(IOException e);

}
