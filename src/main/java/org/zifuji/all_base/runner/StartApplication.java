package org.zifuji.all_base.runner;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zifuji.all_base.util.TcUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class StartApplication implements ApplicationRunner {

	@Value("${server.port}")
	private String port;
	@Value("${spring.application.name}")
	private String name;

	public void run(ApplicationArguments args) throws Exception {
		log.info("开始初始化");
		this.createUserTcDir();
		this.open();
		log.info("结束初始化");
	}

	public void open() {
		String url = "http://localhost:" + port + "/";
		String os = System.getProperty("os.name").toLowerCase();
		Runtime runtime = Runtime.getRuntime();
		try {
			if (os.contains("win")) {
				runtime.exec("cmd /c start " + url);
			} else {

			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}

	public void createUserTcDir() throws Exception {
		String tcDir = TcUtil.getUserDir() + File.separator + "zifuji" + File.separator + name;

		File pathFile = new File(tcDir);
		if (!pathFile.exists()) {
			// 如果路径不存在创建路径
			boolean f = pathFile.mkdirs();
			if (!f) {
				throw new Exception("创建总文件夹失败");
			}
		}
	}

}