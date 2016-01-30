import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;
import org.junit.AfterClass;
import org.junit.Test;

public class WebCrawlerTest {
  private WebCrawler crawler = new WebCrawler();

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @SuppressWarnings("unchecked")
  @Test
  public void testParsePageForLinks() throws Exception {

    Method method = crawler.getClass().getDeclaredMethod("parsePageForLinks", String.class);
    method.setAccessible(true);
    Object object = method.invoke(null, "http://www.google.com");
    assertNotNull(object);
    assertThat(object, IsInstanceOf.instanceOf(Set.class));
    Set<String> links = (Set<String>) object;
    assertNotNull(links);
  }

}
