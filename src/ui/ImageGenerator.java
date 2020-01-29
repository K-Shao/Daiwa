package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageGenerator {

	public static void generateImage(Report report) throws IOException {

		final BufferedImage image = ImageIO.read(new File("res/template.png"));

		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(18f));
		drawText(g, report);
		g.dispose();
		
		JFrame frame = new JFrame ();
		frame.add(new JLabel(new ImageIcon(image)));
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ImageIO.write(image, "png", new File("res/" + report.getDate() + ".png"));

	}

	private static void drawText(Graphics g, Report report) {
		int y = 290;
		for (Entry e: report.getEntries()) {
			
			String [] fields = new String [] {
				Long.toString(e.lot), 
				e.use, 
				e.size1, e.size2, e.size3, 
				e.d1, e.d2, e.d3, e.d4, e.t1, e.t2, e.t3, 
				e.length, 
				Integer.toString(e.bead), Integer.toString(e.flat),
				e.flatSize, 
				Integer.toString(e.bend), Integer.toString(e.appearance), 
				e.time
			};
			int [] x = new int [] {
					100,
					220,
					300, 380, 460, 
					555, 621, 687, 753, 819, 885, 955, 
					1021, 
					1145, 1220,
					1315, 
					1365, 1440, 
					1545
			};
			boolean [] isBool = new boolean [] {
					false, 
					false, 
					false, false, false, 
					false, false, false, false, false, false, false, 
					false, 
					true, true, 
					false, 
					true, true, 
					false
			};
			
			
			for (int i = 0; i < fields.length; i++) {
				if (fields[i] == null) {
					continue;
				}
				if (isBool[i]) {
					g.drawOval(fields[i].equals("1")?x[i]:x[i]+50, y-20, 25, 25);
				} else {
					g.drawString(fields[i],x[i], y);
				}
			}
			y+=40;
		}
		

	}

}
