package com.share.greencloud;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public abstract class TBRestTemplete extends RestTemplate {

    public TBRestTemplete() {
        setErrorHandler(new MyErrorHandler());
    }

    abstract void onError(ClientHttpResponse response);


    public class MyErrorHandler implements ResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // your error handling here
            onError(response);
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }
    }
}
