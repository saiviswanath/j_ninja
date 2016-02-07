import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;


public class PrinterTest {
  private Printer printer = new Printer();

  @SuppressWarnings("unchecked")
  @Test
  public void test() throws Exception {
    Field field = printer.getClass().getDeclaredField("xmlCoordConfigFile");
    field.setAccessible(true);
    field.set(null , "src/test.xml");
    
    Method method = printer.getClass().getDeclaredMethod("readCoordinateXML");
    method.setAccessible(true);
    Object obj2 = method.invoke(null);
    assertNotNull(obj2);
    assertThat(obj2, IsInstanceOf.instanceOf(Map.class));
    Map<Integer, Set<Printer.FieldCoord>> map  = (Map<Integer, Set<Printer.FieldCoord>>) obj2;
    assertNotNull(map);
  }

}
