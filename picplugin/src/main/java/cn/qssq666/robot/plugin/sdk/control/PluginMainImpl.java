package cn.qssq666.robot.plugin.sdk.control;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Random;

import cn.qssq666.plugindemo.SimplePluginInterfaceWrapper;
import cn.qssq666.robot.plugin.sdk.interfaces.IApiCallBack;
import cn.qssq666.robot.plugin.sdk.interfaces.IMsgModel;

/**
 * Created by qssq on 2018/7/19 qssq666@foxmail.com
 */
//cn.qssq666.robot.plugin.sdk.control.PluginMainImpl
public class PluginMainImpl extends SimplePluginInterfaceWrapper {
    private static final String TAG = "PluginMainImpl";

    public File getPicRootdir() {
        return new File("/sdcard/qssq666/pic");
    }

    String mLastMsg = "";

    @Override
    public String getAuthorName() {
        return "情随事迁";
    }

    @Override
    public int getVersionCode() {
        return 2;
    }

    @Override
    public String getBuildTime() {
        return "2018-08-05 00:04:21";
    }

    @Override
    public String getVersionName() {
        return "1.1";
    }

    @Override
    public String getPackageName() {
        return "cn.qssq666.watchkpic";
    }

    @Override
    public String getDescript() {
        return "看本地存储中美女帅哥的框架,/sdcard/qssq666/pic/新建[girl,boy,base,wang,shang]文件夹放入对应图片,支持的命令如美女 帅哥 王思聪 打赏";
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
        mLastMsg = item.getMessage();
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

    private boolean doLogic(final IMsgModel item) {
        String message = item.getMessage();


        //todo

        if (message != null && message.equals("美女")) {

            File file = new File(getPicRootdir(), "girl");

            if (file.isDirectory() && file.list() != null && file.list().length > 0) {
                File[] list = file.listFiles();

                int i = new Random().nextInt(list.length);
                String picPath = list[i].getAbsolutePath();
                getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);
                return true;

            } else {
                getControlApi().sendMsg(item.setMessage("无法发送图片,因为" + file.getAbsolutePath() + "文件夹里面没有图片."));
            }
            return true;

        } else if (message != null && message.equals("帅哥")) {


            File file = new File(getPicRootdir(), "boy");

            if (file.isDirectory() && file.list() != null && file.list().length > 0) {
                File[] list = file.listFiles();

                int i = new Random().nextInt(list.length);
                String picPath = list[i].getAbsolutePath();
                             /*   if(APIControl.getApiControlListener()!=null) {
                                    APIControl.getApiControlListener().requestCall(1, QQEngine.sSelfuin, QQEngine.sFrienduin, QQEngine.sSenderuin, QQEngine.sIsStropGroup, picPath);
                                }*/
                getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);
                return true;

            } else {

                getControlApi().sendMsg(item.setMessage("无法发送图片,因为" + file.getAbsolutePath() + "文件夹里面没有图片"));

            }

            return true;

        } else if (message != null && message.equals("看图")) {


            File file = new File(getPicRootdir(), "base");

            if (file.isDirectory() && file.list() != null && file.list().length > 0) {
                File[] list = file.listFiles();

                int i = new Random().nextInt(list.length);
                String picPath = list[i].getAbsolutePath();

                getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);


                Log.w(TAG, "SENDPIC" + picPath);
                return true;

            } else {

                getControlApi().sendMsg(item.setMessage("无法发送图片,因为" + file.getAbsolutePath() + "文件夹里面没有图片"));

            }

        } else if (message != null && message.startsWith("搜图"))

        {

            final String searchword = message.substring("搜图".length(), message.length());
            if (TextUtils.isEmpty(searchword)) {

                getControlApi().sendMsg(item.setMessage("搜图需要填写名称"));
            } else {

                getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片中,请稍等片刻!"));


                Random random = new Random();
                int randomp = random.nextInt(10000 + new Random().nextInt(30000));
                ;

                String format = String.format("http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=%d&rn=1&p=channel&from=1", randomp);
                getControlApi().sendAsyncGetRequest(format, new IApiCallBack<byte[]>() {
                    @Override
                    public void onSucc(byte[] bytes) {
//                        getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败!! code="+code+",exception:"+e.getMessage()));

                        final String s = new String(bytes);

                        if (s.contains("col") && s.contains("tag")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                JSONArray imgs = jsonObject.getJSONArray("imgs");
                                if (imgs.length() > 0 && !imgs.isNull(0)) {

                                } else {

                                    getControlApi().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败请更换关键词!"));


                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.w(TAG, "无法搜索图片" + searchword + ",结果 服务器出现问题:" + s);
                                getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败 数据格式出现错误!!" + e.getMessage()));

                            }

                        } else {
                            getControlApi().post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.w(TAG, "无法搜索图片" + searchword + ",结果 服务器出现问题:" + s);
                                    getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败 服务器已失效!"));


                                }
                            });
                        }

                        //http://image.baidu.com/data/imgs?col=%E7%BE%8E%E5%A5%B3&tag=%E5%B0%8F%E6%B8%85%E6%96%B0&sort=0&pn=2&rn=2&p=channel&from=1


                    }

                    @Override
                    public void onFail(final int code, final Exception e) {

                        getControlApi().post(new Runnable() {
                            @Override
                            public void run() {
                                getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败!! code=" + code + ",exception:" + e.getMessage()));


                            }
                        });

                    }
                });
            }

            return true;

//            http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=10&rn=10&p=channel&from=1


        } else if (message != null && message.equals("更新美女"))

        {

            getControlApi().sendMsg(item.setMessage("搜索相关美女图片中,请稍等片刻!"));


            Random random = new Random();
            final int randomp = random.nextInt(1000 );
            ;

            String format = String.format("http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=%d&rn=1&p=channel&from=1", randomp);
            getControlApi().sendAsyncGetRequest(format, new IApiCallBack<byte[]>() {
                @Override
                public void onSucc(byte[] bytes) {
//                        getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败!! code="+code+",exception:"+e.getMessage()));

                    final String s = new String(bytes);

                    Log.w(TAG,"net_pic_result:"+s);

                    if (s.contains("col") && s.contains("tag")) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            JSONArray imgs = jsonObject.getJSONArray("imgs");
                            if (imgs.length() > 0 && !imgs.isNull(0)) {

                                //downloadUrl: "http://c.hiphotos.baidu.com/image/pic/item/810a19d8bc3eb135298010bba41ea8d3fd1f446e.jpg",
                                final String downloadUrl = imgs.getJSONObject(0).optString("downloadUrl");
                                if(downloadUrl==null){

                                    getControlApi().post(new Runnable() {
                                        @Override
                                        public void run() {


                                            getControlApi().sendMsg(item.setMessage("搜索相关图片失败!!请再试一次,code="+randomp));


                                        }


                                    });
                                    return;
                                }
                                File dir = new File(getPicRootdir(), "girl");
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                int i = downloadUrl.lastIndexOf(".");
                                String postfix = downloadUrl.substring(i, downloadUrl.length());
                                final File fileSavepATH = new File(dir, "网络美女" + downloadUrl.hashCode() + postfix);
                                final String savePath = fileSavepATH.getAbsolutePath();
                                getControlApi().sendAnsyncDownload(downloadUrl, savePath, new IApiCallBack<Boolean>() {
                                    @Override
                                    public void onSucc(Boolean aBoolean) {

                                        if (fileSavepATH.exists()) {

                                            Log.w(TAG, "下载美女图片" + downloadUrl + "成功,保存的路径:" + savePath);

                                            getControlApi().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), savePath);

                                                }

                                            });
                                        } else {
                                            getControlApi().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getControlApi().sendMsg(item.setMessage("下载美女图片失败! 无法得知原因!\n图片地址:" + downloadUrl + "\n保存路径:" + savePath));


                                                }
                                            });
                                    }

                                }

                                @Override
                                public void onFail ( final int code, final Exception e){
                                    getControlApi().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            getControlApi().sendMsg(item.setMessage("下载美女图片失败!\n图片地址:" + downloadUrl + "\n保存路径:" + savePath + "\n错误原因 code=" + code + ",message=" + e.getMessage()));


                                        }
                                    });
                                }
                            });


                        }else{

                            getControlApi().post(new Runnable() {
                                @Override
                                public void run() {
                                    getControlApi().sendMsg(item.setMessage("搜索相关图片失败请更换关键词!"));


                                }
                            });

                        }
                    } catch(JSONException e){
                        e.printStackTrace();
                        Log.w(TAG, "无法搜索图,结果 服务器出现问题:" + s);
                        getControlApi().sendMsg(item.setMessage("搜索相关图片失败 数据格式出现错误!!" + e.getMessage()));

                    }

                }else

                {
                    getControlApi().post(new Runnable() {
                        @Override
                        public void run() {
                            Log.w(TAG, "无法搜索图片,结果 服务器出现问题:" + s);
                            getControlApi().sendMsg(item.setMessage("搜索相关图片失败 服务器已失效!"));


                        }
                    });
                }

                //http://image.baidu.com/data/imgs?col=%E7%BE%8E%E5%A5%B3&tag=%E5%B0%8F%E6%B8%85%E6%96%B0&sort=0&pn=2&rn=2&p=channel&from=1


            }

            @Override
            public void onFail ( final int code, final Exception e){

            getControlApi().post(new Runnable() {
                @Override
                public void run() {
                    getControlApi().sendMsg(item.setMessage("搜索相关图片失败!! code=" + code + ",exception:" + e.getMessage()));


                }
            });

        }
        });

        return true;

//            http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=10&rn=10&p=channel&from=1


    } else if(message.equals("打赏"))

    {


        File file = new File(getPicRootdir(), "shang");

        if (file.isDirectory() && file.list() != null && file.list().length > 0) {
            File[] list = file.listFiles();

            int i = new Random().nextInt(list.length);
            String picPath = list[i].getAbsolutePath();

            getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);


            Log.w(TAG, "shang" + picPath);
            return true;

        } else {

            getControlApi().sendMsg(item.setMessage("无法发送图片,因为" + file.getAbsolutePath() + "文件夹里面没有图片"));

        }

    }else if(message.equals("王思聪"))

    {

        File file = new File(getPicRootdir(), "wang");

        if (file.isDirectory() && file.list() != null && file.list().length > 0) {
            File[] list = file.listFiles();

            int i = new Random().nextInt(list.length);
            String picPath = list[i].getAbsolutePath();

            getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);


            Log.w(TAG, "wang" + picPath);
            return true;

        } else {

            getControlApi().sendMsg(item.setMessage("无法发送图片,因为" + file.getAbsolutePath() + "文件夹里面没有图片"));

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
        textView.setText("使用方法\n发送 看美女 或者看帅哥,或者看图 就能欣赏图片啦,前提是需要在/sdcard/pic文件夹放入 boy ,girl other文件夹 然后分别放入对应的图片哦,本程序插件免费开源!欢迎各位开发新的插件哦!温馨提示,插件不支持自己测试触发,必须借助非机器人qq发送消息测试! \n最后消息:" + mLastMsg);


        return textView;
    }
}
