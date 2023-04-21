package com.example.demo.kmedoids.clustering;

import com.example.demo.crawler.CrawlerConfig;
import com.example.demo.util.file.FileEx;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.sicnu.yudidi.crawler.CrawlerConfig;
//import com.sicnu.yudidi.utils.file.FileEx;
@Slf4j
public class Test {

	public static void main(String[] args) {
		randowCluster();
	}

	public static void randowCluster() {
		// 加载数据集
//		List<DataSetRecord> records = loadRecords();
		List<DataSetRecord> records = loadRecordsMock();
		// 设置聚类器的数据集和簇数目
		KMedoidsClusterer clusterer = new KMedoidsClusterer(4, records, 10);
		// 设置聚类器的初始簇
		clusterer.randowClustering();
		// 打印聚类结果
		Map<String, List<DataSetRecord>> output = clusterer.output();
//		log
		log.info("打印聚类结果");
		for (String clusterName : output.keySet()) {
			List<DataSetRecord> recordsOfOneClusterName = output.get(clusterName);
//			System.out.println(String.format("=====簇名字:%s; 簇包含%d个记录 ====",
//					clusterName, recordsOfOneClusterName.size()));
			log.info(String.format("=====簇名字:%s; 簇包含%d个记录 ====",
					clusterName, recordsOfOneClusterName.size()));
			for (int i = 0; i < recordsOfOneClusterName.size(); i++) {
				DataSetRecord dataSetRecord = recordsOfOneClusterName.get(i);
				String info = recordsOfOneClusterName.get(i).getInfo();
//				id 的列表   info
				int[] users = dataSetRecord.getUsers();

//				System.out.println(String.format("记录:%s| 用户数目:%d| 是否中心记录:%s ",
//						info, dataSetRecord.getUsers().length, records.get(i).isMedoid()));
				log.info(String.format("记录:%s| 用户数目:%d| 是否中心记录:%s ",
						info, dataSetRecord.getUsers().length, records.get(i).isMedoid()));
				log.info("users {}",users);
			}
		}

		int[][] disMemo = clusterer.getDistancesMemo();
//		是啥
		for (int i = 0; i < disMemo.length; i++) {
			for (int j = 0; j < disMemo.length; j++) {
				System.out.print(String.format("[记录%s-->记录%s=%d],", i + 1, j + 1, disMemo[i][j]));
			}
			System.out.println();
		}
	}

	/**
	 * 加载数据集到列表集合
	 * 
	 * @return
	 */
	public static List<DataSetRecord> loadRecords() {
		List<DataSetRecord> records = new ArrayList<>();
		File file = Paths.get(CrawlerConfig.userDir, "output", "dataset.csv").toFile();
		String[] lines = FileEx.readLineByReader(file);
		String recordName = null;
		int[] userIds;
		for (int i = 0; i < lines.length; i++) {
			String[] items = lines[i].split(",");
			recordName = items[0];
//			recordName,userIds
//			recordName,1,2,3,4,5,6
//			recordName,[user id s -- 1,2,3,4,5,6
			userIds = new int[items.length - 1];
			for (int j = 1; j < items.length; j++) {
				userIds[j - 1] = Integer.valueOf(items[j]);
			}
//			簇名:0 哪里来的
			records.add(new DataSetRecord(i, recordName, userIds));
		}
		return records;
	}

	public static List<DataSetRecord> loadRecordsMock() {
		List<DataSetRecord> records = new ArrayList<>();
//		records.add(new DataSetRecord(1,"1",new int[]{1,2,3}));
//		records.add(new DataSetRecord(2,"2",new int[]{1,2,3}));
//		records.add(new DataSetRecord(3,"3",new int[]{1,2,3}));
//		records.add(new DataSetRecord(4,"2",new int[]{1,2,3}));
//		records.add(new DataSetRecord(5,"2",new int[]{1,2,3}));
//		records.add(new DataSetRecord(6,"2",new int[]{1,2,3}));
//		records.add(new DataSetRecord(7,"2",new int[]{1,2,3}));

//		records.add(new DataSetRecord(1,"1",new int[]{1,2,3}));
//		records.add(new DataSetRecord(2,"2",new int[]{1,2,3}));
////		[记录1-->记录2=997], 有三个 1 2 3 是相同的
//		records.add(new DataSetRecord(3,"3",new int[]{1,2,3}));
//		records.add(new DataSetRecord(4,"4",new int[]{4,5,6}));
//		records.add(new DataSetRecord(5,"5",new int[]{1,2,3}));
//		records.add(new DataSetRecord(6,"6",new int[]{4,2,3}));
//		records.add(new DataSetRecord(7,"7",new int[]{1,6,3}));

//		records.add(new DataSetRecord(1,"1",new int[]{1,2,3}));
//		records.add(new DataSetRecord(2,"2",new int[]{1,2,3,4,5,6,7,8}));
////		[记录1-->记录2=997], 有三个 1 2 3 是相同的
//		records.add(new DataSetRecord(3,"3",new int[]{1,2,3}));
//		records.add(new DataSetRecord(4,"4",new int[]{4,5,6}));
//		records.add(new DataSetRecord(5,"5",new int[]{1,2,3}));
//		records.add(new DataSetRecord(6,"6",new int[]{4,2,3}));
//		records.add(new DataSetRecord(7,"7",new int[]{1,6,3}));

		records.add(new DataSetRecord(1,"1",new int[]{1,2,3,4}));
		records.add(new DataSetRecord(2,"2",new int[]{1,2,3,4,5,6,7,8}));
//		[记录1-->记录2=996]
		records.add(new DataSetRecord(3,"3",new int[]{1,2,3}));
		records.add(new DataSetRecord(4,"4",new int[]{4,5,6}));
		records.add(new DataSetRecord(5,"5",new int[]{1,2,3}));
		records.add(new DataSetRecord(6,"6",new int[]{4,2,3}));
//		records.add(new DataSetRecord(7,"7",new int[]{1,6,3}));
		records.add(new DataSetRecord(7,"7",new int[]{1,2,3}));

//		File file = Paths.get(CrawlerConfig.userDir, "output", "dataset.csv").toFile();
//		String[] lines = FileEx.readLineByReader(file);
//		String recordName = null;
//		int[] userIds;
//		for (int i = 0; i < lines.length; i++) {
//			String[] items = lines[i].split(",");
//			recordName = items[0];
////			recordName,userIds
////			recordName,1,2,3,4,5,6
////			recordName,[user id s -- 1,2,3,4,5,6
//			userIds = new int[items.length - 1];
//			for (int j = 1; j < items.length; j++) {
//				userIds[j - 1] = Integer.valueOf(items[j]);
//			}
//			records.add(new DataSetRecord(i, recordName, userIds));
//		}
		return records;
	}
}
