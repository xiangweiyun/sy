package com.sy.center.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * 代码生成工具，执行前先确认对应的数据库连接信息 执行main方法控制台输入模块表名回车自动生成对应项目目录中
 * 
 * @author xiangwy
 * @date: 2020-11-30 16:33:35
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
public class CodeGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeGenerator.class);
	/**
	 * Project package
	 */
	private static String bootDir;
	private static String pojoAndVoDir;
	private static String serviceDir;
	private static String classPackageName;

	/**
	 * 包配置
	 */
	private static String packageMapper;
	private static String pojoPackageName;
	private static String voPackageName;
	private static String servicePackageName;
	private static String serviceImplPackageName;
	private static String controllerPackageName;

	/**
	 * Database url
	 */
	private static String url;
	/**
	 * Database username
	 */
	private static String username;
	/**
	 * Database password
	 */
	private static String password;
	/**
	 * Database driver class
	 */
	private static String driverClass;

	/**
	 * 读取控制台内容
	 * 
	 * @author: xiangwy
	 * @date: 2020-11-30 16:33:45
	 * @param tip
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("请输入" + tip + "：");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	/**
	 * 读取生成配置
	 */
	static {
		Properties properties = new Properties();
		InputStream i = CodeGenerator.class.getResourceAsStream("/sysOracle.properties");
		try {
			properties.load(i);
			url = properties.getProperty("generator.jdbc.url").trim();
			username = properties.getProperty("generator.jdbc.username").trim();
			password = properties.getProperty("generator.jdbc.password").trim();
			driverClass = properties.getProperty("generator.jdbc.driverClass").trim();

			bootDir = properties.getProperty("bootDir").trim();
			pojoAndVoDir = properties.getProperty("pojoAndVoDir").trim();
			serviceDir = properties.getProperty("serviceDir").trim();

			classPackageName = properties.getProperty("classPackageName").trim();
			packageMapper = properties.getProperty("packageMapper").trim();
			pojoPackageName = properties.getProperty("pojoPackageName").trim();
			voPackageName = properties.getProperty("voPackageName").trim();
			servicePackageName = properties.getProperty("servicePackageName").trim();
			serviceImplPackageName = properties.getProperty("serviceImplPackageName").trim();
			controllerPackageName = properties.getProperty("controllerPackageName").trim();
		} catch (IOException e) {
			LOGGER.error("读取配置文件失败", e);
		}
	}

	public static void main(String[] args) {
		bootDir = bootDir.replaceAll("\\.", "/");
		pojoAndVoDir = pojoAndVoDir.replaceAll("\\.", "/");
		serviceDir = serviceDir.replaceAll("\\.", "/");
		String packageDir = classPackageName.replaceAll("\\.", "/");

		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setAuthor("xiangwy");
		// 是否打开输出目录
		gc.setOpen(false);
		// 输出文件目录
		gc.setOutputDir(bootDir + "/src/main/java");
		// 是否覆盖已有文件
		gc.setFileOverride(true);
		// 实体属性 Swagger2 注解
		gc.setSwagger2(true);
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("%sService");
		gc.setServiceImplName("%sServiceImpl");
		// mapper.xml中生成BaseResultMap
		gc.setBaseResultMap(true);
		gc.setActiveRecord(false);
		gc.setBaseColumnList(true);

		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.ORACLE);
		dsc.setDriverName(driverClass.trim());
		dsc.setUrl(url.trim());
		// dsc.setSchemaName("public");
		dsc.setUsername(username.trim());
		dsc.setPassword(password.trim());
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(classPackageName.trim());
		// 模块名
		// pc.setModuleName("model名");
		pc.setMapper(packageMapper.trim());
		pc.setEntity(pojoPackageName.trim());
		pc.setService(servicePackageName.trim());
		pc.setServiceImpl(serviceImplPackageName.trim());
		pc.setController(controllerPackageName.trim());
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		// 如果模板引擎是 freemarker
		String templatePath = "template/sqlMap.xml.ftl";

		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
				return bootDir + "src/main/resources/" + packageDir + "/mapper/"
						+ tableInfo.getEntityName() + "Mapper"
						+ StringPool.DOT_XML;
			}
		});
		templatePath = "template/pojoSource.java.ftl";
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名
				return pojoAndVoDir + "src/main/java/" + packageDir + "/entity/"
						+ tableInfo.getEntityName() + StringPool.DOT_JAVA;
			}
		});
		templatePath = "/templates/service.java.ftl";
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名
				return serviceDir + "src/main/java/" + packageDir + "/service/"
						+ tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setController("template/controller.java");
		templateConfig.setService(null);
		templateConfig.setEntity(null);
		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setEntityColumnConstant(true);
		// strategy.setExclude("");
		// 表名
		strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
		strategy.setControllerMappingHyphenStyle(true);
		/** 表前缀,要自动去除添加 */
		strategy.setTablePrefix(pc.getModuleName() + "_");
		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();

	}

}
