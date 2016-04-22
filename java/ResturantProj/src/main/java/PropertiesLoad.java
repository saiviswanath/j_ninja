import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoad {
	public static Properties getProperties() throws RuntimeException {
		InputStream in = PropertiesLoad.class.getClassLoader()
				.getResourceAsStream("props.properties");
		if (in == null) {
			throw new RuntimeException("props.properties not defined");
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException("Could not load props.properties");
		}
		return props;
	}
}
