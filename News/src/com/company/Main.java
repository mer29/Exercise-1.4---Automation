package com.company;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    News news = new News();

        // get newses from google news page
        news.getNews();

        // open local news page with data collected from google news
        news.openNewsPage();
    }
}

class News {

    public void getNews() {
        // locate the chrome driver file
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        WebDriver page = new ChromeDriver();

        //open google news page
        page.get("https://news.google.com/?hl=en-ET&gl=ET&ceid=ET:en");

        // get newses on the page
        List<WebElement> news  = page.findElements(By.className("NiLAwe"));

        // store each news in the database
        for (WebElement element: news){
            String title = element.findElement(new By.ByTagName("span")).getText();
            String content = element.findElement(new By.ByTagName("p")).getText();
            String link = element.findElement(new By.ByClassName("VDXfz")).getAttribute("href");
            String img = "";

            // check if the news has image
            if (element.findElements(By.tagName("img")).size()>0){
                img = element.findElement(By.tagName("img")).getAttribute("src");
            }

            System.out.println(title + " \n " + content + "\n image \n"+ img);


            DefaultHttpClient httpClient = new DefaultHttpClient();

            try {
                // trying to store the data collected from google news into the database
                HttpPatch request = new HttpPatch("http://localhost:3000/api/news");
                StringEntity params = new StringEntity("{" +
                        "\"content\":\"" + content +
                        "\",\"title\":\"" + title +
                        "\",\"img\":\"" + img +
                        "\",\"link\"" + ":\"" + link +
                        "\"} ");
                request.addHeader("content-type", "application/json");
                request.addHeader("Accept","application/json");
                request.setEntity(params);
                HttpResponse response = httpClient.execute(request);

                System.out.println(response);
            }catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    public void openNewsPage() {
        // locate the chrome driver file
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        WebDriver page = new ChromeDriver();

        //open local news page
        page.get("http://localhost:4200/");
    }
}
