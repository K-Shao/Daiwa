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
			try {
				g.drawString(Integer.toString(e.lot), 100, y);
				g.drawString(e.use, 220, y);
				g.drawString(e.size1, 300, y);
				g.drawString(e.size2, 380, y);
				g.drawString(e.size3, 460, y);
				g.drawString(e.d1, 555, y);
				g.drawString(e.d2, 621, y);
				g.drawString(e.d3, 687, y);
				g.drawString(e.d4, 753, y);
				g.drawString(e.t1, 819, y);
				g.drawString(e.t2, 885, y);
				g.drawString(e.t3, 955, y);
				g.drawString(e.length, 1021, y);
				g.drawOval(e.bead==1?1145:1195, y-20, 30, 30);
				g.drawOval(e.flat==1?1225:1270, y-20, 30, 30);
				g.drawString(e.flatSize, 1315, y);
				g.drawOval(e.bend==1?1365:1415, y-20, 30, 30);
				g.drawOval(e.appearance==1?1440:1490, y-20, 30, 30);
				g.drawString(e.time, 1545, y);
				
			} catch (NullPointerException ex) {
				
			}

			y+=40;
		}
		

	}

}
