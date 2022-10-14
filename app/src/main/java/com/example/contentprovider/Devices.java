package com.example.contentprovider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.BufferedReader;
import java.util.Set;

public class Devices implements IDeviceInfo{


    private final Context mContext;

    private TelephonyManager telephonyManager;

    /**
     * Creates a new instance.
     *
     * @param context A {@link Context} instance.
     */
    public Devices(Context context) {
        this.mContext = context;
        Log.i("TAG", "init DeviceInfoService");
    }

    //1.获取终端设备序列号
    public String getSerialNo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Build.getSerial();
        } else {
            return Build.SERIAL;
        }
    }

    //2.获取终端IMSI号
    @SuppressLint("HardwareIds")
    public String getIMSI(){
        telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId() ;
    }

    //3.获取终端IMEI号
    @SuppressLint("HardwareIds")
    public String getIMEI(){
        telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    // <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    //4.获取终端SIM卡ICCID号
    @SuppressLint("HardwareIds")
    public String getICCID(){
        telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
    }

    //5.获取厂商名称
    public String getManufacture(){
        return android.os.Build.MANUFACTURER;
    }

    //6. 获取终端型号
    public String getModel(){
        return android.os.Build.MODEL;
    }

    //7.获取Android操作系统版本
    public String getAndroidOSVersion(){
        return android.os.Build.VERSION.RELEASE;
    }

    //8.获取Android内核版本
    public String getAndroidKernelVersion(){
        String kernelVersion = "";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/proc/version");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return kernelVersion;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
        String info = "";
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                info += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (info != "") {
                final String keyword = "version ";
                int index = info.indexOf(keyword);
                line = info.substring(index + keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return kernelVersion;
    }


    //MIUI标识
    public static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    public static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    public static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    //EMUI标识
    public static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    public static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    public static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    //Flyme标识
    public static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    public static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    public static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    public static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    public static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";
    //当前标识符
    public static String KEY_CURRENT_FALG = KEY_MIUI_INTERNAL_STORAGE;

    //9.获取终端ROM版本
    public String getROMVersion(){

        String rom_type = "OTHER";


        return rom_type;
    }

    //10. 获取终端固件版本
    public String getFirmwareVersion(){
        return null;
    }

    //11.获取终端硬件版本
    public String getHardwareVersion(){
        return null;
    }

    //12.更新终端系统时间
    public Boolean updateSystemTime(String date, String time){
        return true;
    }

    /**
     * 系统功能设置
     * * <ul>
     *      * <li>HOMEKEY(boolean) – 是否允许使用HOME键</li>
     *      * <li>STATUSBARKEY(boolean) – 是否允许使用下拉菜单</li>
     * * </ul>
     * @param bundle
     * @return
     */
    public Boolean setSystemFunction(Bundle bundle){
        return false;
    }



    //15.获取终端产品序列号
    public String getPN(){
        String devicePN = "N/A";
        try {
            Class<?> propClass = Class.forName("android.os.SystemProperties");
            Method getProp = propClass.getMethod("get", String.class, String.class);
            devicePN = (String) getProp.invoke(propClass, "ro.lenovosn2", "N/A");
        } catch (Exception ignored) {
        }
        return devicePN;
    }

    /**
     * 16.设置是否屏蔽电源键
     * @param status : 1:屏蔽电源键   0:不屏蔽
     */
    public void setPowerStatus(boolean status){

    }



    //19. 获取Flash总量（字节）
    public String getFlashTotal(){
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        //获得sdcard上 block的总数
        long blockCount = statFs.getBlockCountLong();
        //获得sdcard上每个block 的大小
        long blockSize = statFs.getBlockSizeLong();
        //计算标准大小使用：1024，当然使用1000也可以
        return null;
    }

    //20.获取Flash可用容量（字节）
    public String getFlashAvailable(){
        return null;
    }

    //21.获取移动数据使用流量（字节）
    public String getMobileDataUsageTotal(){
        return null;
    }

    //22.获取终端开关机次数
    public String getBootCounts(){
        return null;
    }

    //23. 获取终端走纸总长度（毫米）
    public String getPrintPaperLen(){
        return null;
    }

    //24.获取电池温度
//import static android.os.BatteryManager.EXTRA_TEMPERATURE;
    public String getBatteryTemperature(){
        Intent intent = mContext.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int  temp   = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)) / 10;
        return String.valueOf(temp);
    }

    //25.获取电池电量
//import static android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY;
    public String getBatteryLevel(){
        Intent intent = mContext.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int  level   = (intent.getIntExtra(String.valueOf(BatteryManager.BATTERY_PROPERTY_CAPACITY),0)) / 10;
        return String.valueOf(level);
    }

    //26. 获取MEID
    public String getMEID(){
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class, String.class);

            String meid = (String) method.invoke(null, "ril.cdma.meid", "");
            if(!TextUtils.isEmpty(meid)){
                return meid;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.w("TAG","getMEID error : "+ e.getMessage());
        }
        return "";
    }

    //27.获取设备服务版本号
    public String getServiceVersion(){
        return android.os.Build.ID;
    }

    //28.获取证书???
    public String getCertificate( int mode ){
        View view = null;
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.KEYCODE_DPAD_UP || event.getAction() == KeyEvent.KEYCODE_DPAD_DOWN){
                    return false;
                }
                return true;
            }
        });
        return null;
    }

    //29.获取电池充电次数
    public String getBatteryChargingTimes(){
        return null;
    }

    /**
     *获取设备状态
     * * <ul>
     * * <li> CAMERA_FRONT (string) – 照相机</li>
     * * <li> CAMERA_REAR (string) – 照相机</li>
     * * <li> SDCARD (string) – SD卡</li>
     * * <li> ICCARDREADER_SLOT1 (string) – IC SLOT1</li>
     * * <li> ICCARDREADER_SLOT2 (string) – IC SLOT2</li>
     * * <li> RFCARDREADER (string) – 非接读卡器</li>
     * * <li> SAMCARDREADER_SLOT1 (string) – SAM1</li>
     * * <li> SAMCARDREADER_SLOT2 (string) – SAM2</li>
     * * <li> PINPAD (string) – 密码键盘</li>
     * * <li> PRINTER (string) – 打印机</li>
     * * </ul>
     * @param bundle
     * @return 0 - 工作状态正常，非0 - 工作状态异常
     */
    public int getDeviceStatus(Bundle bundle){
        return 0;
    }

    //31.获取纽扣电池的电量
    public String getButtonBatteryVol(){
        return null;
    }

    /**
     * 获取设备信息
     * @return
     * * <ul>
     *      *      <li>SN (String) - Serial No </li>
     *      *      <li>PN (String) - Product No</li>
     *      *      <li>IMSI(String) - IMSI</li>
     *      *      <li>IMEI(String) - IMEI</li>
     *      *      <li>MEID (String) - MEID</li>
     *      *      <li>manufacture (String) - 厂商编号</li>
     *      *      <li>deviceModel (String) - 设备型号</li>
     *      *      <li>androidOsVer (String) - 安卓系统版本号</li>
     *      *      <li>androidKernalVer (String) - 安卓内核版本号</li>
     *      *      <li>romVer (String) - ROM版本号</li>
     *      *      <li>firmwareVer (String) – Firmware版本号</li>
     *      *      <li>hardwareVer (String) - 硬件版本号</li>
     *      *      <li>SPVer (String) - 安全芯片软件版本号</li>
     *      *      <li>VFSerivceVer (String) - 设备服务版本号</li>
     *      *      <li>VRKSn (Stirng) - VRK sn </li>
     *      *      <li>SponsorID (String) - sponsor HashValue</li>
     *       * </ul>
     */
    public Bundle getDeviceInfo(){
        Bundle deviceInfo = new Bundle();
        deviceInfo.putString("SN","SN");
        deviceInfo.toString();
        return deviceInfo;
    }

    /**
     * 获取设备信息扩展
     * @param extrend
     * @return
     ** <ul>
     *      *      <li>SN (String) - Serial No </li>
     *      *      <li>PN (String) - Product No</li>
     *      *      <li>IMSI(String) - IMSI</li>
     *      *      <li>IMEI(String) - IMEI</li>
     *      *      <li>MEID (String) - MEID</li>
     *      *      <li>manufacture (String) - 厂商编号</li>
     *      *      <li>deviceModel (String) - 设备型号</li>
     *      *      <li>androidOsVer (String) - 安卓系统版本号</li>
     *      *      <li>androidKernalVer (String) - 安卓内核版本号</li>
     *      *      <li>romVer (String) - ROM版本号</li>
     *      *      <li>firmwareVer (String) – Firmware版本号</li>
     *      *      <li>hardwareVer (String) - 硬件版本号</li>
     *      *      <li>SPVer (String) - 安全芯片软件版本号</li>
     *      *      <li>VFSerivceVer (String) - 设备服务版本号</li>
     *      *      <li>VRKSn (Stirng) - VRK sn </li>
     *      *      <li>SponsorID (String) - sponsor HashValue</li>
     *      *      <li>SponsorName (String) - sponsor Name </li>
     *      *      <li>bootVer(String) - Boot version </li>
     *      * </ul>
     */
    public Bundle getDeviceInfoEx(Bundle extrend){
        Bundle bundle = new Bundle();
        for(int i =0;i<bundle.size();i++){

        }
        //import java.util.Set;
        Set<String> keys=bundle.keySet();
        if (keys!=null){
            for (String key : keys) {
                if (Integer.parseInt(bundle.getString(key))==0){
                    return bundle;
                }else {
                    break;
                }
            }
        }
        bundle.putString("IMSI",getIMSI());
        bundle.putString("IMEI",getIMEI());
        bundle.putString("MEID",getMEID());
        return bundle;



    }


}
