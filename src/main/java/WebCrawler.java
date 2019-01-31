import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    private Queue<String> queue = new LinkedList<String>();
    private Set<String> marked = new HashSet<String>();
    private String regex = "http[s]*://(\\w+\\.)*(\\w+)";

    public void bfsAlgorithm(String root) throws IOException {
        queue.add(root);
        BufferedReader br = null;

        while (!queue.isEmpty()) {
            String crawledUrl = queue.poll();
            System.out.println("\n=== Side crawled : " + crawledUrl + " ===");

            if (marked.size() > 100) {
                return;
            }

            boolean ok = false;
            URL url = null;

            while (!ok ) {
                try {
                    url = new URL(crawledUrl);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;
                } catch (MalformedURLException e) {
                    System.err.println("*** Malformed URL : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                } catch (IOException ioe) {
                    System.err.println("*** IO Exception : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                }
            }
            StringBuilder sb = new StringBuilder();

            while ((crawledUrl = br.readLine()) != null) {
                sb.append(crawledUrl);
            }
            crawledUrl = sb.toString();

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(crawledUrl);

            while (matcher.find()) {
                String temp = matcher.group();

                if (!marked.contains(temp)) {
                    marked.add(temp);
                    System.out.println("Side added for crawling : " + temp);
                    queue.add(temp);
                }
            }
        }
    }

    public void showResults() {
        System.out.println("\n\nResults : ");
        System.out.println("Web sides crawled : " + marked.size() + "\n");
        for (String s : marked) {
            System.out.println("* " + s);
        }
    }
}
