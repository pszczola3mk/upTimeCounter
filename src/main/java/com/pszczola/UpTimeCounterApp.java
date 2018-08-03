package com.pszczola;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpTimeCounterApp {

	public static String fileName = "times.txt";
	public static String propfileName = "props.txt";

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String today = sdf.format(new Date());
		int todayMinutes = 0;
		File f = new File(UpTimeCounterApp.fileName);
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			Stream<String> stream = Files.lines(Paths.get(UpTimeCounterApp.fileName));
			List<String> lines = stream.collect(Collectors.toList());
			Optional<String> todayLine = lines.stream().filter(l -> l.contains(today)).findFirst();
			if (todayLine.isPresent()) {
				todayMinutes = Integer.parseInt(todayLine.get().split(";")[1]);
			} else {
				lines.add(0, today + ";0");
			}
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new UpTimeCounterTask(today, todayMinutes, lines), 0, 1, TimeUnit.MINUTES);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
