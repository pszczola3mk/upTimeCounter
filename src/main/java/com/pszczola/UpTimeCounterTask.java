package com.pszczola;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UpTimeCounterTask implements Runnable {

	private String today;
	private int todayMinutes;
	private List<String> lines;


	public UpTimeCounterTask(String today, int todayMinutes, List<String> lines) {
		this.today = today;
		this.todayMinutes = todayMinutes;
		this.lines = lines;
	}

	@Override
	public void run() {
		this.todayMinutes = this.todayMinutes + 1;
		this.lines.remove(0);
		this.lines.add(0, this.today + ";" + this.todayMinutes);
		try {
			Files.write(Paths.get(UpTimeCounterApp.fileName), this.lines);
			int maxMin = Integer.parseInt(PropsReader.readProp("maxMin"));
			if (this.todayMinutes == maxMin) {
				MailSender.sendMail(this.today, this.todayMinutes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
