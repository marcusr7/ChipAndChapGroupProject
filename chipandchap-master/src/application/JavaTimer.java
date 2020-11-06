package application;

import java.awt.Frame;
import java.awt.Graphics;
import java.util.IllegalFormatCodePointException;
import java.util.Timer;
import java.util.TimerTask;

import maze.Maze;
import renderer.Renderer;

import javax.swing.*;

public class JavaTimer {
	private final int defaultTime = 300;
	private int timeLeft = defaultTime;
	private Timer timer = new Timer();
	/**
	 * Simple timer which will handle the rendering of movement of the game board.
	 * Also used for the count down timer of the game.
	 * @param chipandchap the main game state
	 * @param tickRate the rate at which the timer will call the 'run' method
	 * @param countdown is this a count down timer or a tick timer for game rendering.
	 * @param render used to set the time left for the count down.
	 */
	public JavaTimer(ChipAndChap chipandchap, int tickRate, boolean countdown, Renderer render, JavaTimer otherTimer) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (chipandchap.isRunning()) {
					if (countdown) {
						timeLeft--;
						render.setTimeLeft(timeLeft);
						chipandchap.getMaze().moveMonsters();
						if (timeLeft == 0) {
							timer.cancel();
							timer.purge();
							ChipAndChap.setTimeOver();
							JOptionPane.showMessageDialog(null, "Time has ran out!");
							System.exit(0);
						}

					} else {
						if(chipandchap.getFrame() != null) {
							chipandchap.getFrame().repaint();
						}
					}
				}
			}
		};
		timer.schedule(task, 1, tickRate);
	}

	/**
	 * Simple getter to return the time for the current timer.
	 * @return the time left for the countdown.
	 */
	public int getTime() {
		return timeLeft;
	}

	/**
	 * Stops the timer from running (cancels scheduled tasks)
	 */
	public void stopTime() {
		timer.cancel();
		timer.purge();
	}

	/**
	 * Sets the time left field
	 * @param timeLeft int indicating time left
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * Returns the time left
	 * @return int Amount of time left
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * Returns the default time
	 * @return int Default amount of time
	 */
	public int getDefaultTime() {
		return defaultTime;
	}

	/**
	 * Method will change the timer's value
	 * back to the default value when changing levels.
	 */
	public void resetCountdown(int timeToSetTo) {
		if(timeLeft < defaultTime-10) {
			timeLeft = timeToSetTo;
		}
	}
}
