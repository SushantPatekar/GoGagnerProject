package networkcommunication;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dbModel.User;
import dbModel.UserModel;
import utility.Constants;
import utility.Helper;

/**
 * Created by sneha.chandiyekar on 6/16/2018.
 */
public class SessionManager {

    static boolean response = false;
    private static PendingRequest pendingRequest;

    public static void setPendingRequest(String url, int reqType, Object requestBody, NetworkCommunicationHelper.OnResponseReceived responseReceived) {
        pendingRequest = new PendingRequest();
        pendingRequest.setUrl(url);
        pendingRequest.setType(reqType);
        pendingRequest.setBody(requestBody);
        pendingRequest.setResponseReceived(responseReceived);
    }

    public static PendingRequest getPendingRequest() {
        return pendingRequest;
    }



    public static void sendRefreshTokenReq(final Application context, final OnSessionResponseReceived responseReceived) {
        try {
            final  User user =new UserModel().getUserbyID(context,Helper.getSharedPrefValStr(context,
                    Constants.sharedPref.userName));
            String webAPI = Helper.getSharedPrefValStr(context, Constants.sharedPref.s_BASE_URL)
                    .concat(Constants.webAPI.refreshToken);
            String requestBody = new Helper().generateRefreshTokenObject(user);
//TODO
            NetworkCommunicationHelper networkHelper = new NetworkCommunicationHelper();
            networkHelper.FetchAccessTokenRequest(context, webAPI, requestBody, new NetworkCommunicationHelper.OnResponseReceived() {
                @Override
                public void onSuccess(String res) {
                    try{
                        JSONObject jRes= new JSONObject(res);
                        String accessToken = jRes.getString("token");
                        new UserModel().updateUserAccessToken(context,accessToken,""+user.getId());
                        responseReceived.onSuccess(res);
                    }
                    catch (Exception e){

                    }

                }

                @Override
                public void onFailure(String err) {
                    responseReceived.onError(err);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            responseReceived.onError(e.getMessage());
        }
    }

   /* public static void sendSyncRefreshTokenReq(final Application context, final OnSessionResponseReceived responseReceived) {
        try {
            SessionData session = getSessionData(context);
            Map<String, String> params = new HashMap<>();
            params.put("refresh_token", session.getRefreshToken());
            params.put("grant_type", "refresh_token");
            logger.info("sendRefreshTokenReq params: " + params);

            String url = MainApp.BASE_URL + Constants.Session.REFRESH_TOKEN_URL;
            //String url = "http://10.254.5.37:8080/token";
            logger.info("refresh url: " + url);

            NetworkCommunicationHelper networkHelper = new NetworkCommunicationHelper();
            networkHelper.sendSynchronousTokenReq(context, url, params, new NetworkCommunicationHelper.OnResponseReceived() {
                @Override
                public void onSuccess(String res) {
                    logger.info("refresh token response: " + res);
                    SessionData sessionData = addSessionDetail(context, res);
                    long expiresIn = sessionData.getExpiresIn() - 300;
                    if (expiresIn < 0) {
                        expiresIn = 30;
                    }
                    Helper helper = new Helper();
                    helper.updateSharedPrefValLong(context, Constants.Session.EXPIRES_IN, expiresIn);
                    helper.setAlarmForRefreshToken(context, expiresIn);
                    responseReceived.onSuccess(res);
                    //new LogDetailsModel().addLogToDb(context, LogType.REFRESH_TOKEN.getLogTypeId());

                }

                @Override
                public void onFailure(String err) {
                    logger.info("response: " + err);
                    responseReceived.onError(err);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            responseReceived.onError(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    private static void showDialog(Application context, boolean flag) {
        try {
            boolean foreground = new ForegroundCheckTask().execute(context).get();
            if (foreground) {
                Intent intent = new Intent(Constants.ACTION_DISABLE_OFFLINE_MODE);
                intent.putExtra(Constants.Session.IS_REFRESHING, flag);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean sendSyncAccessTokenReq(final Application context, final OnSessionResponseReceived responseReceived) {
        String key = new Helper().getSharedPrefValStr(context, Constants.RegisterDevice.REGISTER_DEVICE_KEY);
        String encPwd = new KeysModel().encryptPassword(key, context);
        Map<String, String> params = new HashMap<>();
        params.put("username", new Helper().getEquipmentId(context) + "@vdsb");
        params.put("password", encPwd);
        params.put("grant_type", "password");

        logger.info("sendSynchronousTokenReq params: " + params);

        String url = MainApp.BASE_URL + Constants.Session.ACCESS_TOKEN_URL;
        //String url = "http://10.254.5.37:8080/token";

        logger.info("access url: " + url);

        NetworkCommunicationHelper helper = new NetworkCommunicationHelper();
        helper.sendSynchronousTokenReq(context, url, params, new NetworkCommunicationHelper.OnResponseReceived() {
            @Override
            public void onSuccess(String res) {
                logger.info("response: " + res);
                response = true;
                SessionData sessionData = addSessionDetail(context, res);
                long expiresIn = sessionData.getExpiresIn() - 300;
                if (expiresIn < 0) {
                    expiresIn = 300;
                }
                Helper helper = new Helper();
                helper.updateSharedPrefValLong(context, Constants.Session.EXPIRES_IN, expiresIn);
                helper.setAlarmForRefreshToken(context, expiresIn);
                responseReceived.onSuccess(res);
                //new LogDetailsModel().addLogToDb(context, LogType.ACCESS_TOKEN.getLogTypeId());

            }

            @Override
            public void onFailure(String err) {
                logger.info("response: " + err);
                response = false;
                responseReceived.onError(err);
            }
        });

        return response;
    }

    public static boolean sendAccessTokenReq(final Application context, final OnSessionResponseReceived responseReceived) {
        String key = new Helper().getSharedPrefValStr(context, Constants.RegisterDevice.REGISTER_DEVICE_KEY);
        String encPwd = new KeysModel().encryptPassword(key, context);
        Map<String, String> params = new HashMap<>();
        params.put("username", new Helper().getEquipmentId(context) + "@vdsb");
        params.put("password", encPwd);
        params.put("grant_type", "password");

        logger.info("sendSynchronousTokenReq params: " + params);

        String url = MainApp.BASE_URL + Constants.Session.ACCESS_TOKEN_URL;
        //String url = "http://10.254.5.37:8080/token";

        logger.info("access url: " + url);

        NetworkCommunicationHelper helper = new NetworkCommunicationHelper();
        helper.sendTokenReq(context, url, params, new NetworkCommunicationHelper.OnResponseReceived() {
            @Override
            public void onSuccess(String res) {
                logger.info("response: " + res);
                response = true;
                SessionData sessionData = addSessionDetail(context, res);
                long expiresIn = sessionData.getExpiresIn() - 300;
                if (expiresIn < 0) {
                    expiresIn = 300;
                }
                Helper helper = new Helper();
                helper.updateSharedPrefValLong(context, Constants.Session.EXPIRES_IN, expiresIn);
                helper.setAlarmForRefreshToken(context, expiresIn);
                responseReceived.onSuccess(res);
                //new LogDetailsModel().addLogToDb(context, LogType.ACCESS_TOKEN.getLogTypeId());

            }

            @Override
            public void onFailure(String err) {
                logger.info("response: " + err);
                response = false;
                responseReceived.onError(err);
            }
        });

        return response;
    }

    private static SessionData addSessionDetail(Application context, String res) {
        try {
            SessionData data = new SessionData();
            JSONObject resJson = new JSONObject(res);
            data.setAccessToken(resJson.getString("access_token"));
            data.setRefreshToken(resJson.getString("refresh_token"));
            data.setExpiresIn(resJson.getLong("expires_in"));
            data.setTokenType(resJson.getString("token_type"));

            SessionDataDao sessionDataDao = VaishnoDeviRoomDb.getInstance(context).sessionDataDao();
            sessionDataDao.delete();
            sessionDataDao.insert(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }*/

    public interface OnSessionResponseReceived {
        void onSuccess(String res);

        void onError(String err);
    }

}
