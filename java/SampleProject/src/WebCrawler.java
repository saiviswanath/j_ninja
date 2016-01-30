import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Sample
 * 
 * @author viswa
 *
 */
public class WebCrawler {
  private static String site;
  private static int depth = 1;
  private static Integer dCounter = 0;
  private static int numPages;
  private static int timeoutInSecs;
  private static Set<String> urls = new HashSet<String>();

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    if (args.length < 1) {
      System.out.println("Usage:"); // TODO
    }

    site = args[0];
    if (args.length == 2) {
      depth = Integer.parseInt(args[1]);
    }

    if (args.length == 3) {
      numPages = Integer.parseInt(args[2]);
      startNumPagesThread();
    }

    if (args.length == 4) {
      timeoutInSecs = Integer.parseInt(args[3]);
      startTimeoutThread();
    }

    crawl();

    for (String url : urls) {
      System.out.println(url);
    }
    System.out.println(urls.size());
    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime));
  }

  private static void startTimeoutThread() {
    new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          Thread.sleep(timeoutInSecs * 1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("Timeout occurred. So stopping crawl...");
        System.exit(0);
      }
    }).start();
  }

  private static void startNumPagesThread() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          if (urls.size() == numPages) {
            System.out.println("Max Pages crawled. So stopping crawl...");
            System.exit(0);
          }

          try {
            Thread.sleep(20000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  private static void crawl() {
    Set<String> parentPageLinks = parsePageForLinks(site);
    dCounter++;

    if (depth == 1) {
      urls.addAll(parentPageLinks);
      return;
    } else {
      urls.addAll(parentPageLinks);
    }

    AtomicInteger ai = new AtomicInteger(0);
    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
            new ThreadFactory() {
              @Override
              public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setPriority(Thread.NORM_PRIORITY);
                t.setName("Current Thread: " + ai.incrementAndGet());
                t.setDaemon(false);
                return t;
              }
            });

    multiCrawl(executor, parentPageLinks);

    executor.shutdown();
    try {
      if (executor.awaitTermination(1, TimeUnit.HOURS)) {
        throw new RuntimeException("Thread Pool Stutdown");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void multiCrawl(ThreadPoolExecutor executor, Set<String> links) {
    if (depth == dCounter) {
      return;
    } else {
      dCounter++;
      for (String link : links) {
        executor.execute(new Runnable() {
          @Override
          public void run() {
            Set<String> lks = parsePageForLinks(link);
            synchronized (urls) {
              if (lks != null && !lks.isEmpty()) {
                urls.addAll(lks);
              }
            }
            if (depth < dCounter) {
              multiCrawl(executor, lks); // TODO: validate on depth > 1
            }
          }
        });
      }
    }
  }

  /*
   * 
   */
  private static synchronized Set<String> parsePageForLinks(String url) {
    Set<String> links = new HashSet<String>();
    Document doc = null;

    try {
      new URL(url); // For validation purpose mainly malformed urls
      doc = Jsoup.connect(url).get();
    } catch (MalformedURLException e2) {
      System.out.println(url + " is malformed");
      url = site + url; // Possible prefix as site
      try {
        doc = Jsoup.connect(url).get();
      } catch (HttpStatusException e) {
        System.out.println(e.getMessage());
      } catch (UnknownHostException e1) {
        System.out.println(e1.getMessage());
      } catch (IOException e3) {
        e3.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    Elements hrefEle = doc.select("a"); // TODO:Fix all possible NPEs
    if (hrefEle == null) {
      return null;
    }
    for (Element ele : hrefEle) {
      links.add(ele.attr("href"));
    }
    return links;
  }
}
