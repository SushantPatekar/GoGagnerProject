package networkcommunication;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import gogagner.goldenbrainsithub.com.GoGagnerApplication;
import gogagner.goldenbrainsithub.com.R;
import utility.Constants;

public class NetworkCommunicationHelper {

    final int TIMEOUT = 1 * 60 * 1000;    //1min
    private final int POST_REQ = 1;
    private final int SYNC_POST_REQ = 2;
    private final int GET_REQ = 3;
    private final int SYNC_GET_REQ = 4;

    public void sendSynchronousGetRequest(final Application context, String url, final OnResponseReceived responseReceived) {
        RequestQueue mRequestQueue = ((GoGagnerApplication) context).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        responseReceived.onSuccess(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        //Log.d("ERROR","error => "+error.toString());
                        responseReceived.onFailure(getServerError(error));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(Constants.webAPI.apitoken, Constants.webAPI.apitoken_val);
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }

//POST Call
    public void sendPostRequest(final Application context, final String url, final String reqJson, final OnResponseReceived responseReceived) {
        RequestQueue mRequestQueue = ((GoGagnerApplication) context).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseReceived.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.e("error",""+error.networkResponse.statusCode);
                Log.e("error",""+error.networkResponse.data);
                responseReceived.onFailure(getServerError(error));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return reqJson == null ? null : reqJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    uee.fillInStackTrace();
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", reqJson, "utf-8");

                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();

                header.put(Constants.webAPI.header_content_type, Constants.webAPI.value_content_type);
                header.put(Constants.webAPI.apitoken, Constants.webAPI.apitoken_val);
                return header;
            }
        };

        mRequestQueue.add(stringRequest);

    }


    //userLoginBased API
    public void sendUserPostRequest(final Application context, final String url, final String reqJson, final OnResponseReceived responseReceived) {
        RequestQueue mRequestQueue = ((GoGagnerApplication) context).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseReceived.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              /*  Log.e("error",""+error.networkResponse.statusCode);
                Log.e("error",""+error.networkResponse.data);
                responseReceived.onFailure(getServerError(error));*/
              String serverError = null;
                JSONObject jsonServerError =null;
                String serverStatus = null;
              try
              {
                serverError =  getServerError(error);
                jsonServerError = new JSONObject(serverError);
                serverStatus = jsonServerError.getString("status");

              }
              catch ( SecurityException e){
                  if (serverStatus.matches(Constants.Session.TOKEN_EXPIRY_CODE)) {
                      refreshRequest(context, url, SYNC_POST_REQ, reqJson, responseReceived);
                  } else {
                      responseReceived.onFailure(getServerError(error));
                  }
              } catch (JSONException e) {
                  responseReceived.onFailure(getServerError(error));
                  e.printStackTrace();
              } catch (Exception e) {
                  responseReceived.onFailure(getServerError(error));
                  e.printStackTrace();
              }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return reqJson == null ? null : reqJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    uee.fillInStackTrace();
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", reqJson, "utf-8");

                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();

                header.put(Constants.webAPI.header_content_type, Constants.webAPI.value_content_type);
                header.put(Constants.webAPI.apitoken, Constants.webAPI.apitoken_val);
                header.put(Constants.webAPI.header_x_access_token, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjI4LCJmaXJzdE5hbWUiOiJBc2h1IiwibGFzdE5hbWUiOiJuYWlyIiwibW9iaWxlIjoiOTg2NzQ0NTU0MSIsInVzZXJUeXBlIjowLCJpYXQiOjE1NDkxMzI5NzUsImV4cCI6MTU0OTEzMzg3NX0.1AYmdT-5vsVu99P_QtGdak10NOb2TjywKGgLOn0da0g");
                return header;
            }
        };

        mRequestQueue.add(stringRequest);

    }
    public void sendGetRequest(final Application context, final String url, final OnResponseReceived responseReceived) {
        RequestQueue mRequestQueue = ((GoGagnerApplication) context).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseReceived.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                responseReceived.onFailure(Arrays.toString(parseError(e)));
            }
        }

        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    public void sendSynchronousPostRequest(final Application context, String url, final String requestBody, final OnResponseReceived responseReceived) {
        RequestQueue mRequestQueue = ((GoGagnerApplication) context).getRequestQueue();
        RequestFuture<String> requestFuture = RequestFuture.newFuture();
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url, requestFuture, requestFuture) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
               // return "application/json";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    uee.fillInStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();

                header.put(Constants.webAPI.header_content_type, Constants.webAPI.value_content_type);
                header.put(Constants.webAPI.apitoken, Constants.webAPI.apitoken_val);
                return header;
            }

        };

       /* jsonRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        mRequestQueue.add(jsonRequest);
        try {
            String response = requestFuture.get(TIMEOUT, TimeUnit.MILLISECONDS);
            responseReceived.onSuccess(response);
        } catch (InterruptedException | ExecutionException e) {
            responseReceived.onFailure(Arrays.toString(parseError(e)));
        } catch (TimeoutException e) {
            responseReceived.onFailure(e.toString());
            e.printStackTrace();
            //logger.error(e.getMessage());
        } catch (Exception e) {
            responseReceived.onFailure(e.toString());
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }



    /// For Access Token API
    public void sendPostAccessTokenRequest(final Application context, final String url, final String reqJson, final OnResponseReceived responseReceived) {
        RequestQueue mRequestQueue = ((GoGagnerApplication) context).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseReceived.onSuccess(getSuccessResponseToken(response.toString()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",""+error.networkResponse.statusCode);
                Log.e("error",""+error.networkResponse.data);
                responseReceived.onFailure(getServerError(error));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return reqJson == null ? null : reqJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    uee.fillInStackTrace();
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", reqJson, "utf-8");

                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();

                header.put(Constants.webAPI.header_content_type, Constants.webAPI.value_content_type);
                header.put(Constants.webAPI.apitoken, Constants.webAPI.apitoken_val);
                return header;
            }
        };

        mRequestQueue.add(stringRequest);



    }
    ///
    private String[] parseError(Exception e) {
        String[] errorBuilder = new String[3];
        if (e.getCause() instanceof VolleyError) {
            VolleyError ve = (VolleyError) e.getCause();
            //logger.info(ve.toString());
            if (ve.networkResponse != null) {
                errorBuilder[0] = (ve.networkResponse.toString());
                errorBuilder[1] = (ve.networkResponse.statusCode + "");
                errorBuilder[2] = (new String(ve.networkResponse.data));

            } else {
                errorBuilder[0] = ve.toString();
            }
        } else {
            errorBuilder[0] = e.toString();
        }
        return errorBuilder;
    }

    private String getServerError(VolleyError error){
        String errorMessage= null;
        try {
            NetworkResponse response = error.networkResponse;
            if(response != null && response.data != null){

                String errorString = new String(response.data);
                JSONObject main = new JSONObject(errorString);
                JSONObject j_response = main.getJSONObject("error");
               return j_response.toString();

                //Additional cases
            }
        }
        catch (Exception e){

        }
        return errorMessage;
    }


    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            JSONObject innerObj = obj.getJSONObject("error");
            trimmedString = innerObj.getString("messages");
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public String getSuccessResponseToken(String message){
        String responseCode= null;
        try{
            JSONObject main,response,data,userDetails;
            main = new JSONObject(message);
            response = main.getJSONObject("response");
            data = response.getJSONObject("data");
            responseCode = response.getString("status");
            userDetails = data.getJSONObject("userDetails");

        }
        catch (Exception e){

        }
        return responseCode;
    }


    private void refreshRequest(final Application context, String url, final int type, Object body, final OnResponseReceived responseReceived) {
        String BaseURL= url;
        SessionManager.setPendingRequest(url, type, body, responseReceived);

        if (type == GET_REQ || type == POST_REQ) {
            SessionManager.sendRefreshTokenReq(context, new SessionManager.OnSessionResponseReceived() {
                @Override
                public void onSuccess(String res) {
                    PendingRequest pendingRequest = SessionManager.getPendingRequest();
                    if (pendingRequest != null) {
                        if (type == GET_REQ) {
                            sendGetRequest(context, pendingRequest.getUrl(), pendingRequest.getResponseReceived());
                        } else {
                            sendPostRequest(context, pendingRequest.getUrl(), (String) pendingRequest.getBody(), pendingRequest.getResponseReceived());
                        }
                    } else {
                        responseReceived.onFailure("Pending request null");
                    }
                }

                @Override
                public void onError(String err) {
                    responseReceived.onFailure(context.getString(R.string.session_refresh_fail));
                }
            });
        } else {
           /* SessionManager.sendSyncRefreshTokenReq(context, new SessionManager.OnSessionResponseReceived() {
                @Override
                public void onSuccess(String res) {
                    PendingRequest pendingRequest = SessionManager.getPendingRequest();
                    if (pendingRequest != null) {
                        if (type == SYNC_POST_REQ) {
                            sendSynchronousPostRequest(context, pendingRequest.getUrl(), (String) pendingRequest.getBody(), pendingRequest.getResponseReceived());
                        } else if (type == SYNC_GET_REQ) {
                            sendSynchronousGetRequest(context, pendingRequest.getUrl(), pendingRequest.getResponseReceived());
                        } else if (type == SYNC_POST_JSON_ARRAY_REQ) {
                            sendSynchronousPostJSONArrayRequest(context, pendingRequest.getUrl(), (JSONArray) pendingRequest.getBody(), pendingRequest.getResponseReceived());
                        }
                    } else {
                        responseReceived.onFailure("Pending request null");
                    }
                }

                @Override
                public void onError(String err) {
                    responseReceived.onFailure(context.getString(R.string.session_refresh_fail));
                }
            });*/
        }
    }

//TODO
   /* public void sendTokenReq(final Application context, String url, final Map<String, String> dataMap, final OnResponseReceived responseReceived) {
        String BaseURL= url;
        RequestQueue mRequestQueue = ((MainApp) context).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseReceived.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                String[] error = parseError(e);
                if (error != null
                        && error[2] != null && error[2].contains(Constants.Session.INVALID_GRANT_MSG)
                        && error[1] != null && error[1].equals(Constants.Session.INVALID_GRANT_CODE)) {
                    PendingRequest pendingRequest = SessionManager.getPendingRequest();
                    if (pendingRequest != null) {
                        accessTokenRequest(context, pendingRequest.getUrl(), pendingRequest.getType(), pendingRequest.getBody(), pendingRequest.getResponseReceived());
                    }
                    responseReceived.onFailure("Pending request null");
                } else {
                    responseReceived.onFailure(Arrays.toString(parseError(e)));
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return dataMap;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);
    }*/

    public interface OnResponseReceived {
        void onSuccess(String res);

        void onFailure(String err);
    }
}
