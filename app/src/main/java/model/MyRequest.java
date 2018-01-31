package model;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyRequest {

    private OkHttpClient mHttpClient;

    private String sestoken;

    private ArrayList<String> result;

    private void getSesToken() throws IOException {
        final Request request = new Request.Builder()
                .url("https://wanted.mvs.gov.ua/searchtransport/")
                .build();


        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("GET", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("GET", "OK");
                String page = response.body().string();

                Document doc = Jsoup.parse(page);
                Elements elements = doc.getElementsByAttributeValue("name","sestoken");
                sestoken = elements.attr("value");
            }
        });
    }


    private String buildContent(String NOM, String NSH, String sestoken) {
        return "NOM=" + NOM + "&NSH=" + NSH + "&sestoken=" + sestoken;
    }

    private void post(String sestoken, String content) throws IOException{
        RequestBody formBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), content);

        Request request = new Request.Builder()
                .addHeader("Cookie", "PHPSESSID=" + sestoken)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url("https://wanted.mvs.gov.ua/searchtransport/result")
                .post(formBody)
                .build();

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("POST", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("POST", "Failure");

                String page = response.body().string();

                Document doc = Jsoup.parse(page);
                Elements elements = doc.getElementsByTag("li");

                result = new ArrayList<>();

                for (Element current : elements) {
                    try {
                        result.add(current.childNode(1).childNode(0).toString());
                    } catch (Exception e) {
                        result.add("no info");
                    }
                }
            }
        });


        //return  response.body().string();
    }

    public void execute(String nom, String nsh) throws IOException {
        getSesToken();
        post(sestoken, buildContent(nom, nsh, sestoken));
    }

    public MyRequest() {
        mHttpClient = new OkHttpClient();
    }


    public ArrayList<String> getResult() {
        return result;
    }

}
