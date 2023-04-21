package com.example.demo.kmedoids.dataset;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.crawler.CrawlerConfig;
import com.example.demo.util.file.FileEx;
import lombok.extern.slf4j.Slf4j;
//import org.apache.log4j.Logger;

//import com.sicnu.yudidi.crawler.CrawlerConfig;
//import com.sicnu.yudidi.utils.file.FileEx;

@Slf4j
public class GenerateDataSet {

//	private final static Logger log = Logger.getLogger(GenerateDataSet.class);
	public static void main(String[] args) {
		File file = Paths.get(CrawlerConfig.SUBJECTS_DATA).toFile();
		File dataset = Paths.get(CrawlerConfig.userDir, "output", "dataset.csv").toFile();
		StringBuffer records = new StringBuffer();
		for (File dir : file.listFiles()) {
			Set<Integer> authorIds = new TreeSet<>();
			extractAuthorId(dir, authorIds);
			String subjectId = dir.getName();
//			目录地下有好多 authorIds 吗
			StringBuffer record = new StringBuffer();
//			subjectId,,,,
			record.append(subjectId+",");
			for (Integer authorId : authorIds) {
				record.append(authorId+",");
			}
			records.append(record);
			records.append(System.lineSeparator());
			log.info(String.format("|append record|subjectId|%s",subjectId));
		}
		FileEx.saveByWriter(records.toString(), dataset);
		log.info("生成数据集成功");
	}

	public static void extractAuthorId(File file, Set<Integer> authorIds) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				extractAuthorId(subFile, authorIds);
			}
		}else {
			String content = FileEx.readByReader(file);
			Pattern pattern = Pattern.compile("(?<=\"authorId\":)[\\s0-9]*(?=,)");
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				authorIds.add(Integer.valueOf(matcher.group()));
			}
		}
	}
}
