package com.testing.skywell.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Networks {

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %SB", bytes / Math.pow(unit, exp), pre);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[16384];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 65535);
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static boolean isSimExist(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE); // gets the current TelephonyManager
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return true;
        }
        return false;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isNetworkAvailable(WeakReference<Context> context) {
        if (context != null) {
            try {
                ConnectivityManager connectivity = (ConnectivityManager) context.get().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivity == null) {
                    return false;
                } else {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null) {
                        for (int i = 0; i < info.length; i++) {
                            if (info[i].isConnected()) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                Log.d("NETWORK STATE", "NON!!! - 1");
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].isConnected()) {
                            Log.d("NETWORK STATE", "AVALIABLE");
                            return true;
                        }
                    }
                }
            }
            Log.d("NETWORK STATE", "NON!!! - 2");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("NETWORK STATE", "NON!!! - 3");
        return false;
    }

    public static boolean upInterface() {

        InetAddress addr = null;
        try {
            addr = InetAddress.getByAddress(new byte[]{(byte) 8, (byte) 8, (byte) 8, (byte) 8});
            SocketAddress sockaddr = new InetSocketAddress(addr, 53);
            Socket socket = new Socket();
            socket.connect(sockaddr, 1500);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isNetworkAvailableCheckUp(Context context) {
        if (isNetworkAvailable(context)) {
            return Networks.upInterface();
        }
        return false;
    }

}
