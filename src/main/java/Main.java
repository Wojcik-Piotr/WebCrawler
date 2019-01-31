import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebCrawler webCrawler=new WebCrawler();
        webCrawler.bfsAlgorithm("https://www.ssaurel.com/blog");
        webCrawler.showResults();
    }
}
