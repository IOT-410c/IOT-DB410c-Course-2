package net.calit2.mooc.iot_db410c.webserver;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Name:        WebServer
 * Description: Class detailing the methods needed to perform
 *              HTTP Requests and send HTTP Messages
 */
public class WebServer {
    private static final String TAG = "WebServer";

    private static final int REFRESH_RATE = 5000; // in milliseconds

    private JSON json;
    private JSONObject jsonObject;
    private Context context;

    // Loads the C Library
    static{
        System.loadLibrary("server");
    }

    /**
     * Name:        WebServer
     * Description: Instantiates the instance variables that will be used
     *              to create and refresh the JSON. Constructor for the
     *              class.
     *
     * @param context Context passed from the Service to enable JSON to gather
     *                SystemInfo and to enable getString()
     */
    public WebServer(final Context context) {
        this.context = context;
        json = new JSON(context);
        jsonObject = json.createJSON();

        // Running Thread to continuously update the JSON Data
        new Thread() {
            public void run() {
                while(true) {
                    if (System.currentTimeMillis() % REFRESH_RATE == 0) {
                        updateJSONData();
                    }
                }
            }
        }.start();

         beginServer(getIPAddress(), "8080");
    }

    /**
     * Name:        updateJSONData
     * Description: Performs a call to createJSON to generate a JSON string
     *              to display in the HTTP Response
     */
    public void updateJSONData(){
        jsonObject = json.createJSON();
    }

    /**
     * Name:        processMessage
     * Description: Parses the messages received from the WebServer and
     *              returns the appropriate string
     *
     * @param message String containing the HTTP Request
     * @return        String containing the content of
     */
    public String processMessage(String message) {
        Log.e(TAG, message);

        String[] httpRequest = message.split(" ");

        if (httpRequest[0].equals("GET") && httpRequest[1].equals("/")){
            String html = context.getString(R.string.default_html_page);

            return setUpHTTPMessage(html, "text/html");
        } else if(httpRequest[0].equals("GET") &&
                httpRequest[1].equals("/status")) {

            return setUpHTTPMessage(jsonObject.toString(), "application/json");
        } else if(httpRequest[0].equals("GET") &&
                httpRequest[1].equals("/status/pretty")) {
            try {
                return setUpHTTPMessage(jsonObject.toString(2), "application/json");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return setUpHTTPMessage(jsonObject.toString(), "application/json");
        }

        String content = context.getString(R.string.error_html_page);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(context.getString(R.string.date_format),
                        Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(context.getString(
                R.string.time_zone)));

        return "HTTP/1.1 404 Not Found\r\n"+
                "Date: " + dateFormat.format(new Date()).replace("+00:00", "") +
                "\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + content.length() + "\r\n"+ "\r\n" +
                content;
    }

    /**
     * Name:        beginServer
     * Description: Declaration of a Native method to be defined in
     *              server.c
     *
     * @param ipAddress String containing the ipAddress of the device
     * @param port      String containing the port
     */
    public native void beginServer(String ipAddress, String port);

    /**
     * Name:        getIPAddress
     * Description: Iterates through the device's IPAddresses and returns
     *              first non-local IPAddress
     *
     * @return String containing the first non-local IPAddress of
     *          the device.
     */
    public String getIPAddress(){
        String ipAddress = "";
        try {
            for (Enumeration<NetworkInterface> en =
                 NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {

                NetworkInterface networkInterface = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr =
                     networkInterface.getInetAddresses();
                     enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        return ipAddress;
    }

    /**
     * Name:        setUpHTTPMessage
     * Description: Formats the message into an HTTP request that is to be sent
     *              to the server.
     *
     * @param content       String containing the content of the message
     * @param content_type  String containing the "Content-Type"
     * @return              String containing the formatted HTTP request
     *                      message
     */
    public String setUpHTTPMessage(String content, String content_type){
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(context.getString(R.string.date_format),
                        Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(context.getString(
                R.string.time_zone)));

        return "HTTP/1.1 200 OK\r\n"+
            "Date: " + dateFormat.format(new Date()).replace("+00:00", "") +
                "\r\n" +
            "Content-Type: " + content_type + "\r\n" +
            "Content-Length: " + content.length() + "\r\n"+ "\r\n" +
                content;
    }
}