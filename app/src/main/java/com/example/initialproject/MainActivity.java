package com.example.initialproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<Info> myList = new ArrayList<>();

    public void init() {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("https://news-at.zhihu.com/api/3/news/latest").build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert response != null;
//        assert response.body() != null;
//        String responseData = null;
        String responseData = "{\"date\":\"20181028\",\"stories\":[{\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-e0118472495113cb245898a3eb94317b.jpg\"],\"type\":0,\"id\":9699918,\"ga_prefix\":\"102822\",\"title\":\"小事 · 戒酒有多难\"},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-ddd79b791e7a34ea50d9f1a39d3d49f0.jpg\"],\"type\":0,\"id\":9699257,\"ga_prefix\":\"102821\",\"title\":\"为什么在日本动漫里，女生很少会穿牛仔裤？\"},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-9c2e5d5734e213160f522240aa582209.jpg\"],\"type\":0,\"id\":9699352,\"ga_prefix\":\"102819\",\"title\":\"没事用用微信，真的会让老年人感觉更幸福\"},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-65209d1098a97efab33ec2fe10ba828c.jpg\"],\"type\":0,\"id\":9699560,\"ga_prefix\":\"102818\",\"title\":\"吃一碗白米饭，里面藏着的门道还真不少\"},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-d3e92f5469bee5531201c96414707205.jpg\"],\"type\":0,\"id\":9699683,\"ga_prefix\":\"102817\",\"title\":\"为什么同样是黑白相间，斑马却不是熊猫呢？\"},{\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-813e7687370a9af665453eb461ac7132.jpg\"],\"type\":0,\"id\":9699770,\"ga_prefix\":\"102815\",\"title\":\"那些下落不明的大便，都去哪里了？\"},{\"title\":\"每周一吸 · 烈焰红唇萌兔团子（多图）\",\"ga_prefix\":\"102813\",\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-28489a9ba7502794d84e8eec257d29f8.jpg\"],\"multipic\":true,\"type\":0,\"id\":9699658},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-ae21d2e233b470bd612e0bc9efba2c28.jpg\"],\"type\":0,\"id\":9699877,\"ga_prefix\":\"102812\",\"title\":\"大误 · 牛排 7.769 成熟\"},{\"title\":\"不是青山刚昌江郎才尽，是《柯南》已经走到了尽头（多图）\",\"ga_prefix\":\"102810\",\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-fca4f303957ebbe7b4dd213af629c68f.jpg\"],\"multipic\":true,\"type\":0,\"id\":9699296},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-5ff8699864288bcdb4b9fabc6f7ad8e5.jpg\"],\"type\":0,\"id\":9699628,\"ga_prefix\":\"102808\",\"title\":\"给孙子讲以前的故事……等下，这些记忆中的事可能从未发生过\"},{\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-3536aceca2c2cb7e6a57c587c465572e.jpg\"],\"type\":0,\"id\":9699839,\"ga_prefix\":\"102807\",\"title\":\"这位蘑菇头大叔，描绘出了最浪漫的少女形象\"},{\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-d2fc2e5af3430a7e9084d73e397a3ecf.jpg\"],\"type\":0,\"id\":9699841,\"ga_prefix\":\"102807\",\"title\":\"1 毛钱换不来 30 万\"},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-f34c49b1f213cda7e72c33d75f2fe9a9.jpg\"],\"type\":0,\"id\":9699896,\"ga_prefix\":\"102806\",\"title\":\"瞎扯 · 如何正确地吐槽\"}],\"top_stories\":[{\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-1ab59ae1dd2ea332c3e80566f461dfb3.jpg\",\"type\":0,\"id\":9699841,\"ga_prefix\":\"102807\",\"title\":\"1 毛钱换不来 30 万\"},{\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-eea907b3d77bacb26f2483bb9e2ca61f.jpg\",\"type\":0,\"id\":9699296,\"ga_prefix\":\"102810\",\"title\":\"不是青山刚昌江郎才尽，是《柯南》已经走到了尽头\"},{\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-06af1c4123ec1d62c6534dd20fe6315e.jpg\",\"type\":0,\"id\":9699865,\"ga_prefix\":\"102721\",\"title\":\"即将先于《三体》面世的《流浪地球》，预告片就藏了 3 条线\"},{\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-e1ea1b0076db53752fd472f5b83b2aa2.jpg\",\"type\":0,\"id\":9699785,\"ga_prefix\":\"102707\",\"title\":\"努力是必须努力的，「锦鲤」也是要安排的\"},{\"image\":\"https:\\/\\/pic2.zhimg.com\\/v2-9e0cdf47bd5bcef9f70f2c0efe7eb579.jpg\",\"type\":0,\"id\":9699818,\"ga_prefix\":\"102710\",\"title\":\"迄今为止最高水准的大型开放世界游戏，诞生了\"}]}";
        show(responseData);

    }

    public void show(final String str) {
        Log.d("Impor", str);
        String title;
        Info info;
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("stories"));
            String date = jsonObject.getString("date");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");
                title = object.getString("title");
                int type = object.getInt("type");
                info = new Info(title, R.drawable.temp, type, id, date);
                myList.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Impor", "The program is running");
        setContentView(R.layout.mainlayout);
        init();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        InfoAdapter adapter = new InfoAdapter(myList);
        recyclerView.setAdapter(adapter);

    }
}
