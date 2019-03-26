package com.opencsv.examples;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CSVUtils<T> {

	public CSVUtils() {
//		Type interfacesTypes = this.getClass().getGenericSuperclass();
//		 System.out.println(interfacesTypes.getTypeName());
	}
	
	public void writeCSV(List<T> list, String filePath) {
		try (Writer writer = new FileWriter(filePath)) {
		     StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
		    		 .withApplyQuotesToAll(false)
		    		 .build();
		     beanToCsv.write(list);
		     writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public List<T> readCSV(String filePath, Class<? extends T> clazz) {
	     try {
	    	 CsvToBeanBuilder<T> builder =  new CsvToBeanBuilder<T>(new FileReader(filePath))
				       .withType(clazz);
	    	 return builder.build().parse();
		} catch (IllegalStateException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static void main (String [] arg) {
		CSVUtils<MyBean> csv = new CSVUtils<>();
		List<MyBean> list = new ArrayList<>();
		list.add(new MyBean("name1","value1","amount1","currency1","comments1"));
		list.add(new MyBean("name2","value2","amount2","currency2","comments2"));
		list.add(new MyBean("name3","value3","amount3","currency3","comments3"));
		list.add(new MyBean("name4","value4","amount4","currency4","comments4"));
		String filePath = "yourfile.csv";
		csv.writeCSV(list, filePath);
		list = csv.readCSV(filePath, MyBean.class);
		for (MyBean bean : list) {
			System.out.println(bean.getName());
		}
	}
	
}
