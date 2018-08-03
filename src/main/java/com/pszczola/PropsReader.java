package com.pszczola;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropsReader {

	public static String readProp(String key){
		try {
			File f = new File(UpTimeCounterApp.propfileName);
			if (!f.exists()) {
				f.createNewFile();
				List<String> defaultlines = Arrays.asList("maxMin;240");
				Files.write(Paths.get(UpTimeCounterApp.propfileName), defaultlines);
			}
			Stream<String> stream = Files.lines(Paths.get(UpTimeCounterApp.propfileName));
			List<String> lines = stream.collect(Collectors.toList());
			Optional<String> prop = lines.stream().filter(l -> l.contains(key)).findFirst();
			String propValue = prop.get().split(";")[1];
			return propValue;
		}catch (Exception e){
			System.out.println("Ex: "+e.getMessage());
			return "";
		}
	}
}
