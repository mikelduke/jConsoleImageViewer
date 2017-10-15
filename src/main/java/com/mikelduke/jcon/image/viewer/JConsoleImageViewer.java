package com.mikelduke.jcon.image.viewer;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.tw.pi.framebuffer.FrameBuffer;

public class JConsoleImageViewer {
	private static FrameBuffer fb;
	
	public static void main(String[] args) {
		boolean useDummy = false;
		
		if (args.length == 0) {
			System.err.println("Error missing filename");
			return;
		} else if (args.length > 2 && args[1].equalsIgnoreCase("dummy")) {
			useDummy = true;
		}
		
		final boolean fUseDummy = useDummy;
		final String file = args[0];
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				if (fUseDummy) {
					fb = new FrameBuffer("dummy_200x330");
					
					JFrame f = new JFrame("Frame Buffer Test");
					f.setSize(400, 400);
					f.setLocation(300,200);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.getContentPane().add(BorderLayout.CENTER, fb.getScreenPanel());
					f.setVisible(true);
				} else {
					fb = new FrameBuffer("/dev/fb1");
				}

				viewImage(file);
			}
		});
	}

	protected static void viewImage(String fileName) {
		try {
			File file = new File(fileName);
			
			if (!file.exists()) {
				System.err.println("File does not exist: " + file.getAbsolutePath());
				System.exit(1);
			} else if (!file.canRead()) {
				System.err.println("Unable to read file: " + file.getAbsolutePath());
				System.exit(1);
			}
			
			System.out.println("Opening " + file.getAbsolutePath());
			BufferedImage img = ImageIO.read(new FileInputStream(file));
			
			BufferedImage screen = fb.getScreen();
			int w = screen.getWidth();
			int h = screen.getHeight();
			
			System.out.println("Scaling image from " + img.getWidth() + "x" + img.getHeight() + " to " + w + "x" + h);
			Image scaledImage = img.getScaledInstance(w, h, BufferedImage.SCALE_FAST);

			System.out.println("Displaying " + fileName);
			Graphics2D g = screen.createGraphics();
			g.drawImage(scaledImage, 0, 0, w, h, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
}
