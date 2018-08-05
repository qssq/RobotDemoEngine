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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

        if (message.equals("美女")) {

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

        } else if (message.equals("帅哥")) {


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

        } else if (message.equals("看图")) {


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

        } else if (message.startsWith("搜图"))

        {
            //https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=girl&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%E5%B8%85%E5%93%A5&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn=210&rn=30&gsm=d2&153345

            final String searchword = message.substring("搜图".length(), message.length());
            if (TextUtils.isEmpty(searchword)) {

                getControlApi().sendMsg(item.setMessage("搜图需要填写名称"));
            } else {


                Random random = new Random();
                final int randompage = random.nextInt(300);
                ;

//                String format = String.format("https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=%s&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn=%d&rn=1&gsm=d2&153345", searchword,searchword,randomp);

                String format = "https://image.baidu.com/searc" +
                        "h/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=resu" +
                        "lt&queryWord=" + encodeParam(searchword) + "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-" +
                        "1&z=&ic=&word=" + encodeParam(searchword) + "&s=&" +
                        "se=&tab=&width=&height=&face=&istype=&qc=&nc=1&fr=&pn" +
                        "=" + randompage + "&rn=1&gsm=78&1533453669527";
                Log.w(TAG, "处理之后的图片:" + format);
                getControlApi().sendAsyncGetRequest(format, new IApiCallBack<byte[]>() {
                    @Override
                    public void onSucc(byte[] bytes) {
//                        getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败!! code="+code+",exception:"+e.getMessage()));

                        final String s = new String(bytes);


                        Log.w(TAG, "search " + searchword + " result :" + s);
                        if (s.contains("listNum") && s.contains("queryEnc")) {
                            try {
                                final JSONObject jsonObject = new JSONObject(s);

                                JSONArray imgs = jsonObject.getJSONArray("data");


                                if (imgs.length() > 0 && !imgs.isNull(0)) {


                                    getControlApi().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            String bdFmtDispNum = jsonObject.optString("bdFmtDispNum");
                                            getControlApi().sendMsg(item.setMessage("正在下载" + searchword + ""  + "图片," + bdFmtDispNum + "张图片,随机从第一个列表中取第" + randompage + "张"));
                                        }
                                    });
                                    //hoverURL
                                    JSONObject jsonObjectFirst = imgs.getJSONObject(0);
                                    final String downloadUrl = jsonObjectFirst.optString("hoverURL");


                                    downloadPic(downloadUrl, item, "other",searchword, randompage);


                                } else {

                                    getControlApi().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败请更换关键词!"));


                                        }
                                    });
//hoverURL
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
            final int randomp = random.nextInt(1000);
            ;

            String format = String.format("http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=%d&rn=1&p=channel&from=1", randomp);
            getControlApi().sendAsyncGetRequest(format, new IApiCallBack<byte[]>() {
                @Override
                public void onSucc(byte[] bytes) {
//                        getControlApi().sendMsg(item.setMessage("搜索" + searchword + "相关图片失败!! code="+code+",exception:"+e.getMessage()));

                    final String s = new String(bytes);

                    Log.w(TAG, "net_pic_result:" + s);

                    if (s.contains("col") && s.contains("tag")) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            JSONArray imgs = jsonObject.getJSONArray("imgs");
                            if (imgs.length() > 0 && !imgs.isNull(0)) {

                                //downloadUrl: "http://c.hiphotos.baidu.com/image/pic/item/810a19d8bc3eb135298010bba41ea8d3fd1f446e.jpg",
                                final String downloadUrl = imgs.getJSONObject(0).optString("downloadUrl");
                                downloadPic(downloadUrl, item, "girl","网络美女", randomp);


                            } else {

                                getControlApi().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        getControlApi().sendMsg(item.setMessage("搜索相关图片失败请更换关键词!"));


                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.w(TAG, "无法搜索图,结果 服务器出现问题:" + s);
                            getControlApi().sendMsg(item.setMessage("搜索相关图片失败 数据格式出现错误!!" + e.getMessage()));

                        }

                    } else

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
                public void onFail(final int code, final Exception e) {

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


        } else if (message.equals("打赏"))

        {


            File file = new File(getPicRootdir(), "shang");

            if (file.isDirectory() && file.list() != null && file.list().length > 0) {
                File[] list = file.listFiles();

//            int i = new Random().nextInt(list.length);
                int shang_index = getControlApi().readIntConfig("shang_index", -1);
                if (shang_index >= list.length) {

                    shang_index = -1;
                } else {
                    shang_index++;
                }


                String picPath = list[shang_index].getAbsolutePath();

                getControlApi().sendPicMsg(item, item.getFrienduin(), item.getSenderuin(), picPath);

                getControlApi().writeConfig("shang_index", shang_index);
                Log.w(TAG, "shang" + picPath);
                return true;

            } else {

                getControlApi().sendMsg(item.setMessage("无法发送图片,因为" + file.getAbsolutePath() + "文件夹里面没有图片"));

            }

        } else if (message.equals("王思聪"))

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

    private void downloadPic(final String downloadUrl, final IMsgModel item, String childdir, String name,final int randomp) {
        if (downloadUrl == null) {

            getControlApi().post(new Runnable() {
                @Override
                public void run() {


                    getControlApi().sendMsg(item.setMessage("搜索相关图片失败!!请再试一次,code=" + randomp));


                }


            });
            return;
        }
        File dir = new File(getPicRootdir(), childdir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int i = downloadUrl.lastIndexOf(".");
        String postfix = downloadUrl.substring(i, downloadUrl.length());
        final File fileSavepATH = new File(dir, name+"" + downloadUrl.hashCode() + postfix);
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
            public void onFail(final int code, final Exception e) {
                getControlApi().post(new Runnable() {
                    @Override
                    public void run() {
                        getControlApi().sendMsg(item.setMessage("下载美女图片失败!\n图片地址:" + downloadUrl + "\n保存路径:" + savePath + "\n错误原因 code=" + code + ",message=" + e.getMessage()));


                    }
                });
            }
        });
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

    public static String encodeParam(String word) {
        try {
            return URLEncoder.encode(word, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "毛主席";
    }
}
