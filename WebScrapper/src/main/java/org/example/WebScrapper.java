package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WebScrapper {
    public static void main(String[] args) {
        String baseUrl = "https://www.bookswagon.com/promo-best-seller/best-seller/03AC998EBDC2";
        int maxPages = 5;

        try {
            FileWriter fileWriter = new FileWriter("books_data.csv");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("Title,Author,Price,Rating\n");

            for (int i = 1; i <= maxPages; i++) {
                String url = baseUrl + "?page=" + i;
                scrapPage(url, writer);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void scrapPage(String url, BufferedWriter writer) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements books = document.select(".card-body");

        for (Element book : books) {
            String title = book.select(".booktitle").text();
            title = "\"" + title + "\"";

            String author = book.select(".author").text();
            author = "\"" + author + "\"";

            String priceWithSymbol = book.select(".actualprice").text();
            String price = priceWithSymbol.replaceAll("[^0-9.]+", "");

            String rating = book.select(".averageratingbox").text();

            writer.write(title + "," + author + "," + price + "," + rating + "\n");


            System.out.println(title + "- " + author);
            System.out.println(price);
            System.out.println(rating);
        }
    }
}
