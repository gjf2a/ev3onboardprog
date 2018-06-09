package edu.hendrix.ev3onboardprog.colorcluster;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.colorcluster.bsoc.BoundedSelfOrgCluster;
import edu.hendrix.ev3onboardprog.ui.IntRow;
import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import edu.hendrix.ev3onboardprog.vision.BasicVisionBot;
import edu.hendrix.ev3onboardprog.vision.BinaryImage;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.video.YUYVImage;

public class ColorClusterer extends BasicVisionBot {
	public static void main(String[] args) throws IOException {
		IntRow numClusters = new IntRow("# Clusters", 2, 1);
		numClusters.display(3);
		while (Button.ESCAPE.isUp() && Button.ENTER.isUp()) {
			for (Key button: new Key[]{Button.LEFT, Button.RIGHT, Button.UP, Button.DOWN}) {
				UIFuncs.checkAndUse(button, () -> {
					numClusters.update(button == Button.DOWN || button == Button.RIGHT);
					numClusters.display(3);
				});
			}
		}
		while (Button.ESCAPE.isDown() || Button.ENTER.isDown());
		
		ColorClusterer runner = new ColorClusterer(numClusters.get());
		runner.run();
	}
	
	private BoundedSelfOrgCluster<ColorCluster,ColorCluster> bsoc;
	private Mode mode;
	private int targetCluster;

	public ColorClusterer(int numColors) throws IOException {
		super();
		bsoc = new BoundedSelfOrgCluster<>(numColors, ColorCluster::distance, i -> i);
		mode = Mode.TRAIN;
		targetCluster = 0;
	}

	@Override
	public void proc(YUYVImage img) {
		UIFuncs.checkAndUse(Button.ESCAPE, () -> mode = mode.next());
		switch (mode) {
		case TRAIN: train(img); break;
		default: apply(img);
		}
	}
	
	public void train(YUYVImage img) {
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				bsoc.train(new ColorCluster(img, x, y));
			}
		}
	}

	public void apply(YUYVImage img) {
		BinaryImage bin = new BinaryImage(img, (i, x, y) -> bsoc.getClosestMatchFor(new ColorCluster(i, x, y)) == targetCluster);
		bin.draw();
	}
}
