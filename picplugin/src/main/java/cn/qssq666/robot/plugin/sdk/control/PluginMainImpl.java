package cn.qssq666.robot.plugin.sdk.control;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.Random;

import cn.qssq666.plugindemo.SimplePluginInterfaceWrapper;
import cn.qssq666.robot.plugin.sdk.interfaces.IMsgModel;

/**
 * Created by qssq on 2018/7/19 qssq666@foxmail.com
 */
//cn.qssq666.robot.plugin.sdk.control.PluginMainImpl
public class PluginMainImpl extends SimplePluginInterfaceWrapper {
    private static final String TAG = "PluginMainImpl";
String    mLastMsg="";

    @Override
    public String getAuthorName() {
        return "情随事迁";
    }

    @Override
    public int getVersionCode() {
        return 1;
    }

    @Override
    public String getBuildTime() {
        return "2018-07-19 22:14:48";
    }

    @Override
    public String getVersionName() {
        return "1.0";
    }

    @Override
    public String getPackageName() {
        return "cn.qssq666.seekpic";
    }

    @Override
    public String getDescript() {
        return "看本地存储中美女帅哥的框架,/sdcard/pic[girl,boy,other三个文件夹]";
    }

    @Override
    public String getPluginName() {
        return "大自然艺术欣赏";
    }

    @Override
    public boolean isDisable() {
        return false;
    }

    @Override
    public void setDisable(boolean disable) {

    }

    @Override
    public boolean onReceiveMsgIsNeedIntercept(IMsgModel item) {
        mLastMsg=item.getMessage();
        if (getControlApi().isGroupMsg(item)) {

            if (getConfigApi().isEnableGroupMsg() && getConfigApi().isAtGroupWhiteNames(item)) {
                return doLogic(item);
            }
        } else {

            if (getControlApi().isPrivateMsg(item) && getConfigApi().isEnablePrivateReply()) {

                return doLogic(item);


            }

        }

        return false;
    }

    private boolean doLogic(IMsgModel item) {
        String message = item.getMessage();



        //todo

        if (message != null && message.equals("美女")) {

            File file = new File("/sdcard/girl");

            if (file.isDirectory()) {
                File[] list = file.listFiles();
                if (list != null && list.length > 0) {

                    int i = new Random().nextInt(list.length);
                    String picPath = list[i].getAbsolutePath();
                    getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);
                        return true;
                } else {
//                    Toast.makeText(getContext(), "无法发送图片", Toast.LENGTH_SHORT).show();
                }

            } else {
//                Toast.makeText(getContext(), "无法发送图片", Toast.LENGTH_SHORT).show();
            }
            getControlApi().sendQQMsg(item.setMessage("无法发送图片,因为/sdcard/girl文件夹里面没有图片."));
            return true;

        } else if (message != null && message.equals("帅哥")) {


            File file = new File("/sdcard/boy");


            if (file.isDirectory()) {
                File[] list = file.listFiles();
                if (list != null && list.length > 0) {

                    int i = new Random().nextInt(list.length);
                    String picPath = list[i].getAbsolutePath();
                             /*   if(APIControl.getApiControlListener()!=null) {
                                    APIControl.getApiControlListener().requestCall(1, QQEngine.sSelfuin, QQEngine.sFrienduin, QQEngine.sSenderuin, QQEngine.sIsStropGroup, picPath);
                                }*/
                    getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);
                    return true;
                } else {
                     getControlApi().sendQQMsg(item.setMessage("无法发送图片,因为/sdcard/boy文件夹里面没有图片"));
                }

            } else {
//                Toast.makeText(getContext(), "无法发送图片", Toast.LENGTH_SHORT).show();
            }

            getControlApi().sendQQMsg(item.setMessage("无法发送图片,因为/sdcard/boy文件夹里面没有图片"));
            return true;

        } else if (message != null && message.equals("看图")) {


            File file = new File("/sdcard/pic");

            if (file.isDirectory()) {
                File[] list = file.listFiles();
                if (list != null && list.length > 0) {

                    int i = new Random().nextInt(list.length);
                    String picPath = list[i].getAbsolutePath();
//                            sendPicMsg(getContextClassLoader(), QQEngine.sSelfuin, QQEngine.sFrienduin, QQEngine.sSenderuin, QQEngine.sIsStropGroup, picPath);
                              /*  if(APIControl.getApiControlListener()!=null) {
                                    APIControl.getApiControlListener().requestCall(1, QQEngine.sSelfuin, QQEngine.sFrienduin, QQEngine.sSenderuin, QQEngine.sIsStropGroup, picPath);
                                }*/

                    getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);


                    Log.w(TAG, "SENDPIC" + picPath);
                    return true;

                } else {
                    getControlApi().sendQQMsg(item.setMessage("无法发送图片,因为/sdcard/pic文件夹里面没有图片"));
                }

            } else {
                getControlApi().sendQQMsg(item.setMessage("无法发送图片,因为/sdcard/pic文件夹里面没有图片"));
            }

        }


        return false;
    }

    @Override
    public boolean onReceiveOtherIntercept(IMsgModel item, int type) {
        return false;
    }


    @Override
    public void onDestory() {

    }


    @Override
    public View onConfigUi(ViewGroup viewGroup) {
        TextView textView = new TextView(viewGroup.getContext());
        textView.setText("使用方法\n发送 看美女 或者看帅哥,或者看图 就能欣赏图片啦,前提是需要在/sdcard/pic文件夹放入 boy ,girl other文件夹 然后分别放入对应的图片哦,本程序插件免费开源!欢迎各位开发新的插件哦!温馨提示,插件不支持自己测试触发,必须借助非机器人qq发送消息测试! \n最后消息:"+mLastMsg);


        return textView;
    }
}
