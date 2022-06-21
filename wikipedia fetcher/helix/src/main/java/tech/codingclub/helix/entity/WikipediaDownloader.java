package tech.codingclub.helix.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WikipediaDownloader {
    private String keyword;
    private String result;
    private String imgur;
    public WikipediaDownloader(String keyword) {
        this.keyword = keyword;
    }



    public WikiResults getResult(){
        if(this.keyword==null||this.keyword.length()==0) return null;
        this.keyword = this.keyword.replaceAll("[ ]","_");
        String wikiurl = getwikipediaurl(this.keyword);
        String wikiresponse = null;

        String response = null;
        try {
            wikiresponse = HttpUrlRequest.sendGet(wikiurl);

            Document document = Jsoup.parse(wikiresponse,"https://en.wikipedia.org");
            Elements childelement=document.body().select(".mw-parser-output >*");
            int state=0;
            try {
                for (Element child : childelement) {
                    if (state == 0) {
                        if (child.tagName().equals("table")) {
                            state = 1;
                        }

                    }
                    if (state == 1) {
                        if (child.tagName().equals("p")) {
                            state = 2;
                            response = child.text();
                            break;
                        }
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            this.result=response;
            String imgurl = document.body().select(".infobox img").attr("src");
            WikiResults wikiResults;

            if(imgurl.startsWith("//")){
                imgurl="http:"+imgurl;
            }
            this.imgur = imgurl;
            wikiResults = new WikiResults(this.keyword,this.result,this.imgur);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return wikiResults;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
    public static String getwikipediaurl(String keyw){
        return "https://en.wikipedia.org/wiki/"+keyw;
    }


}
